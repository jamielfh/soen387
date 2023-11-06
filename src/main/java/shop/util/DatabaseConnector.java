package shop.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnector {
    public static Connection getConnection() {
        try {
            // Read data access details from configuration file
            String filepath = DatabaseConnector.class.getClassLoader().getResource("db_config.json").getPath();
            FileReader reader = new FileReader(filepath);
            JsonObject dbInfo = JsonParser.parseReader(reader).getAsJsonObject();
            String url =  dbInfo.get("db_url").getAsString();
            String user =  dbInfo.get("db_user").getAsString();
            String password = dbInfo.get("db_password").getAsString();

            // Establish database connection based on the above
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
