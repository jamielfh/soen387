package shop.dao;

import shop.models.Order;
import shop.models.OrderProduct;
import shop.models.User;
import shop.database.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public int createOrder(Order order) {
        String sql = "insert into `order` (user_id, shipping_address, tracking_num) values(?, ?, ?)";
        String sql2 = "SELECT last_insert_rowid()";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             Statement statement2 = connection.createStatement()) {
            if (order.getUser() == null) {
                statement.setNull(1, Types.INTEGER);
            } else {
                statement.setInt(1, order.getUser().getId());
            }
            statement.setString(2, order.getShippingAddress());
            statement.setNull(3, Types.VARCHAR);

            statement.executeUpdate();
            int orderId = 0;

            ResultSet resultSet = statement2.executeQuery(sql2);

            if (resultSet.next()) {
                orderId = resultSet.getInt(1);
            }

            for (OrderProduct orderProduct : order.getOrderProducts()) {
                createOrderProduct(orderId, orderProduct);
            }

            return orderId;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void createOrderProduct(int orderId, OrderProduct orderProduct) {
        String sql = "insert into order_product (order_id, product_sku, qt) values(?, ?, ?)";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderId);
            statement.setString(2, orderProduct.getProduct().getSku());
            statement.setInt(3, orderProduct.getQuantity());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "select * from `order`";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order(
                        resultSet.getInt("id"),
                        resultSet.getString("shipping_address"),
                        resultSet.getString("tracking_num"),
                        UserDAO.getUserFromId(resultSet.getInt("user_id")),
                        getOrderProducts(resultSet.getInt("id"))
                );
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    public List<Order> getOrders(User user) {
        List<Order> orders = new ArrayList<>();
        String sql = "select * from `order` where user_id = ?";

        try (Connection connection = DatabaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order(
                        resultSet.getInt("id"),
                        resultSet.getString("shipping_address"),
                        resultSet.getString("tracking_num"),
                        user,
                        getOrderProducts(resultSet.getInt("id"))
                );
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    public Order getOrder(int id) {
        Order order = null;
        String sql = "select * from `order` where id = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                order = new Order(
                        resultSet.getInt("id"),
                        resultSet.getString("shipping_address"),
                        resultSet.getString("tracking_num"),
                        UserDAO.getUserFromId(resultSet.getInt("user_id")),
                        getOrderProducts(resultSet.getInt("id"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    public List<OrderProduct> getOrderProducts(int orderId) {
        List<OrderProduct> orderProducts = new ArrayList<>();
        String sql = "select * from order_product where order_id = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                OrderProduct orderProduct = new OrderProduct(
                        new ProductDAO().getBySku(resultSet.getString("product_sku")),
                        resultSet.getInt("qt")
                );
                orderProducts.add(orderProduct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderProducts;
    }

    public void setOrderOwner(int id, int userId) {
        String sql = "update `order` set user_id = ? where id = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void shipOrder(int id, String trackingNumber) {
        String sql = "update `order` set tracking_num = ? where id = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, trackingNumber);
            statement.setInt(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
