package shop.util;

import com.google.gson.*;
import shop.dao.UserDAO;

import java.io.*;
import java.nio.file.Paths;

public class JsonIO {

    private static final String DB_CONFIG_FILE_NAME = "db_config.json";
    private static final JsonObject dbInfo;

    // Load data access configuration file only once
    static {
        dbInfo = JsonIO.readJsonResource(JsonIO.getConfigFileName());
    }

    public static String getDbUrl() {
        return "jdbc:sqlite:" + dbInfo.get("db_path").getAsString();
    }

    public static String getConfigFileName() {
        return DB_CONFIG_FILE_NAME;
    }

    public static JsonObject readJsonResource(String fileName) {
        InputStream inputStream = UserDAO.class.getClassLoader().getResourceAsStream(fileName);

        if (inputStream == null) {
            return null; // File doesn't exist
        }

        try (InputStreamReader reader = new InputStreamReader(inputStream)) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            if (jsonElement.isJsonObject()) {
                return jsonElement.getAsJsonObject();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null; // File is empty or JSON data is invalid
    }

    public static JsonObject readJson(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            if (jsonElement.isJsonObject()) {
                return jsonElement.getAsJsonObject();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null; // File is empty or JSON data is invalid
    }

    public static void writeJson(String filePath, JsonObject jsonData) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Paths.get(filePath).toFile()))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(jsonData, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
