package com.odde.massivemailer.service;

import com.odde.massivemailer.model.Course;
import com.odde.massivemailer.model.MailLog;

import java.util.Date;
import java.util.List;

public class MailLogService {

    public boolean saveLog(int contact_person_id, int course_id, Date sent_at){
        MailLog mailLog = new MailLog();
        mailLog.set("contact_person_id", contact_person_id);
        mailLog.set("sent_at", sent_at);
        mailLog.set("course_id", course_id);
        return mailLog.saveIt();
    }

    public boolean saveLogs(int contact_person_id, List<Course> courses) {
        boolean result;
        Date sent_at = new Date();
        for(Course course : courses){
            result = saveLog(contact_person_id, (Integer) course.getId(), sent_at);
            if (!result){
                return false;
            }
        }
        return true;
    }
}
