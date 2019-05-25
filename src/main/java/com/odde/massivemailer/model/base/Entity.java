package com.odde.massivemailer.model.base;

import com.odde.massivemailer.model.base.Repository;
import com.odde.massivemailer.model.onlinetest.Category;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.Objects;

@Getter
@Setter
public abstract class Entity<T> {
    protected ObjectId id = null;

    public ObjectId getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getStringId() {
        return getId().toString();
    }

}
