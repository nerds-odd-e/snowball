package com.odde.massivemailer.model;


import com.odde.massivemailer.model.base.Entity;
import com.odde.massivemailer.model.base.Repository;
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

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Participant extends Entity {
    private String contactPersonId;
    private ObjectId courseId;

    public static Repository<Participant> repository() {
        return new Repository<>(Participant.class, "participants");
    }

    ContactPerson getContactPerson() {
        return ContactPerson.findById(contactPersonId);
    }

    Course getCourse() {
        return Course.repository().findById(courseId);
    }

    public Participant saveIt() {
        repository().save(this);
        return this;
    }

    @Override
    public boolean onBeforeSave() {

        return true;
    }

    public static class ParticipantCodec implements Codec<Participant> {

        @Override
        public void encode(final BsonWriter writer, final Participant value, final EncoderContext encoderContext) {
            writer.writeStartDocument();
            writer.writeObjectId("_id", value.id);
            writer.writeName("courseId");
            writer.writeObjectId(value.courseId);
            writer.writeName("contactPersonId");
            writer.writeString(value.contactPersonId);
            writer.writeEndDocument();
        }

        @Override
        public Participant decode(final BsonReader reader, final DecoderContext decoderContext) {
            Participant visit = new Participant();
            reader.readStartDocument();
            visit.id = reader.readObjectId("_id");
            reader.readName();
            visit.courseId = reader.readObjectId();
            reader.readName();
            visit.contactPersonId = reader.readString();
            reader.readEndDocument();
            return visit;
        }

        @Override
        public Class<Participant> getEncoderClass() {
            return Participant.class;
        }
    }
}