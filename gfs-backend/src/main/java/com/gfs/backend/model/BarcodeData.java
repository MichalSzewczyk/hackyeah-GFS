package com.gfs.backend.model;

import com.gfs.backend.model.barcode.Category;
import com.gfs.backend.model.barcode.Weight;

import java.util.Optional;

public class BarcodeData {
    private Category category;
    private Weight weight;
    private String productName;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    public Optional<Category> getCategory() {
        return Optional.ofNullable(category);
    }

    public Optional<Weight> getWeight() {
        return Optional.ofNullable(weight);
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "BarcodeData{" +
                "category=" + category +
                ", weight=" + weight +
                '}';
    }
}
