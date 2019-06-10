package com.odde.massivemailer.model.base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.odde.massivemailer.model.onlinetest.Category;
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

import static com.odde.massivemailer.model.base.Repository.repo;

@Getter
@Setter
public abstract class Entity<T> {
    static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    protected ObjectId id = null;

    public abstract void onBeforeSave();

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
        onBeforeSave();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Entity>> validate = validator.validate(this);
        if (validate.size() > 0) {
            throw new ValidationException(validate);
        }

        return true;
    }

    protected <T extends Entity>Repository<T> repository() {
        return (Repository<T>) repo(this.getClass());
    }

    public T saveIt() {
        repository().save(this);
        return (T)this;
    }

    public void save() throws ValidationException {
        saveIt();
    }
}
