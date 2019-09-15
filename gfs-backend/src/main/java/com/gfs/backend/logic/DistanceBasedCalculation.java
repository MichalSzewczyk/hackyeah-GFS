package com.gfs.backend.logic;

import com.gfs.backend.datasource.BarcodeService;
import com.gfs.backend.model.Barcode;
import com.gfs.backend.model.Country;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class DistanceBasedCalculation implements GeoAwareCalculation {
    private final BarcodeService<Country> countryProvider;

    @Override
    public long calculate(Barcode barcode, double latitude, double longitude) {
        log.info("Calculating footprint based on country.");
        Optional<Country> optionalCountry = countryProvider.lookup(barcode.getBarcode());
        log.info("Returned country for barcode. [barcode={}] [optionalCountry={}]", barcode, optionalCountry);
        if (!optionalCountry.isPresent()) {
            log.warn("Country not recognized, returning 0 footprint. [barcode={}] [latitude={}] [longitude={}]", barcode, latitude, longitude);
            return 0;
        }
        Country productCountry = optionalCountry.get();
        double result = getDistance(productCountry.getLatitude(), productCountry.getLongitude(), latitude, longitude);
        log.info("Getting distance for lat long. [sourceLatitude={}] [sourceLongitude={}] [destinationLatitude={}] [destinationLongitude={}] [result={}]",
                productCountry.getLatitude(), productCountry.getLongitude(), latitude, longitude, result);
        return (long) result;
    }

    private static double getDistance(double lat1, double lat2, double lon1,
                                      double lon2) {

        final int R = 6371;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;
        return Math.sqrt(distance);
    }
}
