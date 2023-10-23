package shop.servlets;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.models.Product;
import shop.models.StorefrontFacade;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@WebServlet(name = "DownloadServlet", value = "/products/download")
public class DownloadServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        ServletContext context = request.getServletContext();
        StorefrontFacade facade = (StorefrontFacade) context.getAttribute("storefrontFacade");
        List<Product> productList = getProducts(facade);

        // Set the content type to PDF
        response.setContentType("application/pdf");

        // Set the content disposition to attachment to trigger a download
        response.setHeader("Content-Disposition", "attachment; filename=\"product_list.pdf\"");

        // Create a Document
        Document document = new Document();

        try {
            // Get the response's OutputStream
            OutputStream out = response.getOutputStream();

            // Create a PdfWriter to write to the OutputStream
            PdfWriter.getInstance(document, out);

            // Open the document
            document.open();

            // Add content to the PDF
            for (Product product : productList) {
                document.add(new Paragraph(product.getName() + "\t" + product.getPrice()));
            }

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product catalog could not be downloaded");
        } finally {
            // Close the document
            document.close();
        }
    }

    private List<Product> getProducts(StorefrontFacade facade) {
        // Get all products from the facade
        return facade.getAllProducts();
    }
}

