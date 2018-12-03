package steps.driver;

import com.odde.TestWithDB;
import com.odde.massivemailer.startup.DBMigrater;
import org.javalite.activejdbc.Base;
import org.junit.runners.model.InitializationError;

import java.util.List;
import java.util.Map;

public class DBConnectionFactory {
    public static void prepare() {
        Base.open();
        cleanDB();
    }

    private static void cleanDB() {
        List<Map> tables = Base.findAll("show tables");
        tables.forEach(table->{table.values().stream().filter(name-> name != "migration_info").forEach(name ->{ Base.exec("delete from " + name); });});
        Base.exec("INSERT INTO template (TemplateName,Subject,Content) VALUES ('Default Template 1', 'Greeting {FirstName}', 'Hi, {FirstName} {LastName} from {Company}')");
        Base.exec("INSERT INTO template (TemplateName,Subject,Content) VALUES ('RTA Default Template', 'Greeting {FirstName}', 'Hi, {FirstName} {LastName} from {Company}');");
        Base.exec("INSERT INTO template (TemplateName,Subject,Content) VALUES ('Pre-course Template', 'Greeting {FirstName}', 'Hi, {FirstName} {LastName} from {Company}');");
    }

    public static void close() {
        Base.close();
    }
}
