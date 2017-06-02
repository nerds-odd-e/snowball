package com.odde.massivemailer.model;


import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;


import java.util.ArrayList;
import java.util.List;

@Table("template")
public class Template extends Model {

   public void setTemplateName(String templateName) {
        set("templateName", templateName);
    }

    public void setSubject(String subject) {
        set("subject", subject);
    }

    public void setContent(String content) {
        set("content", content);
    }

    public static List<Template> findByTemplateName(String templateName) {
        return where("templateName = ?", templateName);
    }

    public Template() {

    }

    public Template(String templateName, String subject, String content) {
        setTemplateName(templateName);
        setSubject(subject);
        setContent(content);
    }


    public List<Mail> getPopulatedEmailTemplate(Course course, List<ContactPerson> courseParticipants) {
        List<Mail> mails = new ArrayList<Mail>();

        String content;
        String contactEmailId;

        for (ContactPerson contactPerson : courseParticipants) {
            content = getEmailContentFromTemplate(contactPerson, course);
            contactEmailId = contactPerson.getEmail();

            Mail mail = Mail.createEventMail(content, contactEmailId);
            mail.setSubject(getEmailSubjectFromTemplate(contactPerson));

            mails.add(mail);
        }

        return mails;
    }


    private String getEmailContentFromTemplate(ContactPerson contactPerson, Course course) {

        String content = null;
        if (contactPerson != null) {
        content = (String) this.get("content");

        content = content.replace("{FirstName}", (contactPerson.getName() != null ? contactPerson.getName() : "{FirstName}") );
        content = content.replace("{LastName}", (contactPerson.getLastname() != null ? contactPerson.getLastname() : "{LastName}" ) );
        content = content.replace("{CourseName}", (course.getCoursename() != null ? course.getCoursename() : "{CourseName}")) ;
        content = content.replace("{Instructor}", (course.getInstructor() != null ? course.getInstructor() : "{Instructor}" ));
        content = content.replace("{Location}", (course.getLocation() != null ? course.getLocation() : "{Location}" ));
    }

        return content;
    }

    private String getEmailSubjectFromTemplate(ContactPerson contactPerson) {
        String subject  = (String) this.get("subject");
        subject = subject.replace("{FirstName}", (contactPerson.getName() != null ? contactPerson.getName() : "{FirstName}") );

        return subject;
    }

}
