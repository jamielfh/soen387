package shop.servlets;

import shop.models.StorefrontFacade;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;

@WebServlet("/cart/products/*")
public class  CartServlet extends HttpServlet {
    private StorefrontFacade facade = new StorefrontFacade();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Implementation for handling POST requests
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Implementation for handling DELETE requests
    }
}
