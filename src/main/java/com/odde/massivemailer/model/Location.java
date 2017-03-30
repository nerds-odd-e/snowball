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
        int R = 6378137; // Earth’s mean radius in meter
        double dLat = toRadian(location.lat - lat);
        double dLong = toRadian(location.lng - lng);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(toRadian(location.lat)) * Math.cos(toRadian(lat)) *
                        Math.sin(dLong / 2) * Math.sin(dLong / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        Double d = R * c/1000;
        return d.intValue(); // returns the distance in kilometer
    };

    public String getName() {
        return this.name;
    }
}
