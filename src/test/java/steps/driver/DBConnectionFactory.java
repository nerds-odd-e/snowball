package steps.driver;

import com.odde.massivemailer.service.MongoDBConnector;
import org.javalite.activejdbc.Base;

public class DBConnectionFactory {
    public static void prepare() {
        MongoDBConnector.resetAll();
        Base.open();
        cleanDB();
    }

    private static void cleanDB() {
        Base.exec("DELETE FROM template");
        Base.exec("DELETE FROM sent_mail_visits");
        Base.exec("DELETE FROM sent_mails");
        Base.exec("DELETE FROM COURSE_CONTACT_PERSON");
        Base.exec("DELETE FROM contact_people");
        Base.exec("DELETE FROM courses");
        Base.exec("DELETE FROM user_questions");
        Base.exec("DELETE FROM public_questions");
        Base.exec("DELETE FROM category");
        Base.exec("DELETE FROM questions");
        Base.exec("DELETE FROM question_responses");
        Base.exec("DELETE FROM options");
    }

    public static void close() {
        Base.close();
    }
}
