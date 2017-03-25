package com.odde.massivemailer.model.validator;

import org.javalite.activejdbc.DB;
import org.javalite.activejdbc.MetaModel;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.ModelDelegate;
import org.javalite.activejdbc.validation.ValidatorAdapter;

public class UniquenessValidator extends ValidatorAdapter {
    private final String attribute;

    public UniquenessValidator(String attribute) {
        this.attribute = attribute;
        this.setMessage("should be unique");
    }

    public void validate(Model model) {
        MetaModel metaModel = ModelDelegate.metaModelOf(model.getClass());

        if((new DB(metaModel.getDbName())).count(metaModel.getTableName(), this.attribute + " = ? AND " + metaModel.getIdName() + " IS NOT ?", new Object[]{model.get(this.attribute), model.get(metaModel.getIdName())}).longValue() > 0L) {
            model.addValidator(this, this.attribute);
        }

    }
}
