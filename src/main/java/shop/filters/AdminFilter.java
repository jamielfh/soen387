package shop.filters;

import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import shop.models.User;

// Restrict access to all URLs under the /staff directory to authenticated users only
@WebFilter("/staff/*")
public class AdminFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);
        User user = (User) session.getAttribute("user");

        if (user != null && user.isStaff()) {
            // Staff is logged in, allow access
            chain.doFilter(request, response);
        } else {
            // Staff is not logged in, deny access
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Access denied. You must be logged in to view this resource.");
        }
    }

}