
package shop.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.exceptions.ProductSkuExistsException;
import shop.exceptions.ProductSlugExistsException;
import shop.exceptions.ProductSlugInvalidException;
import shop.models.Product;
import shop.models.StorefrontFacade;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        StorefrontFacade facade = (StorefrontFacade) context.getAttribute("storefrontFacade");

        List<Product> products = getProducts(facade);
        request.setAttribute("products", products);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/adminEdit.jsp");
        dispatcher.forward(request, response);
    }

    private List<Product> getProducts(StorefrontFacade facade) {
        // Get all products from the facade
        return facade.getAllProducts();
    }

}