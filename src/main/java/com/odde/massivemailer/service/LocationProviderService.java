package com.odde.massivemailer.service;

import com.odde.massivemailer.model.Location;
import com.odde.massivemailer.service.exception.GeoServiceException;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LocationProviderService {
    private static Map<String, Location> locations = new TreeMap<>();
    private final static String DELIMITER = "/";

    static {
        resetLocations();
    }

    public LocationProviderService() {
    }

    public List<String> getSupportedLocations() {
        return new ArrayList(locations.keySet());
    }

    public Location getLocationForName(String locationString) throws GeoServiceException {
        if (locations.containsKey(locationString)) {
            return locations.get(locationString);
        }
        if (locationString.contains(DELIMITER)) {
            String[] countryAndCity = locationString.split(DELIMITER);
            Location location = getLocation(countryAndCity[0], countryAndCity[1]);
            locations.put(locationString, location);
            return location;
        }
        return null;
    }

    public static void resetLocations() {
        locations = new TreeMap<>();
        locations.put("Singapore/Singapore", new Location("Singapore/Singapore", 1.3521, 103.8198));
        locations.put("Thailand/Bangkok", new Location("Thailand/Bangkok", 13.7563, 100.5018));
        locations.put("Tokyo", new Location("Tokyo", 35.6895, 139.6917));
        locations.put("Japan/Tokyo", new Location("Japan/Tokyo", 35.6895, 139.6917));
        locations.put("Japan/Osaka", new Location("Japan/Osaka", 35.6895, 139.6917));
        locations.put("Jakarta", new Location("Jarkata", -6.174465, 106.822745));
        locations.put("Kuala Lumpur", new Location("Kuala Lumpur", 3.139003, 101.686855));
        locations.put("Seoul", new Location("Seoul", 37.566535, 126.977969));
        locations.put("New Delhi", new Location("New Delhi", 28.613939, 77.209021));
        locations.put("Bangalore", new Location("Bangalore", 12.971599, 77.594563));
        locations.put("Hanoi", new Location("Hanoi", 21.027764, 105.834160));
        locations.put("Manila", new Location("Manila", 14.599512, 120.984219));
        locations.put("Beijing", new Location("Beijing", 39.904211, 116.407395));
        locations.put("Shanghai", new Location("Shanghai", 31.230416, 121.473701));
    }

    public void addLat_LongToMemory(String country, String city) throws GeoServiceException {
        Location location = getLocation(country, city);
        locations.put(country + "/" + city, location);
    }

    private Location getLocation(String country, String city) throws GeoServiceException {
        GoogleGeoAPIService geoService = new GoogleGeoAPIService();
        return geoService.getGeocode(country, city);
    }

    public List<String> getAllCountryNames() throws IOException {
        URL url = getClass().getClassLoader().getResource("csv/countries.csv");
        return Files.readAllLines(Paths.get(url.getPath()));
    }

    public void cacheLocation(String city, String country, String location) {
        Location storedLocation = null;
        try {
            storedLocation = getLocationForName(location);
            if (storedLocation == null) {
                addLat_LongToMemory(country, city);
            }
        } catch (GeoServiceException e) {
            throw new RuntimeException("location service failed", e);
        }
    }

    public Location getCoordinate(String location) {
        Location coordinate;
        try {
            coordinate = getLocationForName(location);
        } catch (GeoServiceException e) {
            throw (new RuntimeException("Location Service is not available", e));
        }
        if (coordinate == null) {
            throw (new RuntimeException("CityName is invalid"));
        }
        return coordinate;
    }
}
