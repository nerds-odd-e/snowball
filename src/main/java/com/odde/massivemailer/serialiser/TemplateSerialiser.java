package com.odde.massivemailer.serialiser;

import com.odde.massivemailer.model.Template;

import java.util.Set;

public class TemplateSerialiser extends ActiveSerialiser {
    @Override
    protected Set<String> getAttributeNames() {
        return Template.getMetaModel().getAttributeNamesSkip();
    }
}
