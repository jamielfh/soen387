
package shop.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.exceptions.CartNotFoundException;
import shop.exceptions.ProductAlreadyInCartException;
import shop.exceptions.ProductNotFoundException;
import shop.exceptions.ProductNotFoundInCartException;
import shop.models.Product;
import shop.models.StorefrontFacade;

import java.io.IOException;
import java.util.List;

@WebServlet("/cart/products/*")
public class CartServlet extends HttpServlet {
    //private StorefrontFacade facade = new StorefrontFacade();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        StorefrontFacade facade = (StorefrontFacade) context.getAttribute("storefrontFacade");
        String pathInfo = request.getPathInfo();
        String user = request.getSession().getId();

        // Request for the items in cart
        List<Product> items = getCart(facade, user);
        request.setAttribute("items", items);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cart.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        StorefrontFacade facade = (StorefrontFacade) context.getAttribute("storefrontFacade");
        String pathInfo = request.getPathInfo();
        String user = request.getSession().getId();

        String slug = pathInfo.substring(1); // Removing leading "/"
        try {
            Product product = getProductBySlug(facade, slug);
            addProductToCart(facade, user, product.getSku());
        } catch (ProductNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
        } catch (ProductAlreadyInCartException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product already in cart");
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Implementation for handling DELETE requests
        ServletContext context = request.getServletContext();
        StorefrontFacade facade = (StorefrontFacade) context.getAttribute("storefrontFacade");
        String pathInfo = request.getPathInfo();
        String user = request.getSession().getId();

        String slug = pathInfo.substring(1); // Removing leading "/"
        try {
            Product product = getProductBySlug(facade, slug);
            removeProductFromCart(facade, user, product.getSku());
        } catch (ProductNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
        } catch (CartNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Cart not found");
        } catch (ProductNotFoundInCartException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found in cart");
        }
    }

    private List<Product> getCart(StorefrontFacade facade, String user) {
        // Get all products from the facade
        return facade.getCart(user);
    }

    private Product getProductBySlug(StorefrontFacade facade, String slug) throws ProductNotFoundException {
        // Get a product by slug from the facade
        return facade.getProductBySlug(slug);
    }

    private void addProductToCart(StorefrontFacade facade, String user, String sku) throws ProductNotFoundException, ProductAlreadyInCartException {
        facade.addProductToCart(user, sku);
    }

    private void removeProductFromCart(StorefrontFacade facade, String user, String sku) throws ProductNotFoundException, CartNotFoundException, ProductNotFoundInCartException {
        facade.removeProductFromCart(user, sku);
    }
}