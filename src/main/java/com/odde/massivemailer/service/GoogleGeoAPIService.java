package com.odde.massivemailer.service;

import com.google.maps.errors.ApiException;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.odde.massivemailer.model.Location;
import com.google.maps.*;

import java.io.IOException;

public class GoogleGeoAPIService {

    public Location getGeocode(String country, String city) {
        if( country != null ){
            return getLocationFromGoogle(country, city);
        }

        return new Location(null, 0, 0);
    }

    private Location getLocationFromGoogle(String country, String city){
        GeocodingResult geocodingResult = getResult(country, city);

        AddressComponent[] addressComponents = geocodingResult.addressComponents;
        Location location =  new Location();

        for ( AddressComponent addressComponent : addressComponents) {
            if (addressComponent.types[0].equals(AddressComponentType.COUNTRY)) {
                location.setCountryCode(addressComponent.shortName);
                location.setCountryName(addressComponent.longName);
            }
            if (addressComponent.types[0].equals(AddressComponentType.LOCALITY)) {
                location.setName(addressComponent.longName);
            }
        }
        LatLng latLng = geocodingResult.geometry.location;
        location.setLat(latLng.lat);
        location.setLng(latLng.lng);

        return location;
    }

    private GeocodingResult getResult(String country, String city){
        GeocodingApiRequest request = GeocodingApi.newRequest(getGeoApiContext()).address(country + " " + city).language("en");

        GeocodingResult[] results = new GeocodingResult[0];
        try {
            results = request.await();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return results[0];
    }

    private GeoApiContext getGeoApiContext(){
        return new GeoApiContext.Builder().apiKey("AIzaSyCS2QW4mfmL_OWAvngQc-hw6xPzHurjTC8").build();
    }


}
