package com.odde.massivemailer.service;

import com.odde.massivemailer.model.Location;

public class GoogleGeoAPIService {

    public Location getGeocode(String country, String city) {
        if( country != null ){
            return new Location("aigle", 0, 0);
        }

        return new Location(null, 0, 0);
    }



}
