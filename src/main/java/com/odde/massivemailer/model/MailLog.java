package com.odde.massivemailer.model;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

import java.util.List;
import java.util.Map;

@Table("mail_logs")
public class MailLog extends Model {
    public static List<Map> getReport() {
        return Base.findAll("SELECT c.email, m.sent_at, c.location, COUNT(m.course_id) AS course_count FROM mail_logs m INNER JOIN contact_people c ON m.contact_person_id = c.id GROUP BY m.contact_person_id, m.sent_at");
    }
}
