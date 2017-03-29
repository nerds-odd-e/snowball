package com.odde.massivemailer.model;

import org.junit.Assert;
import org.junit.Test;

public class LocationTest {
    Location singapore = new Location("Singapore",0,0);

    @Test
    public void distanceFromSingaporeToKL(){
        Location kl = new Location("KL",0,1.0);
        int distance = singapore.distanceFrom(kl);
        Assert.assertEquals(distance,111);
    }

    @Test
    public void distanceFromSingaporeToJB(){
        Location kl = new Location("JB",0,2.0);
        int distance = singapore.distanceFrom(kl);
        Assert.assertEquals(distance,222);
    }

    @Test
    public void distanceFromSingaporeToSingapore(){
        int distance = singapore.distanceFrom(singapore);
        Assert.assertEquals(distance,0);
    }

}
