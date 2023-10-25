package shop.servlets;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
        dispatcher.forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String enteredPassword = request.getParameter("password");

        // For now, staff users' passcode is assumed to always be 'secret'
        if (enteredPassword.equals("secret")) {
            request.getSession().setAttribute("staff", true);
            response.sendRedirect("/admin");
        } else {
            response.sendRedirect("/login");
        }
    }
}
