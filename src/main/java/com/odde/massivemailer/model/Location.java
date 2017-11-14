package com.odde.massivemailer.model;

import com.google.maps.model.LatLng;

public class Location {
    public static double INVALID_LONGTITUDE = -1;
    public static double INVALID_LATITUDE = -1;

    private String name;
    private String countryCode;
    private String countryName;
    private double lat;
    private double lng;

    public Location(String name, double lat, double lng) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    public Location(){}

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
        return this.lat;
    }

    public Double getLongitude() {
        return this.lng;
    }

    public String getCountryCode() { return this.countryCode; }

    public String getCountryName() {
        return countryName;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setLatLong(LatLng latLng) {
        setLat(latLng.lat);
        setLng(latLng.lng);
    }
}
