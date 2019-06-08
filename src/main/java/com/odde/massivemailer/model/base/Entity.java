package com.odde.massivemailer.model.base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
public abstract class Entity<T> {
    protected ObjectId id = null;

    public abstract boolean onBeforeSave();

    public ObjectId getId() {
        return id;
    }

    public String getStringId() {
        return getId().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Map<String, Object> asAMap() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(this, new TypeReference<Map<String, Object>>() {
        });
    }

    public boolean onBeforeSaveEve() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Entity<T>>> validate = validator.validate(this);
        if (validate.size() > 0) {
            throw new ValidationException(validate);
        }

        return onBeforeSave();
    }
}
