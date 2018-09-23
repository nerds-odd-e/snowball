package com.odde.massivemailer.model.callback;

import com.odde.massivemailer.model.ApplicationModel;
import org.javalite.activejdbc.CallbackAdapter;
import org.javalite.activejdbc.Model;

import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;

public class QuestionCallbacks extends CallbackAdapter {

    @Override
    public void beforeValidation(Model m) {
        ApplicationModel model = (ApplicationModel)m;
        if (!model.anyDirtyAttributes("description", "advice", "is_multi_question"))
            return;
        String advice = model.getString("advice");
        model.set("advice", defaultIfEmpty(advice, ""));
    }
}
