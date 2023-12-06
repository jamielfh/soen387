package shop.database;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

public class Database implements DatabaseConnector {

    private static final DataSource DATASOURCE;

    static {
        try {
            DATASOURCE = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/database");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Connection getConnection() {
        try {
            return DATASOURCE.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
