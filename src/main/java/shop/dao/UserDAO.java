package shop.dao;

import com.google.gson.*;
import shop.models.User;
import shop.util.DatabaseConnector;
import shop.util.JsonIO;

import java.sql.*;

public class UserDAO {

    public static boolean idExists(int id) {
        String sql = "select 1 from `user` where id=?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean idIsStaff(int id) {
        String sql = "select is_staff from `user` where id=?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getBoolean("is_staff");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // ID was not found
    }

    public static int getIdForPassword(String password) {
        JsonObject jsonData = JsonIO.readJson(JsonIO.getPwFileName());
        if (jsonData == null) {
            return -1; // JSON data not loaded
        }

        JsonArray users = jsonData.getAsJsonArray("users");

        for (int i = 0; i < users.size(); i++) {
            JsonObject user = users.get(i).getAsJsonObject();
            String user_password = user.get("password").getAsString();

            if (user_password.equals(password)) {
                return user.get("id").getAsInt();
            }
        }

        return -1; // Password not found
    }

    public static boolean passwordExists(String password) {
        return getIdForPassword(password) != -1;
    }

    // Add user info to both the database and JSON file
    public static void add(User user, String password) {
        add(user);
        add(password, user.getId());
    }

    // Add user info to both the database and JSON file with auto-generated ID; return the new user's id
    public static int add(boolean is_staff, String password) {
        int newId = add(is_staff);
        add(password, newId);

        return newId;
    }

    // Add user to the database
    private static void add(User user) {
        String sql = "insert into `user`(id, is_staff) values(?, ?)";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, user.getId());
            statement.setBoolean(2, user.isStaff());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add user to the database with auto-generated ID; return the ID of the inserted user
    private static int add(boolean isStaff) {
        String sql1 = "insert into `user`(is_staff) values(?)";
        String sql2 = "SELECT LAST_INSERT_ID()";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement1 = connection.prepareStatement(sql1);
             Statement statement2 = connection.createStatement()) {

            statement1.setBoolean(1, isStaff);
            statement1.executeUpdate();
            ResultSet resultSet = statement2.executeQuery(sql2);

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Add password and id to the JSON file
    public static void add(String password, int id) {

        // Read existing user/password entries
        String passwordsFile = JsonIO.getPwFileName();
        JsonObject jsonData = JsonIO.readJson(passwordsFile);

        // If the existing JSON data is null, create a new JSON object
        if (jsonData == null) {
            jsonData = new JsonObject();
        }

        // Get the "users" array or create it if it doesn't exist
        JsonArray usersArray = jsonData.getAsJsonArray("users");
        if (usersArray == null) {
            usersArray = new JsonArray();
            jsonData.add("users", usersArray);
        }

        // Create and add the new user entry to the array
        JsonObject userEntry = new JsonObject();
        userEntry.addProperty("id", id);
        userEntry.addProperty("password", password);
        usersArray.add(userEntry);

        // Write the updated JSON data back to the file
        JsonIO.writeJson(JsonIO.getPwFileName(), jsonData);
    }
}
