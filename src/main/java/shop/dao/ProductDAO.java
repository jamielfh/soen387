package shop.dao;

import shop.models.Product;
import shop.database.DatabaseConnector;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public Product getBySku(String sku) {
        String sql = "select * from product where sku = ?";
        return getProduct(sku, sql);
    }

    public Product getBySlug(String slug) {
        String sql = "select * from product where slug = ?";
        return getProduct(slug, sql);
    }

    private Product getProduct(String param, String sql) {
        Product product = null;

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, param);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                product = new Product(
                        resultSet.getString("sku"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getString("vendor"),
                        resultSet.getString("slug"),
                        resultSet.getBigDecimal("price"),
                        resultSet.getString("img_url"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    public boolean add(String sku, String name, String description, String vendor, String slug, BigDecimal price, String imageURL) {
        String sql = "insert into product (sku, name, description, vendor, slug, price, img_url) values(?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, sku);
            statement.setString(2, name);
            statement.setString(3, description);
            statement.setString(4, vendor);
            statement.setString(5, slug);
            statement.setString(7, imageURL);

            if (price != null) {
                statement.setBigDecimal(6, price);
            } else {
                statement.setNull(6, Types.DECIMAL);
            }
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean add(Product product) {
        return add(product.getSku(), product.getName(), product.getDescription(), product.getVendor(), product.getSlug(), product.getPrice(), product.getImageURL());
    }

    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();

        try (Connection dbConnection = DatabaseConnector.getConnection();
             Statement statement = dbConnection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from product;");
            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getString("sku"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getString("vendor"),
                        resultSet.getString("slug"),
                        resultSet.getBigDecimal("price"),
                        resultSet.getString("img_url")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public boolean update(String oldSku, String sku, String name, String description, String vendor, String slug, BigDecimal price) {
        String sql = "update product set name=?, description=?, vendor=?, slug=?, price=?, sku=? where sku=?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, description);
            statement.setString(3, vendor);
            statement.setString(4, slug);
            statement.setBigDecimal(5, price);
            statement.setString(6, sku);
            statement.setString(7, oldSku);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated != 0;
        } catch (SQLException e) {
           return false;
        }
    }

}
