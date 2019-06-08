package com.odde.massivemailer.model.onlinetest;

import com.odde.massivemailer.model.base.Entity;
import com.odde.massivemailer.model.base.Repository;
import com.odde.massivemailer.model.base.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question extends Entity {

    @NotNull(message="Description cannot be empty")
    @NotBlank(message="Description cannot be empty")
    private String description;
    private String advice;
    @NotNull(message="Category cannot be empty")
    @NotBlank(message="Category cannot be empty")
    private ObjectId categoryId;
    private boolean isMultiQuestion;
    private boolean isApproved;

    @Override
    public boolean onBeforeSave() {
//        if(isEmpty(description)) {
//            throw new ValidationException("`description` cannot be empty");
//        }
//        if(categoryId == null) {
//            throw new ValidationException("`categoryId` cannot be empty");
//        }
        return true;
    }

    public static Repository<Question> repository() {
        return new Repository<>(Question.class, "questions");
    }

    static Question getById(ObjectId questionId) {
        Question question = repository().findById(questionId);
        if(question == null) {
           throw new IllegalArgumentException("No question found by given id.");
        }
        return question;
    }

    public Category getCategory() {
        return Category.repository().findById(categoryId);
    }

    public String getCategoryName() {
        Category category = getCategory();
        if (category == null) {
            return "";
        }
        return category.getName();
    }

    public Collection<QuestionOption> getOptions() {
        return QuestionOption.getForQuestion(this.getId());
    }

    public boolean verifyAnswer(List<ObjectId> answeredOptionIds) {
        Collection<QuestionOption> optionsByQuestionId = getOptions();
        List<ObjectId> collectOptions = optionsByQuestionId.stream().filter(QuestionOption::isCorrect).map(answerOption -> answerOption.getId()).collect(toList());
        return collectOptions.equals(answeredOptionIds);
    }

    public ArrayList<ObjectId> getCorrectOption() {
        Collection<QuestionOption> optionsByQuestionId = getOptions();
        final ArrayList<ObjectId> correctOptions = new ArrayList<>();
        for (QuestionOption option : optionsByQuestionId) {
            if (option.isCorrect()) {
                correctOptions.add(option.getId());
            }
        }
        return correctOptions;
    }

    public boolean isCorrect(String optionId) {
        Collection<QuestionOption> optionsByQuestionId = getOptions();
        final ArrayList<String> correctOptions = new ArrayList<>();
        for (QuestionOption option : optionsByQuestionId) {
            if (option.isCorrect()) {
                correctOptions.add(option.getStringId());
            }
        }
        return correctOptions.contains(optionId);
    }

    public Question saveIt() {
        repository().save(this);
        return this;
    }

    public void createWrongOption(String optionText) {
        new QuestionOption(getId(), optionText, false).saveIt();
    }

    public void createCorrectOption(String optionText) {
        new QuestionOption(getId(), optionText, true).saveIt();
    }

    boolean belongsTo(Category cat) {
        return cat.getId().equals(categoryId);
    }

    public static class QuestionCodec implements Codec<Question> {
        @Override
        public void encode(final BsonWriter writer, final Question value, final EncoderContext encoderContext) {
            writer.writeStartDocument();
            writer.writeObjectId("_id", value.id);
            writer.writeName("description");
            writer.writeString(value.description);
            writer.writeName("advice");
            writer.writeString(defaultIfEmpty(value.advice, ""));
            writer.writeName("categoryId");
            writer.writeObjectId(value.categoryId);
            writer.writeName("isMultiQuestion");
            writer.writeBoolean(value.isMultiQuestion);
            writer.writeName("isApproved");
            writer.writeBoolean(value.isApproved);
            writer.writeEndDocument();
        }

        @Override
        public Question decode(final BsonReader reader, final DecoderContext decoderContext) {
            Question option = new Question();
            reader.readStartDocument();
            option.id = reader.readObjectId("_id");
            reader.readName();
            option.description = reader.readString();
            reader.readName();
            option.advice = reader.readString();
            reader.readName();
            option.categoryId = reader.readObjectId();
            reader.readName();
            option.isMultiQuestion = reader.readBoolean();
            reader.readName();
            option.isApproved = reader.readBoolean();
            reader.readEndDocument();
            return option;
        }

        @Override
        public Class<Question> getEncoderClass() {
            return Question.class;
        }
    }

}
