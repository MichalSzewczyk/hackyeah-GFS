package com.gfs.backend.logic;

import com.gfs.backend.model.Barcode;

public interface GeoAwareCalculation {
    long calculate(Barcode barcode, double lat, double lon);
}
