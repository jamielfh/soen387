
package shop.servlets;

import shop.exceptions.ProductNotFoundException;
import shop.exceptions.ProductSlugExistsException;
import shop.exceptions.ProductSlugInvalidException;
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

@WebServlet(name = "productServlet", value = "/products/*")
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
            try {
                Product product = getProductBySlug(facade, slug);
                request.setAttribute("product", product);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/product.jsp");
                dispatcher.forward(request, response);
            } catch (ProductNotFoundException e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        StorefrontFacade facade = (StorefrontFacade) context.getAttribute("storefrontFacade");
        String pathInfo = request.getPathInfo();

        String slug = pathInfo.substring(1); // Removing leading "/"
        String sku = request.getParameter("sku");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String vendor = request.getParameter("vendor");
        String newSlug = request.getParameter("slug");
        double price = Double.parseDouble(request.getParameter("price"));

        try {
            updateProduct(facade, sku, name, description, vendor, newSlug, price);
        } catch (ProductNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
        } catch (ProductSlugInvalidException ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product slug invalid");
        } catch (ProductSlugExistsException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product slug already in use");
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

    private void updateProduct(StorefrontFacade facade, String sku, String name, String description, String vendor, String slug, double price) throws ProductNotFoundException, ProductSlugInvalidException, ProductSlugExistsException {
        // Update product in the facade
        facade.updateProduct(sku, name, description, vendor, slug, price);
    }
}

