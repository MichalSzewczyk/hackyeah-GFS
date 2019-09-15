package com.example.hackyeah2019.model;

import com.google.common.base.MoreObjects;

public class RespondJson {
    private long value;
    private String productName;

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("value", value)
                .add("productName", productName)
                .toString();
    }
}
