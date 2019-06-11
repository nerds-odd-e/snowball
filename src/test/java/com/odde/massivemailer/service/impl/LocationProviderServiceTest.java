package com.odde.snowball.service.impl;

import static org.junit.Assert.*;

import com.odde.snowball.model.Location;
import com.odde.snowball.service.LocationProviderService;
import com.odde.snowball.service.exception.GeoServiceException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class LocationProviderServiceTest {

    private final LocationProviderService locationProviderService;

    public LocationProviderServiceTest(){
        locationProviderService = new LocationProviderService();
    }

    @Before
    public void setUp() {
        LocationProviderService.resetCachedLocations();
    }

    @Test
    public void getSingaporeByName() throws Exception{
        Location singapore = locationProviderService.getLocationForName("Singapore/Singapore");
        assertNotNull(singapore);
    }

    @Test
    public void getKyotoByName() throws Exception{
        Location london = locationProviderService.getLocationForName("Japan/Kyoto");
        assertNotNull(london);
    }

    @Test
    public void getUnknownCity() throws Exception {
        Location location = locationProviderService.getLocationForName("foobar/hogehoge");
        assertNull(location.getLatitude());
        assertNull(location.getLongitude());
    }

    @Test
    public void getLocationNotContainsDelimiterByName() throws Exception {
        Location foobar = locationProviderService.getLocationForName("Kyoto");
        assertNull(foobar);
    }

    @Test
    public void addLocationByName() throws GeoServiceException {
        locationProviderService.addLat_LongToMemory("Japan", "Kobe");
        Location storedLocation = locationProviderService.getLocationForName("Japan/Kobe");
        assertNotNull(storedLocation);
        assertEquals(34.690083, storedLocation.getLatitude(), 0.1);
        assertEquals(135.1955112, storedLocation.getLongitude(), 0.1);
    }

    @Test
    public void getAllCountryNames() throws IOException {
        List<String> countries = locationProviderService.getAllCountryNames();
        assertTrue(countries.contains("Netherlands Antilles"));
    }
}
