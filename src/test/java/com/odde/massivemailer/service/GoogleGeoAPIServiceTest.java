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
        assert location.getLat() == Location.INVALID_LATITUDE;
        assert location.getLng() == Location.INVALID_LONGTITUDE;
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