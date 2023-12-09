package shop.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.exceptions.PasscodeExistsException;
import shop.exceptions.PasscodeInvalidException;
import shop.exceptions.UserDoesNotExistException;
import shop.models.StorefrontFacade;
import shop.models.User;

import java.io.IOException;

@WebServlet("/passcode/*")
public class PasscodeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        RequestDispatcher dispatcher;

        if (pathInfo.equals("/set")) {
            // Request for the set passcode page
            dispatcher = request.getRequestDispatcher("/setPasscode.jsp");
        } else {
            // Request for the change passcode page
            dispatcher = request.getRequestDispatcher("/changePasscode.jsp");
        }
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        StorefrontFacade facade = (StorefrontFacade) context.getAttribute("storefrontFacade");
        String pathInfo = request.getPathInfo();
        RequestDispatcher dispatcher;

        if (pathInfo.equals("/set")) {
            // Set passcode function
            String newPasscode = request.getParameter("newPasscode");
            String confirmPasscode = request.getParameter("confirmPasscode");

            if (!newPasscode.equals(confirmPasscode)) {
                request.setAttribute("message", "Passcodes do not match. Please try again.");
                dispatcher = request.getRequestDispatcher("/setPasscode.jsp");
                dispatcher.forward(request, response);
            }

            try {
                int userId = setPasscode(facade, newPasscode);
                User user = getUserFromId(facade, userId);

                request.getSession().setAttribute("user", user);
                request.setAttribute("message", "Passcode set successfully.");
            } catch (PasscodeExistsException | PasscodeInvalidException e) {
                request.setAttribute("message", e.getMessage());
            } catch (UserDoesNotExistException e) {
                request.setAttribute("message", "Failed to set passcode. Please try again.");
            } finally {
                dispatcher = request.getRequestDispatcher("/setPasscode.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            // Change passcode function
            String oldPasscode = request.getParameter("oldPasscode");
            String newPasscode = request.getParameter("newPasscode");
            String confirmPasscode = request.getParameter("confirmPasscode");

            if (!newPasscode.equals(confirmPasscode)) {
                request.setAttribute("message", "New and confirm passcodes do not match. Please try again.");
                dispatcher = request.getRequestDispatcher("/changePasscode.jsp");
                dispatcher.forward(request, response);
            }

            try {
                User user = getUserFromPasscode(facade, oldPasscode);
                changePasscode(facade, user, newPasscode);
                request.setAttribute("message", "Passcode changed successfully.");
            } catch (UserDoesNotExistException e) {
                request.setAttribute("message", "Old passcode does not match any user. Please set passcode instead.");
            } catch (PasscodeExistsException e) {
                request.setAttribute("message", "This passcode has been taken by another user. Please use a different passcode.");
            } catch (PasscodeInvalidException e) {
                request.setAttribute("message", e.getMessage());
            } finally {
                dispatcher = request.getRequestDispatcher("/changePasscode.jsp");
                dispatcher.forward(request, response);
            }
        }
    }

    private int setPasscode(StorefrontFacade facade, String passcode) throws PasscodeExistsException, PasscodeInvalidException {
        return facade.setPasscode(passcode);
    }

    private User getUserFromId(StorefrontFacade facade, int userId) throws UserDoesNotExistException {
        return facade.getUserFromId(userId);
    }

    private User getUserFromPasscode(StorefrontFacade facade, String passcode) throws UserDoesNotExistException {
        return facade.getUserFromPasscode(passcode);
    }

    private void changePasscode(StorefrontFacade facade, User user, String passcode) throws PasscodeExistsException, PasscodeInvalidException {
        facade.changePasscode(user, passcode);
    }
}
