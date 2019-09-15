package com.gfs.backend.model;



public class OpenFoodData {

    private String productName;

    private boolean isOilPalm = false;


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public boolean isOilPalm() {
        return isOilPalm;
    }

    public void setOilPalm(boolean oilPalm) {
        isOilPalm = oilPalm;
    }
}
