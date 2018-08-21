package com.odde.massivemailer.model.callback;

import com.odde.massivemailer.model.ApplicationModel;
import com.odde.massivemailer.model.Location;
import com.odde.massivemailer.service.LocationProviderService;
import org.javalite.activejdbc.CallbackAdapter;
import org.javalite.activejdbc.Model;

public class LocationCallbacks extends CallbackAdapter {
    @Override
    public void beforeValidation(Model m) {
        ApplicationModel model = (ApplicationModel)m;
        if (!model.anyDirtyAttributes("city", "country"))
            return;
        String city = model.getString("city");
        String country = model.getString("country");
        if (city == null)
            return;
        Location coordinate = new LocationProviderService().getCoordinate(city, country);
        model.set("latitude", coordinate.getLat());
        model.set("longitude", coordinate.getLng());

    }
}
