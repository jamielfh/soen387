package shop.database;

import java.sql.Connection;

public interface DatabaseConnector {
    public Connection getConnection();
}
