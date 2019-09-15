package com.gfs.backend.logic;

import org.junit.Test;

import static org.junit.Assert.*;

public class DistanceBasedCalculationTest {
    @Test
    public void test() {
        double lat2 = 100;
        double lat1 = 200;
        double lon2 = 100;
        double lon1 = 200;
        final int R = 6371;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;
        System.out.println(Math.sqrt(distance));
    }

}