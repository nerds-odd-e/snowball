package com.odde.massivemailer.model;

public enum Location {
    Singapore(1.35, 103),
    Bangkok(13.75, 100.50),
    Tokyo(35.69, 139.69);

    Double latitude, longitude;
    Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
