package com.odde.massivemailer.model.onlinetest;

import com.odde.massivemailer.model.base.Entity;
import com.odde.massivemailer.model.base.Repository;
import com.odde.massivemailer.model.base.ValidationException;
import lombok.*;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionOption extends Entity<QuestionOption> {

    private String description;
    private boolean isCorrect;
    private ObjectId questionId;

    @Override
    public boolean onBeforeSave() {
        if(isEmpty(description)) {
            throw new ValidationException("`description` cannot be empty");
        }
        if(questionId == null) {
            throw new ValidationException("`questionId` cannot be empty");
        }
        return true;
    }

    public static Repository<QuestionOption> repository() {
        return new Repository<>(QuestionOption.class, "questionOptions");
    }

    private QuestionOption(String description, boolean isCorrect) {
        setDescription(description);
        setCorrect(isCorrect);
    }

    public QuestionOption(ObjectId questionId, String description, boolean isCorrect) {
        setQuestionId(questionId);
        setDescription(description);
        setCorrect(isCorrect);
    }

    static Collection<QuestionOption> getForQuestion(ObjectId questionId) {
        return repository().getCollection().find(eq("questionId", questionId)).into(new ArrayList<QuestionOption>());
    }

    static Optional<QuestionOption> getById(String optionId) {
        return Optional.ofNullable(repository().findByStringId(optionId));
    }

    public static QuestionOption create(String description, boolean isCorrect) {
        return new QuestionOption(description, isCorrect);
    }

    public static QuestionOption createIt(ObjectId questionId, String description, boolean isCorrect) {
        return new QuestionOption(questionId, description, isCorrect).saveIt();
    }

    void addToQuestion(ObjectId questionId) {
        setQuestionId(questionId);
        saveIt();
    }

    public QuestionOption saveIt() {
        repository().save(this);
        return this;
    }

    public static class QuestionOptionCodec implements Codec<QuestionOption> {
        @Override
        public void encode(final BsonWriter writer, final QuestionOption value, final EncoderContext encoderContext) {
            writer.writeStartDocument();
            writer.writeObjectId("_id", value.id);
            writer.writeName("questionId");
            writer.writeObjectId(value.getQuestionId());
            writer.writeName("description");
            writer.writeString(value.description);
            writer.writeName("isCorrect");
            writer.writeBoolean(value.isCorrect);
            writer.writeEndDocument();
        }

        @Override
        public QuestionOption decode(final BsonReader reader, final DecoderContext decoderContext) {
            QuestionOption option = new QuestionOption();
            reader.readStartDocument();
            option.id = reader.readObjectId("_id");
            reader.readName();
            option.questionId = reader.readObjectId();
            reader.readName();
            option.description = reader.readString();
            reader.readName();
            option.isCorrect = reader.readBoolean();
            reader.readEndDocument();
            return option;
        }

        @Override
        public Class<QuestionOption> getEncoderClass() {
            return QuestionOption.class;
        }
    }

}
