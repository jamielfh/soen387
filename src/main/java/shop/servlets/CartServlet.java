package shop.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import shop.exceptions.ProductNotFoundException;
import shop.models.CartProduct;
import shop.models.Product;
import shop.models.StorefrontFacade;
import shop.models.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CartServlet", value = "/cart/products/*")
public class CartServlet extends HttpServlet {
    //private StorefrontFacade facade = new StorefrontFacade();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        StorefrontFacade facade = (StorefrontFacade) context.getAttribute("storefrontFacade");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        List<CartProduct> sessionCart = (List<CartProduct>) session.getAttribute("anonCart");
        List<CartProduct> items;

        if (user != null) {
            // User is logged in: Request for the items in cart
            items = getCart(facade, user);
        } else if (sessionCart == null) {
            // User is not logged in & has no cart: Create empty cart and store in session
            items = new ArrayList<>();
            session.setAttribute("anonCart", items);
        } else {
            // User is not logged in & has a cart: Request for the items in the session cart
            items = sessionCart;
        }

        request.setAttribute("items", items);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cart.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        ServletContext context = request.getServletContext();
        StorefrontFacade facade = (StorefrontFacade) context.getAttribute("storefrontFacade");
        String pathInfo = request.getPathInfo();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String quantity = request.getParameter("quantity");

        String slug = pathInfo.substring(1); // Removing leading "/"
        Product product;
        try {
            product = getProductBySlug(facade, slug);
        } catch (ProductNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
            return;
        }

        if (user != null) {

            // User is logged in, cart is in database
            if (quantity == null) {
                addProductToCart(facade, user, product.getSku());
            } else {
                int qty = Integer.parseInt(quantity);
                setProductQuantityInCart(facade, user, product.getSku(), qty);
            }
        } else {

            // User is logged out, cart is in session

            // Retrieve or create cart in the session
            if (session.getAttribute("anonCart") == null) {
                session.setAttribute("anonCart", new ArrayList<>());
            }
            List<CartProduct> sessionCart = (List<CartProduct>) session.getAttribute("anonCart");

            // Determine if the product was already added to cart
            boolean productInCart = false;
            for (CartProduct cartProduct : sessionCart) {
                if (cartProduct.getProduct().getSku().equals(product.getSku())) {
                    productInCart = true;
                    break;
                }
            }

            if (quantity == null && productInCart) {

                // Increment quantity of existing product
                for (CartProduct cartProduct : sessionCart) {
                    if (cartProduct.getProduct().getSku().equals(product.getSku())) {
                        cartProduct.setQuantity(cartProduct.getQuantity() + 1);
                        break;
                    }
                }
            } else if (quantity == null) {

                // Add the new product with quantity 1
               sessionCart.add(new CartProduct(product, 1));

            } else if (Integer.parseInt(quantity) <= 0 && productInCart) {

                // Remove the existing product
                for (int i = 0; i < sessionCart.size(); i++) {
                    if (sessionCart.get(i).getProduct().getSku().equals(product.getSku())) {
                        sessionCart.remove(i);
                        break;
                    }
                }
            } else if (Integer.parseInt(quantity) > 0 && productInCart) {

                // Modify quantity of the existing product
                for (CartProduct cartProduct : sessionCart) {
                    if (cartProduct.getProduct().getSku().equals(product.getSku())) {
                        cartProduct.setQuantity(Integer.parseInt(quantity));
                        break;
                    }
                }
            } else if (Integer.parseInt(quantity) > 0 && !productInCart) {

                // Add the new product with the given quantity
                sessionCart.add(new CartProduct(product, Integer.parseInt(quantity)));
            }
        }

        response.sendRedirect("/cart/products/");
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // Implementation for handling DELETE requests
        ServletContext context = request.getServletContext();
        StorefrontFacade facade = (StorefrontFacade) context.getAttribute("storefrontFacade");
        String pathInfo = request.getPathInfo();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        String slug = pathInfo.substring(1); // Removing leading "/"

        if (user != null) {
            // User is logged in
            if (slug.equals("clear-cart")) {
                clearCart(facade, user);
            } else {
                try {
                    Product product = getProductBySlug(facade, slug);
                    removeProductFromCart(facade, user, product.getSku());
                } catch (ProductNotFoundException e) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                }
            }
        } else if (session.getAttribute("anonCart") != null) {

            // User is logged out and the user's cart exists in the session
            if (slug.equals("clear-cart")) {

                // Reset cart to empty list
               session.setAttribute("anonCart", new ArrayList<CartProduct>());
            } else {
                try {
                    List<CartProduct> sessionCart = (List<CartProduct>) session.getAttribute("anonCart");
                    Product product = getProductBySlug(facade, slug);

                    // Remove product
                    for (int i = 0; i < sessionCart.size(); i++) {
                        if (sessionCart.get(i).getProduct().getSku().equals(product.getSku())) {
                            sessionCart.remove(i);
                            break;
                        }
                    }
                } catch (ProductNotFoundException e) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                }
            }
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