package shop.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.models.CartProduct;
import shop.models.StorefrontFacade;
import shop.models.User;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CheckoutServlet", value = "/cart/checkout")
public class CheckoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        StorefrontFacade facade = (StorefrontFacade) context.getAttribute("storefrontFacade");
        User user = (User) request.getSession().getAttribute("user");

        // Request for the items in cart
        List<CartProduct> items = getCart(facade, user);
        request.setAttribute("items", items);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/checkout.jsp");
        dispatcher.forward(request, response);
    }

    private List<CartProduct> getCart(StorefrontFacade facade, User user) {
        // Get all products from the facade
        return facade.getCart(user);
    }
}
