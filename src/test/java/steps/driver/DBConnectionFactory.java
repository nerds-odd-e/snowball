package steps.driver;

import org.javalite.activejdbc.Base;

public class DBConnectionFactory {
    public static void prepare() {
        Base.open();
        cleanDB();
    }

    private static void cleanDB() {
        Base.exec("DELETE FROM template");
        Base.exec("INSERT INTO template (TemplateName,Subject,Content) VALUES ('Default Template 1', 'Greeting {FirstName}', 'Hi, {FirstName} {LastName} from {Company}')");
        Base.exec("INSERT INTO template (TemplateName,Subject,Content) VALUES ('RTA Default Template', 'Greeting {FirstName}', 'Hi, {FirstName} {LastName} from {Company}');");
        Base.exec("DELETE FROM sent_mail_visits");
        Base.exec("DELETE FROM sent_mails");
        Base.exec("DELETE FROM contact_people");
        Base.exec("DELETE FROM courses");
    }

    public static void close() {
        Base.close();
    }
}
