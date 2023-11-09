
package shop.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import shop.exceptions.ProductNotFoundException;
import shop.exceptions.ProductSkuExistsException;
import shop.exceptions.ProductSlugExistsException;
import shop.exceptions.ProductSlugInvalidException;
import shop.models.Product;
import shop.models.StorefrontFacade;
import shop.models.User;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(name = "AdminServlet", value = "/admin/*")
public class AdminServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        StorefrontFacade facade = (StorefrontFacade) context.getAttribute("storefrontFacade");
        String pathInfo = request.getPathInfo();
        HttpSession session = request.getSession();

        if (session.getAttribute("user") == null || !((User) session.getAttribute("user")).isStaff()) {
            // Staff is not logged in, deny access
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Access denied. You must be logged in to view this page.");
        } else if (pathInfo == null || pathInfo.equals("/")) {
            // Request for the admin product catalogue
            List<Product> products = getProducts(facade);
            request.setAttribute("products", products);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/staff/adminEdit.jsp");
            dispatcher.forward(request, response);
        } else if (pathInfo.equals("/add-product")) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/staff/addProduct.jsp");
            dispatcher.forward(request, response);
        } else {
            // Request to get a specific product to edit
            String slug = pathInfo.substring(1); // Removing leading "/"
            try {
                Product product = getProductBySlug(facade, slug);
                request.setAttribute("product", product);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/staff/editProduct.jsp");
                dispatcher.forward(request, response);
            } catch (ProductNotFoundException e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        StorefrontFacade facade = (StorefrontFacade) context.getAttribute("storefrontFacade");
        String pathInfo = request.getPathInfo();

        if (pathInfo.equals("/add-product")) {
            String sku = request.getParameter("sku");
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String vendor = request.getParameter("vendor");
            String slug = request.getParameter("slug");

            try {
                BigDecimal price = new BigDecimal(request.getParameter("price"));
                createProduct(facade, sku, name, description, vendor, slug, price);
                response.sendRedirect("/products/" + slug);
            } catch (ProductSkuExistsException e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product sku already in use");
            } catch (ProductSlugInvalidException e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product slug format is invalid");
            } catch (ProductSlugExistsException e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product slug already in use");
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product price is not a number");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Add product function not allowed");
        }
    }

    private List<Product> getProducts(StorefrontFacade facade) {
        // Get all products from the facade
        return facade.getAllProducts();
    }

    private Product getProductBySlug(StorefrontFacade facade, String slug) throws ProductNotFoundException {
        // Get a product by slug from the facade
        return facade.getProductBySlug(slug);
    }

    private void createProduct(StorefrontFacade facade, String sku, String name, String description, String vendor, String slug, BigDecimal price) throws ProductSkuExistsException, ProductSlugInvalidException, ProductSlugExistsException {
        // Create product in the facade
        facade.createProduct(sku, name, description, vendor, slug, price);
    }

}