package com.odde.massivemailer.model;

public class Location {
    private String name;
    private double lat;
    private double lng;

    public Location(String name, double lat, double lng) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    private double toRadian (double x) {
        return x * Math.PI / 180;
    };

    public int distanceFrom(Location location) {
        double earthRadiusInKm = 6378.137;
        double differenceOfLatInRadian = toRadian(location.lat - lat);
        double differenceOfLongInRadian = toRadian(location.lng - lng);
        // a is the square of half the chord length between the points.
        double a = Math.sin(differenceOfLatInRadian / 2) * Math.sin(differenceOfLatInRadian / 2) +
                   Math.cos(toRadian(location.lat)) * Math.cos(toRadian(lat)) *
                   Math.sin(differenceOfLongInRadian / 2) * Math.sin(differenceOfLongInRadian / 2);
        double angularDistance = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        Double distanceInKm = earthRadiusInKm * angularDistance;
        return distanceInKm.intValue();
    };

    public String getName() {
        return this.name;
    }

    public Double getLatitude() {
        return 34.41;
    }

    public Double getLongitude() {
        return 135.31;
    }
}
