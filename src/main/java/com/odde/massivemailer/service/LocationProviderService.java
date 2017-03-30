package com.odde.massivemailer.service;

import com.odde.massivemailer.model.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LocationProviderService {
    private Map<String, Location> locations = new TreeMap<>();
    public static final int CLOSE_BY_DISTANCE = 2000;

    public LocationProviderService() {

        locations.put("Singapore", new Location("Singapore", 1.3521,103.8198));
        locations.put("Bangkok", new Location("Bangkok", 13.7563, 100.5018));
        locations.put("Tokyo", new Location("Tokyo", 35.6895, 139.6917));
    }

    public List<String> getSupportedLocations() {
        return new ArrayList(locations.keySet());
    }

    public Location getLocationForName(String locationString) {
        return locations.get(locationString);
    }

    public String getCloseByLocationStrings(String locationName) {
        Location location = locations.get(locationName);
        String locationsString = "";

        if(location != null){
            for (Location loc : locations.values()) {
                if (loc.distanceFrom(location) <= CLOSE_BY_DISTANCE) {
                    locationsString += "\"" + loc.getName() + "\", ";
                }
            }
            locationsString = locationsString.substring(0, locationsString.length() - 2);
        }
        return locationsString;
    }
}
