package com.gfs.backend.model;

import lombok.Getter;

import java.util.Comparator;

@Getter
public class Barcode implements Comparable<Barcode> {
    private final Comparator<Barcode> barcodeComparator;
    private final String barcode;

    public Barcode(String barcode) {
        this.barcode = barcode;
        this.barcodeComparator = Comparator.comparing(Barcode::getBarcode);
    }

    @Override
    public int compareTo(Barcode barcode) {
        return barcodeComparator.compare(this, barcode);
    }
}
