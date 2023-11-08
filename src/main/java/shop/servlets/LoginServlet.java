package shop.servlets;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.dao.UserDAO;
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
        String enteredPassword = request.getParameter("password");
        int userId = UserDAO.getIdForPassword(enteredPassword);

        if (userId != -1) {
            boolean isStaff = UserDAO.idIsStaff(userId);
            request.getSession().setAttribute("user", new User(userId, isStaff));

            if (isStaff) {
                response.sendRedirect("/admin");
            } else {
                response.sendRedirect("/");
            }
        } else {
            // Set an error attribute and re-forward the request to the login page with the error message
            request.setAttribute("error", "Invalid password. Please try again.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        }
    }
}
