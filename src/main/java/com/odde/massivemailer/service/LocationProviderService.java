package com.odde.massivemailer.service;

import com.odde.massivemailer.model.Location;
import com.odde.massivemailer.service.exception.GeoServiceException;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LocationProviderService {
    private static Map<String, Location> locations = new TreeMap<>();
    private final static String DELIMITER = "/";

    static {
        resetCachedLocations();
    }

    public LocationProviderService() {
    }

    public static String locationString(Object city, Object country) {
        String location;
        if (city == null) {
            location = (String) country;
        } else {
            location = country + DELIMITER + city;
        }
        return location;
    }

    public Location getLocationForName(String locationStringWithCase) throws GeoServiceException {
        String locationString = locationStringWithCase.toLowerCase();
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

    private Location getLocationForCityAndCountry(String city, String country) throws GeoServiceException {
        String locationString = locationString(city, country).toLowerCase();
        if (locations.containsKey(locationString)) {
            return locations.get(locationString);
        }
        Location location = getLocation(country, city);
        locations.put(locationString, location);
        return location;
    }

    public static void resetCachedLocations() {
        locations = new TreeMap<>();
        locations.put("japan/notexist", Location.nullLocation());
        addCachedLocation("Singapore/Singapore", "Singapore/Singapore", 1.3521, 103.8198);
        addCachedLocation("Thailand/Bangkok", "Thailand/Bangkok", 13.7563, 100.5018);
        addCachedLocation("Tokyo", "Tokyo", 35.6895, 139.6917);
        addCachedLocation("Japan/Tokyo", "Japan/Tokyo", 35.6895, 139.6917);
        addCachedLocation("Japan/Osaka", "Japan/Osaka", 35.6895, 139.6917);
        addCachedLocation("Jakarta", "Jarkata", -6.174465, 106.822745);
        addCachedLocation("Kuala Lumpur", "Kuala Lumpur", 3.139003, 101.686855);
        addCachedLocation("Malaysia/Kuala Lumpur", "Kuala Lumpur", 3.139003, 101.686855);
        addCachedLocation("Seoul", "Seoul", 37.566535, 126.977969);
        addCachedLocation("New Delhi", "New Delhi", 28.613939, 77.209021);
        addCachedLocation("Bangalore", "Bangalore", 12.971599, 77.594563);
        addCachedLocation("Hanoi", "Hanoi", 21.027764, 105.834160);
        addCachedLocation("Manila", "Manila", 14.599512, 120.984219);
        addCachedLocation("Beijing", "Beijing", 39.904211, 116.407395);
        addCachedLocation("Shanghai", "Shanghai", 31.230416, 121.473701);
        addCachedLocation("USA/New York", "New York", 40.730610, -73.935242);
    }

    private static void addCachedLocation(String place, String city, double lat, double lng) {
        locations.put(place.toLowerCase(), new Location(city, lat, lng));
    }

    public void addLat_LongToMemory(String country, String city) throws GeoServiceException {
        Location location = getLocation(country, city);
        locations.put(locationString(city, country), location);
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
        Location storedLocation;
        try {
            storedLocation = getLocationForName(location);
            if (storedLocation == null) {
                addLat_LongToMemory(country, city);
            }
        } catch (GeoServiceException e) {
            throw new RuntimeException("location service failed", e);
        }
    }

    public Location getCoordinate(String city, String country) {
        try {
            return getLocationForCityAndCountry(city, country);
        } catch (GeoServiceException e) {
            throw (new RuntimeException("Location Service is not available", e));
        }
    }

}
