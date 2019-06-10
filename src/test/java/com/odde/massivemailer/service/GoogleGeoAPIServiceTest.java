package com.odde.massivemailer.service;

import com.odde.massivemailer.model.Location;
import org.junit.Test;

import static org.junit.Assert.*;

public class GoogleGeoAPIServiceTest {

    @Test
    public void getGeocodeNull() throws Exception {
        GoogleGeoAPIService geoAPIService = new GoogleGeoAPIService();

        Location location = geoAPIService.getGeocode(null, null);

        assert location.getLatitude() == null;
        assert location.getLongitude() == null;
    }

    @Test
    public void getGeocodeAigle() throws Exception {
        GoogleGeoAPIService geoAPIService = new GoogleGeoAPIService();

        Location location = geoAPIService.getGeocode("switzerland", "aigle");

        assertEquals("CH", location.getCountryCode());
        assertEquals("Switzerland", location.getCountry());
        assert location.getLatitude() == 46.3190253;
        assert location.getLongitude() == 6.970566;
    }





}