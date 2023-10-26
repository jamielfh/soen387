
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
        products.add(new Product("PU944", "Cat Recovery Suit", "One size fits all. Cat not included.", "Purrito", "cat-recovery-suit", 19.99, "https://i.etsystatic.com/33459047/r/il/81260e/4453593375/il_fullxfull.4453593375_260e.jpg"));
        products.add(new Product("LU441", "Portrait of Luna", "Print of the cat queen Luna. Measures 30x30 inches.", "Lunar Shadow", "luna-portrait", 39.99, "https://i.etsystatic.com/42801559/r/il/98915b/5245110747/il_1588xN.5245110747_r5cb.jpg"));
        products.add(new Product("PE125", "Catnip Crochet Toy", "Random color. Measures 2 inches long. Comes with catnip sachet.", "PetCo", "catnip-crochet-toy", 14.20, "https://i.etsystatic.com/20292668/r/il/224457/4737848035/il_1588xN.4737848035_ne6v.jpg"));
        return products;
    }
}



