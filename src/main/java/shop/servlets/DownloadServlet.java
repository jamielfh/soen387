package shop.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.models.Product;
import shop.models.StorefrontFacade;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "DownloadServlet", value = "/products/download")
public class DownloadServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // Fetch product objects
        ServletContext context = request.getServletContext();
        StorefrontFacade facade = (StorefrontFacade) context.getAttribute("storefrontFacade");
        List<Product> productList = getProducts(facade);

        // Convert product data to JSON in pretty-print style
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonData = gson.toJson(productList);

        // Set the content type to JSON, and the content disposition to attachment to trigger a download
        response.setContentType("application/json");
        response.setHeader("Content-Disposition", "attachment; filename=products.json");

        // Write JSON response
        response.getWriter().write(jsonData);
    }

    private List<Product> getProducts(StorefrontFacade facade) {
        // Get all products from the facade
        return facade.getAllProducts();
    }
}

