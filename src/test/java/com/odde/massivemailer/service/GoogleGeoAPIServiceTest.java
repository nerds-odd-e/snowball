package com.odde.massivemailer.service;

import com.odde.massivemailer.model.Location;
import org.junit.Test;

import static org.junit.Assert.*;

public class GoogleGeoAPIServiceTest {

    @Test
    public void getGeocodeNull() throws Exception {
        GoogleGeoAPIService geoAPIService = new GoogleGeoAPIService();

        Location location = geoAPIService.getGeocode(null, null);

        assertNull(location.getName());
        assert location.getLat() == null;
        assert location.getLng() == null;
    }

    @Test
    public void getGeocodeAigle() throws Exception {
        GoogleGeoAPIService geoAPIService = new GoogleGeoAPIService();

        Location location = geoAPIService.getGeocode("switzerland", "aigle");

        assertEquals("Aigle", location.getName());
        assertEquals("CH", location.getCountryCode());
        assertEquals("Switzerland", location.getCountryName());
        assert location.getLat() == 46.3190253;
        assert location.getLng() == 6.970566;
    }





}