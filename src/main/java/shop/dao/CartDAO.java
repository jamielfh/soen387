package shop.dao;

import shop.models.*;
import shop.util.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    public Cart getCart(User user) {
        List<CartProduct> cartProducts = new ArrayList<>();
        String sql = "select * from cart where user_id = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CartProduct cartProduct = new CartProduct(
                        new ProductDAO().getBySKU(resultSet.getString("product_sku")),
                        resultSet.getInt("quantity")
                );
                cartProducts.add(cartProduct);
            }
        } catch (SQLException e) {
            return new Cart(user);
        }
        return new Cart(user, cartProducts);
    }

    public void addProductToCart(User user, String sku) {
        String sql = "insert into cart (user_id, product_sku, quantity) values(?, ?, ?)";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getId());
            statement.setString(2, sku);
            statement.setInt(3, 1);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeProductFromCart(User user, String sku) {
        String sql = "delete from cart where user_id = ? and product_sku = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getId());
            statement.setString(2, sku);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setProductQuantityInCart(User user, String sku, int quantity) {
        String sql = "insert into cart (user_id, product_sku, quantity) " +
                "values (?, ?, ?) " +
                "on duplicate key update " +
                "quantity = values(quantity)";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getId());
            statement.setString(2, sku);
            statement.setInt(3, quantity);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearCart(User user) {
        String sql = "delete from cart where user_id = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
