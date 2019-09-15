package com.gfs.backend.datasource.internal;

import com.gfs.backend.configuration.CountryConfiguration;
import com.gfs.backend.datasource.BarcodeService;
import com.gfs.backend.model.Barcode;
import com.gfs.backend.model.Country;
import com.gfs.backend.utils.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.TreeMap;

import static java.util.Objects.isNull;

@Slf4j
@Service
@ConfigurationProperties("barcode.country")
@EnableConfigurationProperties
public class MappingBasedCountryProvider implements BarcodeService<Country> {
    private static final String REGEX = "-";
    private final NavigableMap<Barcode, Country> barcodeCountryMapping;

    public MappingBasedCountryProvider(CountryConfiguration countryConfiguration) {
        Map<String, String> rawBarcodeCountryMapping = countryConfiguration.getCountryMapping();
        log.info("Country mapping defined. [rawBarcodeCountryMapping={}]", rawBarcodeCountryMapping);
        barcodeCountryMapping = extractBarcodeCountryMapping(rawBarcodeCountryMapping);
    }

    @Override
    public Optional<Country> lookup(String barcode) {
        Map.Entry<Barcode, Country> barcodeCountryEntry = barcodeCountryMapping.ceilingEntry(new Barcode(barcode));
        if (isNull(barcodeCountryEntry)) {
            return Optional.empty();
        }
        return Optional.ofNullable(barcodeCountryEntry.getValue());
    }

    private NavigableMap<Barcode, Country> extractBarcodeCountryMapping(Map<String, String> rawBarcodeCountryMapping) {
        NavigableMap<Barcode, Country> barcodeCountryMapping = new TreeMap<>();
        rawBarcodeCountryMapping.forEach((key, value) -> extractAndPutPair(key, value, barcodeCountryMapping));
        return barcodeCountryMapping;
    }

    private void extractAndPutPair(String rawCountry, String barcodePrefixRange, NavigableMap<Barcode, Country> barcodeCountryMapping) {
        Country country = Country.valueOf(rawCountry);
        Pair<Barcode, Barcode> barcodesRange = extractBarcodeRangeInclusive(barcodePrefixRange);
        barcodeCountryMapping.put(barcodesRange.getKey(), country);
        barcodeCountryMapping.put(barcodesRange.getValue(), country);
    }

    private Pair<Barcode, Barcode> extractBarcodeRangeInclusive(String barcodePrefixRange) {
        String[] barcodesPrefixes = barcodePrefixRange.split(REGEX);
        if (barcodesPrefixes.length != 2) {
            throw new IllegalArgumentException("Barcodes mapping is incorrect.");
        }
        String prefixFromBarcode = barcodesPrefixes[0];
        String prefixToBarcode = barcodesPrefixes[1];
        String flooredRawFromBarcode = floor(prefixFromBarcode);
        String ceiledRawToBarcode = ceil(prefixToBarcode);
        Barcode fromBarcode = new Barcode(flooredRawFromBarcode);
        Barcode toBarcode = new Barcode(ceiledRawToBarcode);
        return Pair.from(fromBarcode, toBarcode);
    }

    private String floor(String rawBarcodePrefix) {
        return rawBarcodePrefix + "0000000000";
    }

    private String ceil(String rawBarcodePrefix) {
        return rawBarcodePrefix + "9999999999";
    }
}
