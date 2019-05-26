package com.odde.massivemailer.model.base;

import com.odde.massivemailer.model.base.Repository;
import com.odde.massivemailer.model.onlinetest.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.Objects;

@Getter
@Setter
public abstract class Entity<T> {
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
}
