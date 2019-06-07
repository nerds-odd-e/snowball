package com.odde.massivemailer.model;

import com.google.maps.model.LatLng;

public class Location {
    public static final int CLOSE_BY_DISTANCE = 2000;

    private String cityAndCountry;
    private String countryCode;
    private String countryName;
    private Double lat;
    private Double lng;

    public Location(String name, Double lat, Double lng) {
        this.cityAndCountry = name;
        this.lat = lat;
        this.lng = lng;
    }

    public Location() {
    }

    public static Location nullLocation() {
        return new Location(null, null, null);
    }

    private double toRadian(double x) {
        return x * Math.PI / 180;
    }

    public int distanceFrom(Location geoCoordinate) {
        double earthRadiusInKm = 6378.137;
        double differenceOfLatInRadian = toRadian(geoCoordinate.lat - lat);
        double differenceOfLongInRadian = toRadian(geoCoordinate.lng - lng);
        // a is the square of half the chord length between the points.
        double a = Math.sin(differenceOfLatInRadian / 2) * Math.sin(differenceOfLatInRadian / 2) +
                Math.cos(toRadian(geoCoordinate.lat)) * Math.cos(toRadian(lat)) *
                        Math.sin(differenceOfLongInRadian / 2) * Math.sin(differenceOfLongInRadian / 2);
        double angularDistance = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        Double distanceInKm = earthRadiusInKm * angularDistance;
        return distanceInKm.intValue();
    }

    public String getName() {
        return this.cityAndCountry;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setName(String name) {
        this.cityAndCountry = name;
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

    public boolean IsNear(Location geoCoordinate) {
        if(geoCoordinate.getLat() == null) {
            return false;
        }
        return this.distanceFrom(geoCoordinate) <= CLOSE_BY_DISTANCE;
    }

}
