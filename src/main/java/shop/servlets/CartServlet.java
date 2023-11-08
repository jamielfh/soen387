
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
import shop.models.CartProduct;
import shop.models.Product;
import shop.models.StorefrontFacade;
import shop.models.User;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CartServlet", value = "/cart/products/*")
public class CartServlet extends HttpServlet {
    //private StorefrontFacade facade = new StorefrontFacade();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        StorefrontFacade facade = (StorefrontFacade) context.getAttribute("storefrontFacade");
        String pathInfo = request.getPathInfo();
        User user = (User) context.getAttribute("user");

        // Request for the items in cart
        List<CartProduct> items = getCart(facade, user);
        request.setAttribute("items", items);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cart.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        StorefrontFacade facade = (StorefrontFacade) context.getAttribute("storefrontFacade");
        String pathInfo = request.getPathInfo();
        User user = (User) context.getAttribute("user");

        String slug = pathInfo.substring(1); // Removing leading "/"
        try {
            Product product = getProductBySlug(facade, slug);
            addProductToCart(facade, user, product.getSku());
            response.sendRedirect("/cart/products/");
        } catch (ProductNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Implementation for handling DELETE requests
        ServletContext context = request.getServletContext();
        StorefrontFacade facade = (StorefrontFacade) context.getAttribute("storefrontFacade");
        String pathInfo = request.getPathInfo();
        User user = (User) context.getAttribute("user");

        String slug = pathInfo.substring(1); // Removing leading "/"
        try {
            Product product = getProductBySlug(facade, slug);
            removeProductFromCart(facade, user, product.getSku());
        } catch (ProductNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
        }
    }

    private List<CartProduct> getCart(StorefrontFacade facade, User user) {
        // Get all products from the facade
        return facade.getCart(user);
    }

    private Product getProductBySlug(StorefrontFacade facade, String slug) throws ProductNotFoundException {
        // Get a product by slug from the facade
        return facade.getProductBySlug(slug);
    }

    private void addProductToCart(StorefrontFacade facade, User user, String sku) {
        facade.addProductToCart(user, sku);
    }

    private void removeProductFromCart(StorefrontFacade facade, User user, String sku) {
        facade.removeProductFromCart(user, sku);
    }

    private void setProductQuantityInCart(StorefrontFacade facade, User user, String sku, int quantity) {
        facade.setProductQuantityInCart(user, sku, quantity);
    }

    private void clearCart(StorefrontFacade facade, User user) {
        facade.clearCart(user);
    }
}