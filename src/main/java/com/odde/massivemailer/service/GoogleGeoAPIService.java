package com.odde.massivemailer.service;

import com.google.maps.errors.ApiException;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.odde.massivemailer.model.Location;
import com.google.maps.*;
import com.odde.massivemailer.service.exception.GeoServiceException;

import java.io.IOException;
public class GoogleGeoAPIService {

    public Location getGeocode(String country, String city) throws GeoServiceException {
        if (country == null) {
            return new Location(null, Location.INVALID_LATITUDE, Location.INVALID_LONGTITUDE);
        }
        try {
            return getLocationFromGoogle(country, city);
        } catch (InterruptedException | ApiException | IOException e) {
            throw(new GeoServiceException(e));
        }
    }

    private Location getLocationFromGoogle(String country, String city) throws InterruptedException, ApiException, IOException {
        GeocodingResult geocodingResult = getGeocodingResult(country, city);
        if( geocodingResult == null ){
            return null;
        }

        AddressComponent[] addressComponents = geocodingResult.addressComponents;
        Location location = new Location();

        for (AddressComponent addressComponent : addressComponents) {
            setAddressInformation(location, addressComponent);
        }
        location.setLatLong(geocodingResult.geometry.location);

        return location;
    }

    private void setAddressInformation(Location location, AddressComponent addressComponent) {
        AddressComponentType addressComponentType = addressComponent.types[0];
        if (addressComponentType == AddressComponentType.COUNTRY) {
            location.setCountryCode(addressComponent.shortName);
            location.setCountryName(addressComponent.longName);
        }
        if (addressComponentType == AddressComponentType.LOCALITY) {
            location.setName(addressComponent.longName);
        }
    }

    private GeocodingResult getGeocodingResult(String country, String city) throws InterruptedException, ApiException, IOException {
        GeocodingApiRequest request = GeocodingApi.newRequest(getGeoApiContext()).address(country + " " + city).language("en");
        GeocodingResult[] results = request.await();
        if( results.length < 1){
            return null;
        }
        return results[0];
    }

    private GeoApiContext getGeoApiContext() {
        return new GeoApiContext.Builder().apiKey("AIzaSyCS2QW4mfmL_OWAvngQc-hw6xPzHurjTC8").build();
    }
}
