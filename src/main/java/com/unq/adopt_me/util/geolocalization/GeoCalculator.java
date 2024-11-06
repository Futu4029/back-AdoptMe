package com.unq.adopt_me.util.geolocalization;

import org.springframework.stereotype.Component;

@Component
public class GeoCalculator {
    private static final double EARTH_RADIUS_KM = 6371.0; // Radio de la Tierra en kilómetros

    public double calculateDistance(Double lat1, Double lon1, Double lat2, Double lon2) {

        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // Aplicar fórmula del Haversine
        double dlat = lat2Rad - lat1Rad;
        double dlon = lon2Rad - lon1Rad;

        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.pow(Math.sin(dlon / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Distancia en kilómetros
        return Math.abs(EARTH_RADIUS_KM * c);
    }
}
