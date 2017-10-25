package com.odde.massivemailer.service;

import com.odde.massivemailer.model.Location;
import org.junit.Test;

import static org.junit.Assert.*;

public class GoogleGeoAPIServiceTest {
    @Test
    public void getGeocode() throws Exception {
        GoogleGeoAPIService geoAPIService = new GoogleGeoAPIService();

        Location location = geoAPIService.getGeocode(null,null);

        assert location.getName() == null;

        location = geoAPIService.getGeocode("switzerland","aigle");

        assert location.getName() == "aigle";
    }

}