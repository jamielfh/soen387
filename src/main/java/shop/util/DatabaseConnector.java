package shop.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnector {

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(JsonIO.getDbUrl());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
