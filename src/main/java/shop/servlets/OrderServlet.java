package shop.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.exceptions.ProductNotFoundException;
import shop.exceptions.UserDoesNotMatchOrderException;
import shop.models.Order;
import shop.models.Product;
import shop.models.StorefrontFacade;
import shop.models.User;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "OrderServlet", value = "/orders/*")
public class OrderServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        StorefrontFacade facade = (StorefrontFacade) context.getAttribute("storefrontFacade");
        String pathInfo = request.getPathInfo();
        User user = (User) request.getSession().getAttribute("user");

        if (pathInfo == null || pathInfo.equals("/")) {
            // Request for the order list
            List<Order> orders;
            if (user.isStaff()) {
                // Request for all orders
                orders = getAllOrders(facade);
                request.setAttribute("orders", orders);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/staff/adminViewOrders.jsp");
                dispatcher.forward(request, response);
            } else {
                // Request for user's orders
                orders = getOrders(facade, user);
                request.setAttribute("orders", orders);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/viewOrders.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            // Request for a specific order
            int orderId = Integer.parseInt(pathInfo.substring(1)); // Removing leading "/"
            try {
                Order order;
                if (user.isStaff()) {
                    order = getOrder(facade, null, orderId);
                } else {
                    order = getOrder(facade, user, orderId);
                }
                request.setAttribute("order", order);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/viewOrderDetails.jsp");
                dispatcher.forward(request, response);
            } catch (UserDoesNotMatchOrderException e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User does not match order");
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        StorefrontFacade facade = (StorefrontFacade) context.getAttribute("storefrontFacade");
        User user = (User) request.getSession().getAttribute("user");

        String address = request.getParameter("address");
        String country = request.getParameter("country");
        String city = request.getParameter("city");
        String postal = request.getParameter("postal");

        String shippingAddress = String.format("%s, %s, %s %s", address, city, country, postal);
        createOrder(facade, user, shippingAddress);
        response.sendRedirect("/orders/");
    }



    private List<Order> getAllOrders(StorefrontFacade facade) {
        return facade.getAllOrders();
    }

    private List<Order> getOrders(StorefrontFacade facade, User user) {
        return facade.getOrders(user);
    }

    private Order getOrder(StorefrontFacade facade, User user, int id) throws UserDoesNotMatchOrderException {
        return facade.getOrder(user, id);
    }

    private void createOrder(StorefrontFacade facade, User user, String shippingAddress) {
        facade.createOrder(user, shippingAddress);
    }

}
