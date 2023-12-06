package shop.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DatabaseStub implements DatabaseConnector {

    private static final String JDBC_URL = "jdbc:sqlite::memory:"; // In-memory database
    private static final String INITIAL_SCRIPT_PATH = "initial_db_contents.sql";
    private final Connection CONNECTION;

    public DatabaseStub() {
        try {
            CONNECTION = DriverManager.getConnection(JDBC_URL);
        } catch (SQLException e) {
            throw new RuntimeException("DatabaseStub failed to establish a database connection", e);
        }
        executeSqlFile();
    }

    @Override
    public Connection getConnection() {
        return CONNECTION;
    }

    private void executeSqlFile() {
        // Read file contents
        String sql;
        try (Stream<String> lines = Files.lines(Path.of(DatabaseStub.INITIAL_SCRIPT_PATH))) {
            sql = lines.collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException("DatabaseStub failed to read SQL file", e);
        }

        // Remove all comments and line breaks
        sql = sql.replaceAll("--.*\n", "").replace("\n", " ");

        // Separate each statement
        String[] statements = sql.split(";");

        // Execute all statements
        try (Statement statement = CONNECTION.createStatement()) {
            for (String stmt : statements) {
                statement.addBatch(stmt);
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("DatabaseStub failed to execute the initialization SQL statements", e);
        }
    }
}


