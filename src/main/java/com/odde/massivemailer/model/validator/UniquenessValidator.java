package com.odde.massivemailer.model.validator;

import org.javalite.activejdbc.DB;
import org.javalite.activejdbc.MetaModel;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.validation.ValidatorAdapter;

import static org.javalite.activejdbc.ModelDelegate.metaModelOf;

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
        setMessage("should be unique");
    }

    @Override
    public void validate(Model model) {
        MetaModel metaModel = metaModelOf(model.getClass());
        long id = 0;
        if(model.getId() != null) {
            id = model.getLongId();
        }
        if (new DB(metaModel.getDbName()).count(metaModel.getTableName(), attribute + " = ? AND "+ model.getIdName()+" != ?", model.get(attribute), id) > 0) {
            model.addValidator(this, attribute);
        }
    }
}
