package com.odde.massivemailer.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized.Parameter;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class LocationTest {
    private Location baseCity;

    @Before
    public void initialize() {
        baseCity = new Location("Base City",0.0,0.0);
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                // lat  |  lng  |  expectedDistance  |
                //===========================================
                {   0,     1.0,    111},
                { 1.0,       0,    111},
                {   0,       0,      0}
        });
    }

    @Parameter
    public double lat;

    @Parameter(1)
    public double lng;

    @Parameter(2)
    public int expectedDistance;

    @Test
    public void testDistanceBetweenCities() {
        Location city = new Location("Another City", lat, lng);
        int distance = baseCity.distanceFrom(city);
        Assert.assertEquals(distance,expectedDistance);
    }

}
