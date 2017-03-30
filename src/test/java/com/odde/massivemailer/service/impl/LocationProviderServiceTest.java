package com.odde.massivemailer.service.impl;

import static org.junit.Assert.*;

import com.odde.massivemailer.model.Location;
import com.odde.massivemailer.service.LocationProviderService;
import org.junit.Test;

public class LocationProviderServiceTest {

    LocationProviderService locationProviderService;

    public LocationProviderServiceTest(){
        locationProviderService = new LocationProviderService();
    }

    @Test
    public void getSupportedLocations() throws Exception {
        assert(locationProviderService.getSupportedLocations().size()>0);
    }

    @Test
    public void getLocationByName() throws Exception{
        Location singapore = locationProviderService.getLocationForName("Singapore");
        assertNotNull(singapore);
        assertEquals("Singapore", singapore.getName());

        assertNull(locationProviderService.getLocationForName("London"));
    }

    @Test
    public void getClosebyLocations() {
        assertEquals("\"Bangkok\", \"Singapore\"", locationProviderService.getCloseByLocationStrings("Singapore"));
        assertEquals("\"Bangkok\", \"Singapore\"", locationProviderService.getCloseByLocationStrings("Bangkok"));
        assertEquals("\"Tokyo\"", locationProviderService.getCloseByLocationStrings("Tokyo"));
        assertEquals("", locationProviderService.getCloseByLocationStrings("Jarkata"));
    }

}
