package com.gfs.backend.controllers;

import com.gfs.backend.logic.FootprintCalculator;
import com.gfs.backend.model.Barcode;
import com.gfs.backend.model.EnvironmentFootprint;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
public class GreenFootprintController {
    private final FootprintCalculator weightedFootprintCalculator;

    @GetMapping("/footprint/calculate")
    ResponseEntity<EnvironmentFootprint> getGreenFootrpint(@RequestParam("lat") double lat, @RequestParam("lon") double lon, @RequestParam("barcode") String barcode) {
        log.info("Received request for footprint. [latitude={}] [longitude={}] [barcode={}]", lat, lon, barcode);
        EnvironmentFootprint environmentFootprint = weightedFootprintCalculator.calculate(new Barcode(barcode), lat, lon);
        return ResponseEntity.ok(environmentFootprint);
    }
}
