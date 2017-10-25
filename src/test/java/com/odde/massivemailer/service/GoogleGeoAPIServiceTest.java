package com.odde.massivemailer.service;

import com.google.maps.GeoApiContext;
import com.odde.massivemailer.model.Location;
import org.junit.Test;

import static org.junit.Assert.*;

public class GoogleGeoAPIServiceTest {

    @Test
    public void getGeocodeNull() throws Exception {
        GoogleGeoAPIService geoAPIService = new GoogleGeoAPIService();

        Location location = geoAPIService.getGeocode(null, null);

        assertEquals(location.getName(), null);
        assert location.getLat() == 0;
        assert location.getLng() == 0;
    }

    @Test
    public void getGeocodeAigle() throws Exception {
        GoogleGeoAPIService geoAPIService = new GoogleGeoAPIService();

        Location location = geoAPIService.getGeocode("switzerland", "aigle");

        System.out.println("name:" + location.getName());
        System.out.println("countryCode:" + location.getCountryCode());
        System.out.println("countryName:" + location.getCountryName());
        System.out.println("lat:" + location.getLat());
        System.out.println("lang:" + location.getLng());

        assertEquals("Aigle", location.getName());
        assertEquals("CH", location.getCountryCode());
        assertEquals("Switzerland", location.getCountryName());
        assert location.getLat() == 46.3190253;
        assert location.getLng()==6.970566;
    }





}