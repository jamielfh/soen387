package shop.servlets;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.dao.UserDAO;
import shop.models.CartProduct;
import shop.models.StorefrontFacade;
import shop.models.User;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String enteredPasscode = request.getParameter("passcode");
        int userId = new UserDAO().getUserFromPasscode(enteredPasscode).getId();

        if (userId != -1) {
            boolean isStaff = new UserDAO().idIsStaff(userId);
            User user = new User(userId, isStaff, enteredPasscode);

            // Merge user's cart with the past session cart if it exists
            List<CartProduct> pastCart = (List<CartProduct>) request.getSession().getAttribute("anonCart");
            if (pastCart != null && !pastCart.isEmpty()) {
                ServletContext context = request.getServletContext();
                StorefrontFacade facade = (StorefrontFacade) context.getAttribute("storefrontFacade");
                facade.mergeCart(user, pastCart);
            }

            request.getSession().setAttribute("user", user);

            if (isStaff) {
                response.sendRedirect(request.getContextPath() + "/admin/home");
            } else {
                response.sendRedirect(request.getContextPath());
            }
        } else {
            // Set an error attribute and re-forward the request to the login page with the error message
            request.setAttribute("error", "Invalid passcode. Please try again.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        }
    }
}
