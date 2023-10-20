<%@ page import="shop.models.Product" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
  <head>
    <title>Products</title>
  </head>
  <body>
  <ul>
    <%
      ArrayList<Product> productList = (ArrayList<Product>) request.getAttribute("products");
      if (productList != null) {
        for (Product product : productList) {
    %>
    <li><%= product.getName() %></li>
    <%
      }
    } else {
    %>
    <p>No products available.</p>
    <%
      }
    %>
  </ul>
  </body>
</html>
