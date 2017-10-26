package com.odde.massivemailer.service.impl;

import static org.junit.Assert.*;

import com.odde.massivemailer.model.Location;
import com.odde.massivemailer.service.LocationProviderService;
import org.junit.Before;
import org.junit.Test;

public class LocationProviderServiceTest {

    LocationProviderService locationProviderService;

    public LocationProviderServiceTest(){
        locationProviderService = new LocationProviderService();
    }

    @Before
    public void setUp() {
        LocationProviderService.resetLocations();
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
        assertEquals("\"Bangkok\", \"Jarkata\", \"Kuala Lumpur\", \"Singapore\", \"Singapore/Singapore\", \"Thailand/Bangkok\"", locationProviderService.getCloseByLocationStrings("Singapore"));
        assertEquals("\"Bangkok\", \"Hanoi\", \"Kuala Lumpur\", \"Singapore\", \"Singapore/Singapore\", \"Thailand/Bangkok\"", locationProviderService.getCloseByLocationStrings("Bangkok"));
        assertEquals("\"Japan/Tokyo\", \"Seoul\", \"Shanghai\", \"Tokyo\"", locationProviderService.getCloseByLocationStrings("Tokyo"));
        assertEquals("\"Bangalore\", \"New Delhi\"", locationProviderService.getCloseByLocationStrings("Bangalore"));
        assertEquals("\"Beijing\", \"Seoul\", \"Shanghai\"", locationProviderService.getCloseByLocationStrings("Beijing"));
        assertEquals("", locationProviderService.getCloseByLocationStrings("Bukit Batok West Ave 6"));
    }

    @Test
    public void addLocationByName() {
        locationProviderService.addLocationByName("Japan/Osaka");
        Location storedLocation = locationProviderService.getLocationForName("Japan/Osaka");
        assertNotNull(storedLocation);
        assertEquals(new Double(34.41), storedLocation.getLatitude());
        assertEquals(new Double(135.31), storedLocation.getLongitude());
    }

}
