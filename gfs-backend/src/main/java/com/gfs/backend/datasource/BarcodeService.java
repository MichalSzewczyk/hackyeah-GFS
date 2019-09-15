package com.gfs.backend.datasource;

import java.util.Optional;

public interface BarcodeService<T> {
    Optional<T> lookup(String input);
}
