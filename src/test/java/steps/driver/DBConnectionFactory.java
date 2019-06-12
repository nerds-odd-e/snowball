package steps.driver;

import org.javalite.activejdbc.Base;

public class DBConnectionFactory {
    public static void prepare() {
        Base.open();
        cleanDB();
    }

    private static void cleanDB() {
        Base.exec("DELETE FROM todos");
    }

    public static void close() {
        Base.close();
    }
}
