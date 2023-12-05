package shop.database;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

public class Database {

    static DataSource dataSource;

    static {
        try {
            dataSource = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/database");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
