package shop.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import shop.models.Product;
import shop.models.StorefrontFacade;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebListener
public class ProductInitializer implements ServletContextListener {
    Connection dbConnection;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        // Connect to database based on access details from configuration file
        JsonObject dbInfo = readJsonFile("/Users/carmen/IdeaProjects/soen387/src/main/resources/db_config.json");
        dbConnection = connectToDb(
                dbInfo.get("db_url").getAsString(),
                dbInfo.get("db_user").getAsString(),
                dbInfo.get("db_password").getAsString()
        );

        // Initialize the list of products based on the database
        List<Product> products = initializeProducts();

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
        // Cleanup code, if needed
    }

    private Connection connectToDb(String url, String user, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private JsonObject readJsonFile(String filename) {
        try {
            FileReader reader = new FileReader(filename);
            return JsonParser.parseReader(reader).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
            // Return an empty object as a fallback
            return new JsonObject();
        }
    }

    private List<Product> initializeProducts() {
        List<Product> products = new ArrayList<>();

        // Map product table data to Product objects and add them to the list
        try {
            ResultSet productData = dbConnection.createStatement().executeQuery("select * from product;");

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
            e.printStackTrace();
        }

        return products;
    }
}
