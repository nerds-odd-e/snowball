package com.odde.snowball.model.base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.beans.Transient;
import java.util.Map;
import java.util.Objects;

import static com.odde.snowball.model.base.Repository.repo;

@Getter
@Setter
public abstract class Entity<T> {
    protected ObjectId id = null;

    public void onBeforeSave() {
    }

    @Transient
    public String stringId() {
        return getId().toString();
    }

    public Map<String, Object> asAMap() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(this, new TypeReference<Map<String, Object>>() {
        });
    }

    public T save() throws ValidationException {
        repository().save(this);
        return (T) this;
    }

    protected <S extends Entity> Repository<S> repository() {
        return (Repository<S>) repo(this.getClass());
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

}
