package com.odde.massivemailer.model.callback;

import com.odde.massivemailer.model.Location;
import com.odde.massivemailer.service.LocationProviderService;
import org.javalite.activejdbc.CallbackAdapter;
import org.javalite.activejdbc.Model;

public class LocationCallbacks extends CallbackAdapter {
    @Override
    public void beforeValidation(Model m) {
        String city = m.getString("city");
        String country = m.getString("country");
        if (city == null)
            return;
        Location coordinate = new LocationProviderService().getCoordinate(LocationProviderService.locationString(city, country));
        m.set("latitude", coordinate.getLat());
        m.set("longitude", coordinate.getLng());

    }
}
