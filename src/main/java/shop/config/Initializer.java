package shop.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import shop.models.StorefrontFacade;
import shop.util.DatabaseConnector;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            // Explicitly register the jdbc driver
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Store the StorefrontFacade in the application context
        ServletContext context = sce.getServletContext();
        StorefrontFacade facade = new StorefrontFacade();
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
