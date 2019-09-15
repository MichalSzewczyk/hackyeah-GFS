package com.gfs.backend.logic;

import com.gfs.backend.model.Barcode;

public interface Calculation {
    long calculate(Barcode barcode);
}
