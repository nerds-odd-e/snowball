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

import static com.odde.massivemailer.model.base.Repository.repo;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Participant extends Entity<Participant> {
    private ObjectId contactPersonId;
    private ObjectId courseId;

    ContactPerson getContactPerson() {
        return repo(ContactPerson.class).findById(contactPersonId);
    }

    Course getCourse() {
        return repo(Course.class).findById(courseId);
    }

    @Override
    public void onBeforeSave() {
    }

    public static class ParticipantCodec implements Codec<Participant> {

        @Override
        public void encode(final BsonWriter writer, final Participant value, final EncoderContext encoderContext) {
            writer.writeStartDocument();
            writer.writeObjectId("_id", value.id);
            writer.writeName("courseId");
            writer.writeObjectId(value.courseId);
            writer.writeName("contactPersonId");
            writer.writeObjectId(value.contactPersonId);
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
            visit.contactPersonId = reader.readObjectId();
            reader.readEndDocument();
            return visit;
        }

        @Override
        public Class<Participant> getEncoderClass() {
            return Participant.class;
        }
    }
}