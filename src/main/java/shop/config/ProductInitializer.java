package shop.config;

import jakarta.servlet.annotation.WebListener;
import shop.models.Product;
import shop.models.StorefrontFacade;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import java.util.ArrayList;
import java.util.List;

@WebListener
public class ProductInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Initialize the list of products
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

    private List<Product> initializeProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("SKU1", "Product1", "Description1", "Vendor1", "slug1", 19.99));
        products.add(new Product("SKU2", "Product2", "Description2", "Vendor2", "slug2", 29.99));
        products.add(new Product("SKU3", "Product3", "Description3", "Vendor3", "slug3", 39.99));
        return products;
    }
}


