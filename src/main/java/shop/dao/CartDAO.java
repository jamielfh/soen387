package shop.dao;

import shop.models.*;
import shop.database.DatabaseConnector;

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
                        new ProductDAO().getBySku(resultSet.getString("product_sku")),
                        resultSet.getInt("qt")
                );
                cartProducts.add(cartProduct);
            }
        } catch (SQLException e) {
            return new Cart(user);
        }
        return new Cart(user, cartProducts);
    }

    // Check the quantity of the given product in the given user's cart. If cart entry doesn't exist, return null
    public Integer checkProductInCart(User user, String sku) {
        Integer qt = null;
        String sql = "select qt from `cart` where user_id = ? and product_sku = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getId());
            statement.setString(2, sku);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                qt = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return qt;
    }

    public void addProductToCart(User user, String sku) {
        String sql = "insert into cart (user_id, product_sku, qt) values(?, ?, ?)";

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
        String sql = "UPDATE `cart` SET qt = ? WHERE user_id = ? AND product_sku = ?;";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(2, user.getId());
            statement.setString(3, sku);
            statement.setInt(1, quantity);
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
