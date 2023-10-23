
package shop.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.exceptions.*;
import shop.models.Product;
import shop.models.StorefrontFacade;

import java.io.IOException;
import java.util.List;

@WebServlet("/create-product")
public class CreateProductServlet extends HttpServlet {
    //private StorefrontFacade facade = new StorefrontFacade();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/create.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        StorefrontFacade facade = (StorefrontFacade) context.getAttribute("storefrontFacade");

        String sku = request.getParameter("sku");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String vendor = request.getParameter("vendor");
        String slug = request.getParameter("slug");
        double price = Double.parseDouble(request.getParameter("price"));

        try {
            createProduct(facade, sku, name, description, vendor, slug, price);
        } catch (ProductSkuExistsException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product sku already in use");
        } catch (ProductSlugInvalidException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product slug invalid");
        } catch (ProductSlugExistsException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product slug already in use");
        }
    }

    private void createProduct(StorefrontFacade facade, String sku, String name, String description, String vendor, String slug, double price) throws ProductSkuExistsException, ProductSlugInvalidException, ProductSlugExistsException {
        // Create product in the facade
        facade.createProduct(sku, name, description, vendor, slug, price);
    }
}