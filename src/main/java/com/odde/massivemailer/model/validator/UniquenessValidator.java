package com.odde.massivemailer.model.validator;

import org.javalite.activejdbc.DB;
import org.javalite.activejdbc.MetaModel;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.ModelDelegate;
import org.javalite.activejdbc.validation.ValidatorAdapter;

/*
 * Code forked from https://github.com/javalite/activejdbc/blob/master/activejdbc/src/main/java/org/javalite/activejdbc/validation/UniquenessValidator.java
 * to fix a bug for SQLite.
 *
 * Issue has been reported to: https://github.com/javalite/activejdbc/issues/234
 *
 * This can be removed when the bug is fixed in ActiveJDBC.
 */
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
