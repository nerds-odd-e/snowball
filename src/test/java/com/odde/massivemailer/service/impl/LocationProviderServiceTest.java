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
        assertEquals("\"Bangkok\", \"Jarkata\", \"Kuala Lumpur\", \"Singapore\"", locationProviderService.getCloseByLocationStrings("Singapore"));
        assertEquals("\"Bangkok\", \"Hanoi\", \"Kuala Lumpur\", \"Singapore\"", locationProviderService.getCloseByLocationStrings("Bangkok"));
        assertEquals("\"Seoul\", \"Shanghai\", \"Tokyo\"", locationProviderService.getCloseByLocationStrings("Tokyo"));
        assertEquals("\"Bangalore\", \"New Delhi\"", locationProviderService.getCloseByLocationStrings("Bangalore"));
        assertEquals("\"Beijing\", \"Seoul\", \"Shanghai\"", locationProviderService.getCloseByLocationStrings("Beijing"));
        assertEquals("", locationProviderService.getCloseByLocationStrings("Bukit Batok West Ave 6"));
    }

    @Test
    public void addLocation() {
        locationProviderService.addLocation(new Location("JPN-Osaka", 34.41, 135.31));
        Location storedLocation = locationProviderService.getLocationForName("JPN-Osaka");
        assertNotNull(storedLocation);
        assertEquals(new Double(34.41), storedLocation.getLatitude());
        assertEquals(new Double(135.31), storedLocation.getLongitude());
    }

}
