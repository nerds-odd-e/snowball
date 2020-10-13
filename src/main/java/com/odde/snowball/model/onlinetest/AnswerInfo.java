package com.odde.snowball.model.onlinetest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class AnswerInfo {
    private ObjectId id;
    private String questionId="";
    private Date lastAnsweredDate=null;
    private int correctCount=0;
    private Date nextShowDate=null;

    public AnswerInfo(String questionId, Date lastAnsweredDate, int correctCount, Date nextShowDate) {
        this.id = new ObjectId();
        this.questionId = questionId;
        this.lastAnsweredDate = lastAnsweredDate;
        this.correctCount = correctCount;
        this.nextShowDate = nextShowDate;
    }

    public String stringId() {
        return id.toString();
    }

}