
<%@ page import="shop.models.Product" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Lunar Shadow Shop</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
  <link rel="stylesheet" href="style.css">
</head>
<body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<jsp:include page="header.jsp" />


<!-- Product PAGE -->
<div class="container">
  <div class="card-header my-3">All Products </div>


    <%
      ArrayList<Product> productList = (ArrayList<Product>) request.getAttribute("products");
      if (productList != null) {
        for (Product product : productList) {
    %>
  <div class="row row-cols-1">
      <div class="col-md-3 my-3">
        <div class="card" style="width: 18rem;">
          <img src="..." class="card-img-top" alt="...">
          <div class="card-body">
            <h5 class="productName"><%= product.getName() %></h5>
            <h6 class="sku"><%= product.getSku()%></h6>
            <p class="description"><%= product.getDescription() %></p>
            <a class="viewProduct" href="/products/<%= product.getSlug()%>">View Product</a>
            <h6><%= product.getPrice()%></h6>

            <form id="addToCartForm" action="/cart/products/<%=product.getSlug()%>" method="post">
              <button type="submit">Add to Cart</button>
            </form>
          </div>
        </div>
      </div>
    </div>
    <%
      }
    } else {
    %>
    <p>No products available.</p>
    <%
      }
    %>

</div>

</body>
</html>
