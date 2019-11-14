package com.odde.snowball.model.onlinetest;

import java.util.List;

public class TestResult {
    private List<UserAnswer> answers;

    public TestResult(List<UserAnswer> answers) {
        this.answers = answers;
    }

    public long getCorrectAnswerCount() {
        return answers.stream().filter(UserAnswer::isCorrect).count();
    }

    public int correctPercentage() {
        return (int) (getCorrectAnswerCount() * 100 / getTotal());
    }

    public int getTotal() {
        return answers.size();
    }

    public String showFinalMessage() {
        int correctPercentage = correctPercentage();
        if (correctPercentage < 85) {
            return "基本を学びなおしましょう";
        } else if (correctPercentage == 100) {
            return "あなたはスクラムマスター！";
        }
        return "あともう少し";
    }

}
