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

import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SentMailVisit extends Entity {
    private String emailAddress;
    private int readCount;
    private ObjectId sentMailId;

    public static Repository<SentMailVisit> repository() {
        return new Repository<>(SentMailVisit.class, "sent_mail_visits");
    }

    public String toJSON() {
        return "{\"email\": \"" + getEmailAddress() + "\", \"open_count\": " + getReadCount() + "}";
    }

    public void updateViewCount() {
        setReadCount(getReadCount() + 1);
        saveIt();
    }

    public SentMailVisit saveIt() {
        repository().save(this);
        return this;
    }

    @Override
    public void onBeforeSave() {

    }

    public static class SentMailVisitCodec implements Codec<SentMailVisit> {
        @Override
        public void encode(final BsonWriter writer, final SentMailVisit value, final EncoderContext encoderContext) {
            writer.writeStartDocument();
            writer.writeObjectId("_id", value.id);
            writer.writeName("emailAddress");
            writer.writeString(value.emailAddress);
            writer.writeName("readCount");
            writer.writeInt32(value.readCount);
            writer.writeName("sentMailId");
            writer.writeObjectId(value.sentMailId);
            writer.writeEndDocument();
        }

        @Override
        public SentMailVisit decode(final BsonReader reader, final DecoderContext decoderContext) {
            SentMailVisit visit = new SentMailVisit();
            reader.readStartDocument();
            visit.id = reader.readObjectId("_id");
            reader.readName();
            visit.emailAddress = reader.readString();
            reader.readName();
            visit.readCount = reader.readInt32();
            reader.readName();
            visit.sentMailId = reader.readObjectId();
            reader.readEndDocument();
            return visit;
        }

        @Override
        public Class<SentMailVisit> getEncoderClass() {
            return SentMailVisit.class;
        }
    }

}
