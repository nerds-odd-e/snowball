package com.odde.massivemailer.model.onlinetest;

public class CategoryTestResult {

    public int categoryId;
    public int questionCount;

    CategoryTestResult(int categoryId) {
        this.categoryId = categoryId;
        this.questionCount = 0;
    }
}
