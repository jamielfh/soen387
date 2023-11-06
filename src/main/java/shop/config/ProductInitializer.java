package shop.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import shop.models.Product;
import shop.models.StorefrontFacade;
import shop.util.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebListener
public class ProductInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            // Explicitly register the jdbc driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Initialize the list of products based on the database
        List<Product> products = new ArrayList<>();
        try (Connection dbConnection = DatabaseConnector.getConnection();
             ResultSet productData = dbConnection.createStatement().executeQuery("select * from product;")) {
            while (productData.next()) {
                Product product = new Product(
                        productData.getString("sku"),
                        productData.getString("name"),
                        productData.getString("description"),
                        productData.getString("vendor"),
                        productData.getString("slug"),
                        productData.getDouble("price"),
                        productData.getString("img_url")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Store the list of products in the application context
        ServletContext context = sce.getServletContext();
        context.setAttribute("products", products);

        // Store the list of products in the StorefrontFacade
        StorefrontFacade facade = new StorefrontFacade();
        facade.setProducts(products);
        context.setAttribute("storefrontFacade", facade);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try (Connection dbConnection = DatabaseConnector.getConnection()) {
            // Unregister the JDBC driver to prevent memory leaks
            Driver jdbcDriver = DriverManager.getDriver(dbConnection.getMetaData().getURL());
            DriverManager.deregisterDriver(jdbcDriver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
