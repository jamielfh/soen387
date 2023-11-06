package shop.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;

public class DatabaseConnector {

    private static final JsonObject dbInfo;

    // Load data access configuration file only once
    static {
        try (InputStream inputStream = DatabaseConnector.class.getClassLoader().getResourceAsStream("db_config.json");
             InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(inputStream))) {
            dbInfo = JsonParser.parseReader(reader).getAsJsonObject();
        } catch (NullPointerException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String url = dbInfo.get("db_url").getAsString();
    private static final String user = dbInfo.get("db_user").getAsString();
    private static final String password = dbInfo.get("db_password").getAsString();

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
