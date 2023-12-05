package shop.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.exceptions.OrderAlreadyClaimedException;
import shop.exceptions.OrderDoesNotExistException;
import shop.exceptions.UserDoesNotMatchOrderException;
import shop.models.CartProduct;
import shop.models.Order;
import shop.models.StorefrontFacade;
import shop.models.User;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "OrderServlet", value = "/orders/*")
public class OrderServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        StorefrontFacade facade = (StorefrontFacade) context.getAttribute("storefrontFacade");
        String pathInfo = request.getPathInfo();
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            // User is not logged in, deny access
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Access denied. You must be logged in to view this page.");
            return;
        }

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
        } else if (pathInfo.equals("/claim")) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/claimOrder.jsp");
            dispatcher.forward(request, response);
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
            throws IOException, ServletException {
        ServletContext context = request.getServletContext();
        StorefrontFacade facade = (StorefrontFacade) context.getAttribute("storefrontFacade");
        String pathInfo = request.getPathInfo();
        User user = (User) request.getSession().getAttribute("user");

        if (pathInfo == null || pathInfo.equals("/")) {
            String address = request.getParameter("address");
            String country = request.getParameter("country");
            String city = request.getParameter("city");
            String postal = request.getParameter("postal");

            String shippingAddress = String.format("%s, %s, %s %s", address, city, country, postal);
            if (user == null) {
                List<CartProduct> cartProducts = (List<CartProduct>) request.getSession().getAttribute("anonCart");
                int orderId = createAnonymousOrder(facade, cartProducts, shippingAddress);
                request.getSession().setAttribute("anonCart", new ArrayList<>());

                String message = "Cart checkout successfully. Your order ID is " + orderId;
                response.sendRedirect(request.getContextPath() + "/cart/products/?message=" + URLEncoder.encode(message, StandardCharsets.UTF_8.toString()));
            } else {
                createOrder(facade, user, shippingAddress);
        response.sendRedirect(request.getContextPath() + "/orders/");
            }
        } else {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            try {
                claimOrder(facade, user, orderId);
                String message = "Order claimed successfully";
                response.sendRedirect(request.getContextPath() + "/orders/?message=" + URLEncoder.encode(message, StandardCharsets.UTF_8.toString()));
            } catch (OrderAlreadyClaimedException | OrderDoesNotExistException e) {
                request.setAttribute("message", e.getMessage());
                RequestDispatcher dispatcher = request.getRequestDispatcher("/claimOrder.jsp");
                dispatcher.forward(request, response);
            }
        }
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

    private int createAnonymousOrder(StorefrontFacade facade, List<CartProduct> cartProducts, String shippingAddress) {
        return facade.createAnonymousOrder(cartProducts, shippingAddress);
    }

    private void claimOrder(StorefrontFacade facade, User user, int orderId) throws OrderAlreadyClaimedException, OrderDoesNotExistException {
        facade.setOrderOwner(orderId, user.getId());
    }

}
