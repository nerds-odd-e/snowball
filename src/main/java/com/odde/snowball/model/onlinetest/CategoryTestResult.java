package com.odde.snowball.model.onlinetest;

public class CategoryTestResult {

    public String categoryId;
    public int questionCount;

    CategoryTestResult(String categoryId) {
        this.categoryId = categoryId;
        this.questionCount = 0;
    }
}
