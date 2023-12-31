package shop.servlets;

import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import shop.models.StorefrontFacade;
import shop.models.User;

import java.io.IOException;

@WebServlet(name = "DownloadServlet", value = "/products/download")
public class DownloadServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        if (user == null || !user.isStaff()) {
            // Staff is not logged in, deny access
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Access denied. You must be logged in to view this resource.");
        } else {
            // Fetch product objects
            ServletContext context = request.getServletContext();
            StorefrontFacade facade = (StorefrontFacade) context.getAttribute("storefrontFacade");

            // Convert product data to JSON in pretty-print style
            String jsonData = facade.downloadProductCatalog();

            // Set the content type to JSON, and the content disposition to attachment to trigger a download
            response.setContentType("application/json");
            response.setHeader("Content-Disposition", "attachment; filename=products.json");

            // Write JSON response
            response.getWriter().write(jsonData);
        }
    }
}

