package com.gfs.backend.datasource.internal;

import com.gfs.backend.model.Barcode;
import com.gfs.backend.model.Country;

import java.util.Optional;

public interface CountryProvider {
    Optional<Country> getCountryByBarcode(Barcode barcode);
}
