package com.odde.massivemailer.model;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

import java.util.List;
import java.util.Map;

@Table("mail_logs")
public class MailLog extends Model {
    public static List<Map> getReport() {
        return Base.findAll("SELECT *, COUNT(contact_person_id) AS course_count FROM mail_logs GROUP BY contact_person_id, sent_at");
    }
}
