<%@ page import="shop.models.Product" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Online Shop</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
  <link rel="stylesheet" href="style.css">
</head>
<body>
<h1><%= "Online Shop" %></h1>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" ></script>
<br/>

<!-- Navigation BAR -->
<nav class="navbar navbar-expand-lg bg-body-tertiary">
  <div class="container-fluid">
    <a class="navbar-brand" href="index.jsp">SHOP</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav">
        <li class="nav-item">
          <a class="nav-link" aria-current="page" href="products">Products</a>
        </li>
        <li class="nav-item">
          <a class="nav-link"  aria-current="page" href="#">Login</a>
        </li>
      </ul>
      <ul class="navbar-nav ms-auto">
        <li class="nav-item" >
          <a class="nav-link active" id="cart" aria-current="page" href="cart.jsp" >CART
            <i class="bi bi-cart"></i>
            <span class="position-absolute top-0 end-0"></span>
          </a>
        </li>
      </ul>
    </div>
  </div>
</nav>



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
            <a class="viewProduct" href="#">View Product</a>
            <h6><%= product.getPrice()%></h6>
            <a href="#" class="btn btn-primary">Add to Cart</a>
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
