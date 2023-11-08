package shop.util;

import com.google.gson.*;
import shop.dao.UserDAO;

import java.io.*;
import java.nio.file.Paths;

public class JsonIO {

    private static final String PASSWORDS_FILE_NAME = "passwords.json";
    private static final String DB_CONFIG_FILE_NAME = "db_config.json";
    private static final String ROOT_RESOURCES_PATH = "/src/main/resources/";
    private static final JsonObject dbInfo;

    // Load data access configuration file only once
    static {
        dbInfo = JsonIO.readJson(JsonIO.getConfigFileName());
    }

    public static String getDbUrl() {
        return dbInfo.get("db_url").getAsString();
    }

    public static String getDbUser() {
        return dbInfo.get("db_user").getAsString();
    }

    public static String getDbPw() {
        return dbInfo.get("db_password").getAsString();
    }

    public static String getRootPath() {
        return dbInfo.get("project_root").getAsString();
    }

    public static String getConfigFileName() {
        return DB_CONFIG_FILE_NAME;
    }

    public static String getPwFileName() {
        return PASSWORDS_FILE_NAME;
    }

    public static JsonObject readJson(String fileName) {
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

    public static void writeJson(String fileName, JsonObject jsonData) {

        // Write inside the project root's resources directory
        String absoluteFile = getRootPath() + ROOT_RESOURCES_PATH + fileName;
        System.out.println(absoluteFile);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Paths.get(absoluteFile).toFile()))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(jsonData, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
