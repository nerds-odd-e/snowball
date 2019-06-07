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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static com.mongodb.client.model.Filters.eq;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SentMail extends Entity {
    private Date sentDate;
    private String subject;
    private String content;
    private Long messageId;
    private String receivers;

    public static Repository<SentMail> repository() {
        return new Repository<>(SentMail.class, "sent_mails");
    }

    public void addEmailAddress(final String emailAddress) {
        SentMailVisit sentMailVisit = new SentMailVisit();
        sentMailVisit.setEmailAddress(emailAddress);

        addVisit(sentMailVisit);
    }

    public void addVisit(final SentMailVisit sentMailVisit) {
        sentMailVisit.setSentMailId(getId());
        sentMailVisit.saveIt();
    }


    public String extract() {
        ArrayList<String> sarray = new ArrayList<>();
        int count = 0;
        for (SentMailVisit detail : getSentMailVisits()) {
            count += detail.getReadCount();
            sarray.add(detail.toJSON());
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        TimeZone tz = TimeZone.getTimeZone("Asia/Singapore");
        dateFormat.setTimeZone(tz);
        String date = null;
        if (getSentDate() != null) {
            date = dateFormat.format(getSentDate());
        }
        return "{\"subject\":\"" + getSubject() + "\", \"sent_at\":\"" + date + "\", \"total_open_count\":" + count + ", \"emails\":[" + String.join(", ", sarray) + "]}";
    }

    public List<SentMailVisit> getSentMailVisits() {
        return SentMailVisit.repository().find(eq("sentMailId", getId()));
    }

    public static SentMail getSentMailBy(String email) {
        return repository().findFirstBy("receivers", email);
    }

    public SentMail saveIt() {
        repository().save(this);
        return this;
    }

    @Override
    public boolean onBeforeSave() {

        return true;
    }

    public static class SentMailCodec implements Codec<SentMail> {
        @Override
        public void encode(final BsonWriter writer, final SentMail value, final EncoderContext encoderContext) {
            if (value.sentDate == null) {
                value.sentDate = new Date();
            }
            writer.writeStartDocument();
            writer.writeObjectId("_id", value.id);
            writer.writeName("sentDate");
            writer.writeDateTime(value.sentDate.getTime());
            writer.writeName("subject");
            writer.writeString(value.subject);
            writer.writeName("content");
            writer.writeString(value.content);
            writer.writeName("messageId");
            writer.writeInt64(value.messageId);
            writer.writeName("receivers");
            writer.writeString(value.receivers);
            writer.writeEndDocument();
        }

        @Override
        public SentMail decode(final BsonReader reader, final DecoderContext decoderContext) {
            SentMail visit = new SentMail();
            reader.readStartDocument();
            visit.id = reader.readObjectId("_id");
            reader.readName();
            visit.sentDate = new Date(reader.readDateTime());
            reader.readName();
            visit.subject = reader.readString();
            reader.readName();
            visit.content = reader.readString();
            reader.readName();
            visit.messageId = reader.readInt64();
            reader.readName();
            visit.receivers = reader.readString();
            reader.readEndDocument();
            return visit;
        }

        @Override
        public Class<SentMail> getEncoderClass() {
            return SentMail.class;
        }
    }
}
