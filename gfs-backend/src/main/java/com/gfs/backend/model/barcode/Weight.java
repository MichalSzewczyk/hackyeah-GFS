package com.gfs.backend.model.barcode;

public class Weight {
    private final int weightInGrams;

    public Weight(int weightInGrams) {
        this.weightInGrams = weightInGrams;
    }


    public int getWeightInGrams() {
        return weightInGrams;
    }

    @Override
    public String toString() {
        return "Weight{" +
                "weightInGrams=" + weightInGrams +
                '}';
    }
}
