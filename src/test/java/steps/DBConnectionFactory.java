package steps;

import org.javalite.activejdbc.Base;

public class DBConnectionFactory {
    public static void clean() {
        Base.open();
        try {
            cleanDB();
        }
        finally {
            Base.close();
        }
    }

    private static void cleanDB() {
        Base.exec("DELETE FROM Template");
        Base.exec("INSERT INTO Template (TemplateName,Subject,Content) VALUES ('Default Template 1', 'Greeting {FirstName}', 'Hi, {FirstName} {LastName} from {Company}')");
        Base.exec("INSERT INTO Template (TemplateName,Subject,Content) VALUES ('RTA Default Template', 'Greeting {FirstName}', 'Hi, {FirstName} {LastName} from {Company}');");
        Base.exec("DELETE FROM notification_details");
        Base.exec("DELETE FROM notifications");
        Base.exec("DELETE FROM events");
        Base.exec("DELETE FROM contact_people");
        Base.exec("DELETE FROM courses");
    }

}
