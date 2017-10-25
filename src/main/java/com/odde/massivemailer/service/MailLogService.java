package com.odde.massivemailer.service;

import com.odde.massivemailer.model.Course;
import com.odde.massivemailer.model.MailLog;

import java.util.Date;
import java.util.List;

public class MailLogService {

    public static boolean saveLog(Object contact_person_id, Object course_id, Date sent_at){
        MailLog mailLog = new MailLog();
        mailLog.set("contact_person_id", contact_person_id);
        mailLog.set("sent_at", sent_at);
        mailLog.set("course_id", course_id);
        return mailLog.saveIt();
    }

    public static boolean saveLogs(Object contact_person_id, List<Course> courses) {
        boolean result;
        Date sent_at = new Date();
        for(Course course : courses){
            result = saveLog(contact_person_id, course.getId(), sent_at);
            if (!result){
                return false;
            }
        }
        return true;
    }
}
