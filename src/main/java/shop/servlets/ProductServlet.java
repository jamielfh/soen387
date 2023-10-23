
package shop.servlets;

import shop.exceptions.ProductNotFoundException;
import shop.models.Product;
import shop.models.StorefrontFacade;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "productServlet", value = "/products")
public class ProductServlet extends HttpServlet {
    //private StorefrontFacade facade = new StorefrontFacade();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        StorefrontFacade facade = (StorefrontFacade) context.getAttribute("storefrontFacade");
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            // Request for the product list
            List<Product> products = getProducts(facade);
            request.setAttribute("products", products);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/products.jsp");
            dispatcher.forward(request, response);
        } else {
            // Request for a specific product
            String slug = pathInfo.substring(1); // Removing leading "/"
            Product product = null;
            try {
                product = getProductBySlug(facade, slug);
            } catch (ProductNotFoundException e) {
                throw new RuntimeException(e);
            }

            if (product != null) {
                request.setAttribute("product", product);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/product.jsp");
                dispatcher.forward(request, response);
            } else {
                // Handle case where product is not found
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
            }
        }
    }

    private List<Product> getProducts(StorefrontFacade facade) {
        // Get all products from the facade
        return facade.getAllProducts();
    }

    private Product getProductBySlug(StorefrontFacade facade, String slug) throws ProductNotFoundException {
        // Get a product by slug from the facade
        return facade.getProductBySlug(slug);
    }
}

