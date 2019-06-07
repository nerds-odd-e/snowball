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


import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Template extends Entity {
    private String templateName;
    private String subject;
    private String content;

    public static Repository<Template> repository() {
        return new Repository<>(Template.class, "email_templates");
    }

    public static List<Template> findByTemplateName(String templateName) {
        return repository().findBy("templateName", templateName);
    }

    public List<Mail> getPopulatedEmailTemplate(Course course, List<ContactPerson> courseParticipants) {
        List<Mail> mails = new ArrayList<>();

        String content;
        String contactEmailId;

        for (ContactPerson contactPerson : courseParticipants) {
            content = getEmailContentFromTemplate(contactPerson, course);
            contactEmailId = contactPerson.getEmail();

            Mail mail = Mail.createUpcomingCoursesEmail(content, contactEmailId);
            mail.setSubject(getEmailSubjectFromTemplate(contactPerson));

            mails.add(mail);
        }

        return mails;
    }


    private String getEmailContentFromTemplate(ContactPerson contactPerson, Course course) {

        String populatedContent = null;
        if (contactPerson != null) {
            populatedContent = this.content;

            populatedContent = populatedContent.replace("{FirstName}", (contactPerson.getName() != null ? contactPerson.getName() : "{FirstName}"));
            populatedContent = populatedContent.replace("{LastName}", (contactPerson.getLastname() != null ? contactPerson.getLastname() : "{LastName}"));
            populatedContent = populatedContent.replace("{CourseName}", (course.getCourseName() != null ? course.getCourseName() : "{CourseName}"));
            populatedContent = populatedContent.replace("{Instructor}", (course.getInstructor() != null ? course.getInstructor() : "{Instructor}"));
            populatedContent = populatedContent.replace("{Location}", (course.getLocation() != null ? course.getLocation() : "{Location}"));
        }

        return populatedContent;
    }

    private String getEmailSubjectFromTemplate(ContactPerson contactPerson) {
        String populatedSubject = this.subject;
        populatedSubject = populatedSubject.replace("{FirstName}", (contactPerson.getName() != null ? contactPerson.getName() : "{FirstName}"));

        return populatedSubject;
    }

    public void saveTemplate(String subject, String content) {
        setSubject(subject);
        setContent(content);
        saveIt();
    }

    public Template saveIt() {
        repository().save(this);
        return this;
    }

    @Override
    public boolean onBeforeSave() {

        return true;
    }

    public static class TemplateCodec implements Codec<Template> {
        @Override
        public void encode(final BsonWriter writer, final Template value, final EncoderContext encoderContext) {
            writer.writeStartDocument();
            writer.writeObjectId("_id", value.id);
            writer.writeName("templateName");
            writer.writeString(defaultIfEmpty(value.templateName, ""));
            writer.writeName("subject");
            writer.writeString(defaultIfEmpty(value.subject, ""));
            writer.writeName("content");
            writer.writeString(defaultIfEmpty(value.content, ""));
            writer.writeEndDocument();
        }

        @Override
        public Template decode(final BsonReader reader, final DecoderContext decoderContext) {
            Template template = new Template();
            reader.readStartDocument();
            template.id = reader.readObjectId("_id");
            reader.readName();
            template.templateName = reader.readString();
            reader.readName();
            template.subject = reader.readString();
            reader.readName();
            template.content = reader.readString();
            reader.readEndDocument();
            return template;
        }

        @Override
        public Class<Template> getEncoderClass() {
            return Template.class;
        }
    }

}
