package com.gfs.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EnvironmentFootprint {
    private final long value;
    private final String productName;
}
