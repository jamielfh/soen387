package shop.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import shop.exceptions.UserDoesNotMatchOrderException;
import shop.models.Order;
import shop.models.StorefrontFacade;
import shop.models.User;

import java.io.IOException;

@WebServlet(name = "ShippingServlet", value = "/shipping/*")
public class ShippingServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null || !((User) session.getAttribute("user")).isStaff()) {
            // Staff is not logged in, deny access
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Access denied. You must be logged in to view this page.");
            return;
        }

        ServletContext context = request.getServletContext();
        StorefrontFacade facade = (StorefrontFacade) context.getAttribute("storefrontFacade");
        String pathInfo = request.getPathInfo();

        try {
            int orderId = Integer.parseInt(pathInfo.substring(1));
            Order order = getOrder(facade, orderId);
            request.setAttribute("order", order);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/staff/shipping.jsp");
            dispatcher.forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Order ID is not a number");
        } catch (UserDoesNotMatchOrderException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User does not match order");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null || !((User) session.getAttribute("user")).isStaff()) {
            // Staff is not logged in, deny access
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Access denied. You must be logged in to view this page.");
            return;
        }

        ServletContext context = request.getServletContext();
        StorefrontFacade facade = (StorefrontFacade) context.getAttribute("storefrontFacade");
        String pathInfo = request.getPathInfo();

        try {
            int orderId = Integer.parseInt(pathInfo.substring(1));
            String trackingNumber = request.getParameter("trackingNumber");
            shipOrder(facade, orderId, trackingNumber);
            response.sendRedirect("/orders/");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Order ID is not a number");
        }
    }

    private Order getOrder(StorefrontFacade facade, int orderId) throws UserDoesNotMatchOrderException {
        return facade.getOrder(null, orderId);
    }

    private void shipOrder(StorefrontFacade facade, int orderId, String trackingNumber) {
        facade.shipOrder(orderId, trackingNumber);
    }
}
