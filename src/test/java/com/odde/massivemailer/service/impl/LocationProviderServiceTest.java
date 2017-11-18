package com.odde.massivemailer.service.impl;

import static org.junit.Assert.*;

import com.odde.massivemailer.model.Location;
import com.odde.massivemailer.service.LocationProviderService;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

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
    public void getSingaporeByName() throws Exception{
        Location singapore = locationProviderService.getLocationForName("Singapore/Singapore");
        assertNotNull(singapore);
        assertEquals("Singapore/Singapore", singapore.getName());
    }

    @Test
    public void getKyotoByName() throws Exception{
        Location london = locationProviderService.getLocationForName("Japan/Kyoto");
        assertNotNull(london);
        assertEquals("Kyoto", london.getName());
    }

    @Test
    public void getUnknownCity() throws Exception {
        Location location = locationProviderService.getLocationForName("foobar/hogehoge");
        assertNull(location);
    }

    @Test
    public void getLocationNotContainsDelimiterByName() throws Exception {
        Location foobar = locationProviderService.getLocationForName("Kyoto");
        assertNull(foobar);
    }

    @Test
    public void getClosebyLocations() {
        assertEquals("\"Jakarta\", \"Kuala Lumpur\", \"Singapore/Singapore\", \"Thailand/Bangkok\"", locationProviderService.getCloseByLocationStrings("Singapore/Singapore"));
        assertEquals("\"Hanoi\", \"Kuala Lumpur\", \"Singapore/Singapore\", \"Thailand/Bangkok\"", locationProviderService.getCloseByLocationStrings("Thailand/Bangkok"));
        assertEquals("\"Bangalore\", \"New Delhi\"", locationProviderService.getCloseByLocationStrings("Bangalore"));
        assertEquals("\"Beijing\", \"Seoul\", \"Shanghai\"", locationProviderService.getCloseByLocationStrings("Beijing"));
        assertEquals("", locationProviderService.getCloseByLocationStrings("Bukit Batok West Ave 6"));
    }

    @Test
    public void addLocationByName() {
        locationProviderService.addLat_LongToMemory("Japan", "Kobe");
        Location storedLocation = locationProviderService.getLocationForName("Japan/Kobe");
        assertNotNull(storedLocation);
        assertTrue(34.690083 == storedLocation.getLat());
        assertTrue(135.1955112 == storedLocation.getLng());
    }

    @Test
    public void getAllCountryNames() throws IOException {
        List<String> countries = locationProviderService.getAllCountryNames();
        assertTrue(countries.contains("Netherlands Antilles"));
    }
}
