package shop.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import shop.models.StorefrontFacade;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Store the StorefrontFacade in the application context
        ServletContext context = sce.getServletContext();
        StorefrontFacade facade = new StorefrontFacade();
        context.setAttribute("storefrontFacade", facade);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
