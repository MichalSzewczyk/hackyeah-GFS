package com.gfs.backend.logic;

import com.gfs.backend.model.Barcode;
import com.gfs.backend.model.EnvironmentFootprint;

public interface FootprintCalculator {
    EnvironmentFootprint calculate(Barcode barcode, double latitude, double longitude);
}
