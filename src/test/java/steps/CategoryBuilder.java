package steps;

import com.odde.snowball.model.onlinetest.Category;
import org.bson.types.ObjectId;

public class CategoryBuilder {
    public CategoryBuilder() {
    }

    ObjectId categoryByName(String name) {
        return Category.getIdByName(name);
    }
}