package com.gfs.backend.logic;

import com.gfs.backend.model.Barcode;
import com.gfs.backend.model.BarcodeData;
import com.gfs.backend.model.EnvironmentFootprint;
import com.gfs.backend.model.OpenFoodData;
import com.gfs.backend.model.barcode.Weight;
import com.gfs.backend.persistence.Product;
import com.gfs.backend.repo.ProductRepository;
import com.gfs.backend.datasource.BarcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WeightedFootprintCalculator implements FootprintCalculator {
    @Autowired
    private ProductRepository productRepository;
    private final GeoAwareCalculation distanceBasedCalculation;
    private final BarcodeService<BarcodeData> barcodeDataProvider;
    private final BarcodeService<OpenFoodData> openFoodDataProvider;

    public WeightedFootprintCalculator(GeoAwareCalculation distanceBasedCalculation, BarcodeService<BarcodeData> barcodeDataProvider, BarcodeService<OpenFoodData> openFoodDataProvider) {
        this.distanceBasedCalculation = distanceBasedCalculation;
        this.barcodeDataProvider = barcodeDataProvider;
        this.openFoodDataProvider = openFoodDataProvider;
    }

    @Override
    public EnvironmentFootprint calculate(Barcode barcode, double latitude, double longitude) {
        long calculationResult = 0;
        Product product = productRepository.findByBarCode(barcode.getBarcode());
        Optional<BarcodeData> optionalBarcodeData = barcodeDataProvider.lookup(barcode.getBarcode());

        Optional<OpenFoodData> optionalOpenFoodData = openFoodDataProvider.lookup(barcode.getBarcode());
        if (optionalOpenFoodData.isPresent()) {
            calculationResult = addOpenFoodData(calculationResult, optionalOpenFoodData.get());
        }
        long distanceBasedCalculationResult = distanceBasedCalculation.calculate(barcode, latitude, longitude);

        calculationResult = addBarcodeData(optionalBarcodeData, calculationResult, distanceBasedCalculationResult);

        final long normalizedCalculationResult = (calculationResult / 100) % 100;
        return optionalBarcodeData.map(barcodeData1 -> new EnvironmentFootprint(normalizedCalculationResult, barcodeData1.getProductName()))
                .orElseGet(() -> new EnvironmentFootprint(normalizedCalculationResult, "unknown product"));
    }

    private long addBarcodeData(Optional<BarcodeData> optionalBarcodeData, long finalCalculationResult, long distanceBasedCalculationResult) {
        int weightInGrams = 100;
        if (optionalBarcodeData.isPresent()) {
            BarcodeData barcodeData = optionalBarcodeData.get();
            Optional<Weight> optionalWeight = barcodeData.getWeight();
            if (optionalWeight.isPresent() && optionalWeight.get().getWeightInGrams() > 1) {
                weightInGrams = optionalWeight.get().getWeightInGrams();
            }
        }
        return finalCalculationResult + weightInGrams * distanceBasedCalculationResult;
    }

    private long addOpenFoodData(long finalCalculationResult, OpenFoodData openFoodData) {
        if (openFoodData.isOilPalm()) {
            finalCalculationResult *= 2;
        }
        return finalCalculationResult;
    }
}
