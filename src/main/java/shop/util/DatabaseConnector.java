package shop.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnector {

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(JsonIO.getDbUrl(), JsonIO.getDbUser(), JsonIO.getDbPw());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
