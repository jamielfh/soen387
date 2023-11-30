package shop.dao;

import shop.models.Cart;
import shop.models.CartProduct;
import shop.models.Product;
import shop.models.User;
import shop.util.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public static User getUserFromId(int userId) {
        User user = null;
        String sql = "select * from user where id = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User(
                        userId,
                        resultSet.getBoolean("is_staff"),
                        resultSet.getString("passcode")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static User getUserFromPasscode(String passcode) {
        User user = null;
        String sql = "select * from user where passcode = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, passcode);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User(
                        resultSet.getInt("id"),
                        resultSet.getBoolean("is_staff"),
                        passcode
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static boolean passcodeExists(String passcode) {
        return getUserFromPasscode(passcode) != null;
    }

    public static int createUser(boolean isStaff, String passcode) {
        String sql = "insert into user (is_staff, passcode) values(?, ?)";
        String sql2 = "SELECT last_insert_rowid()";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             Statement statement2 = connection.createStatement()) {
            statement.setBoolean(1, isStaff);
            statement.setString(2, passcode);

            statement.executeUpdate();
            ResultSet resultSet = statement2.executeQuery(sql2);

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void changePasscode(User user, String passcode) {
        String sql = "update user set passcode = ? where id = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, passcode);
            statement.setInt(2, user.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<User> getAllCustomers() {
        List<User> customers = new ArrayList<>();
        String sql = "select * from user where is_staff = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, false);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User customer = new User(
                        resultSet.getInt("id"),
                        resultSet.getBoolean("is_staff")
                );
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public static List<User> getAllStaff() {
        List<User> staff = new ArrayList<>();
        String sql = "select * from user where is_staff = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, true);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User staffMember = new User(
                        resultSet.getInt("id"),
                        resultSet.getBoolean("is_staff")
                );
                staff.add(staffMember);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staff;
    }

    public static void changePermission(User user, boolean isStaff) {
        String sql = "update user set is_staff = ? where id = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, isStaff);
            statement.setInt(2, user.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
