package com.odde.massivemailer.model;


import com.odde.massivemailer.model.base.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static com.odde.massivemailer.model.base.Repository.repo;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Template extends Entity<Template> {
    private String templateName;
    private String subject;
    private String content;

    public static List<Template> findByTemplateName(String templateName) {
        return repo(Template.class).findBy("templateName", templateName);
    }

    public List<Mail> populateEmailTemplate(Course course, List<ContactPerson> courseParticipants) {
        List<Mail> mails = new ArrayList<>();

        String content;
        String contactEmailId;

        for (ContactPerson contactPerson : courseParticipants) {
            content = emailContentFromTemplate(contactPerson, course);
            contactEmailId = contactPerson.getEmail();

            Mail mail = Mail.createUpcomingCoursesEmail(content, contactEmailId);
            mail.setSubject(emailSubjectFromTemplate(contactPerson));

            mails.add(mail);
        }

        return mails;
    }


    private String emailContentFromTemplate(ContactPerson contactPerson, Course course) {

        String populatedContent = null;
        if (contactPerson != null) {
            populatedContent = this.content;

            populatedContent = populatedContent.replace("{FirstName}", (contactPerson.getFirstName() != null ? contactPerson.getFirstName() : "{FirstName}"));
            populatedContent = populatedContent.replace("{LastName}", (contactPerson.getLastName() != null ? contactPerson.getLastName() : "{LastName}"));
            populatedContent = populatedContent.replace("{CourseName}", (course.getCourseName() != null ? course.getCourseName() : "{CourseName}"));
            populatedContent = populatedContent.replace("{Instructor}", (course.getInstructor() != null ? course.getInstructor() : "{Instructor}"));
            populatedContent = populatedContent.replace("{Location}", (course.location() != null ? course.location() : "{Location}"));
        }

        return populatedContent;
    }

    private String emailSubjectFromTemplate(ContactPerson contactPerson) {
        String populatedSubject = this.subject;
        populatedSubject = populatedSubject.replace("{FirstName}", (contactPerson.getFirstName() != null ? contactPerson.getFirstName() : "{FirstName}"));

        return populatedSubject;
    }
}
