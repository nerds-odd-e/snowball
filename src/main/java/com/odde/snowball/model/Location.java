package com.odde.snowball.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor
public class Location {
    public static final int CLOSE_BY_DISTANCE = 2000;

    private String city;
    private String countryCode;
    private String country;
    @NotNull(message="This location cannot be found")
    private Double latitude;
    private Double longitude;

    public Location(String name, Double lat, Double lng) {
        city = name;
        this.latitude= lat;
        this.longitude = lng;
    }

    public static Location nullLocation() {
        return new Location(null, null, null);
    }

    private double toRadian(double x) {
        return x * Math.PI / 180;
    }

    public int distanceFrom(Location geoCoordinate) {
        double earthRadiusInKm = 6378.137;
        double differenceOfLatInRadian = toRadian(geoCoordinate.latitude - latitude);
        double differenceOfLongInRadian = toRadian(geoCoordinate.longitude - longitude);
        // a is the square of half the chord length between the points.
        double a = Math.sin(differenceOfLatInRadian / 2) * Math.sin(differenceOfLatInRadian / 2) +
                Math.cos(toRadian(geoCoordinate.latitude)) * Math.cos(toRadian(latitude)) *
                        Math.sin(differenceOfLongInRadian / 2) * Math.sin(differenceOfLongInRadian / 2);
        double angularDistance = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        Double distanceInKm = earthRadiusInKm * angularDistance;
        return distanceInKm.intValue();
    }

    public boolean IsNear(Location geoCoordinate) {
        if(geoCoordinate == null || geoCoordinate.getLatitude() == null) {
            return false;
        }
        return this.distanceFrom(geoCoordinate) <= CLOSE_BY_DISTANCE;
    }

}
