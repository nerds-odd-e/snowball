package steps;

import com.odde.massivemailer.model.onlinetest.Category;

public class CategoryBuilder {
    public CategoryBuilder() {
    }

    String categoryByName(String name) {
        return String.valueOf(Category.getIdByName(name));
    }
}