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
<h1><%= "Lunar Shadow Shop" %></h1>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" ></script>
<br/>

<!-- Navigation BAR -->
<nav class="navbar navbar-expand-lg bg-body-tertiary">
  <div class="container-fluid">
    <a class="navbar-brand" href="/index.jsp">HOME<i class="bi bi-cloud-moon-fill"></i></a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav">
        <li class="nav-item">
          <a class="nav-link" aria-current="page" href="/products/">Products</a>
        </li>
        <li class="nav-item">
          <a class="nav-link"  aria-current="page" href="/login.jsp">Login</a>
        </li>
      </ul>
      <ul class="navbar-nav ms-auto">
        <li class="nav-item" >
          <a class="nav-link active" id="cart" aria-current="page" href="/cart/products/" >CART
            <i class="bi bi-cart"></i>
            <span class="position-absolute top-0 end-0"></span>
          </a>
        </li>
      </ul>
    </div>
  </div>
</nav>

<!--CART PAGE-->
<h2>Your Shopping Cart</h2>
 <div class="container">
  <table class="table table-loght">
    <thead>
      <tr>
        <th scope="col"></th>
        <th scope="col">Product</th>
        <th scope="col">Quantity</th>
        <th scope="col">Price</th>
        <th scope="col">Delete</th>
      </tr>
    </thead>
    <tbody>
    <%
      ArrayList<Product> productList = (ArrayList<Product>) request.getAttribute("items");
      if (productList != null && !productList.isEmpty()) {
        int index = 0; // To keep track of the index for unique identifiers
        for (Product product : productList) {
    %>
    <tr>
      <th scope="row"><%= index + 1 %></th>
      <td><%= product.getName() %></td>
      <td>
        <form action="" method="post" class="form-inline">
          <input type="hidden" name="id" value="<%= index + 1 %>" class="form-input">
          <div class="form-group d-sm-flex justify-content-sm-center">
            <a class="btn btn-sm btn-incre" href="#"><i class="bi bi-plus-square"></i></a>
            <input type="text" name="quantity" class="form-control" id="autoSizingInput"
                   value="1" readonly>
            <a class="btn btn-sm btn-decre" href="#"><i class="bi bi-dash-square"></i></a>
          </div>
        </form>
      </td>
      <td><%= product.getPrice() %></td>
      <td>
        <!-- Button that triggers the DELETE request using JavaScript -->
        <button onclick="deleteProduct('<%= product.getSlug() %>')">Delete Product</button>
      </td>
    </tr>
    <%
        index++;
      }
    } else {
    %>
    <tr>
      <td colspan="5">No items in cart.</td>
    </tr>
    <%
      }
    %>
    </tbody>

    <!-- JavaScript function to perform the DELETE request -->
    <script>
      function deleteProduct(slug) {
        // Create an XMLHttpRequest object
        var xhr = new XMLHttpRequest();

        // Configure the request
        xhr.open("DELETE", "http://localhost:8080/cart/products/" + slug, true);

        // Set up the callback function for when the request completes
        xhr.onreadystatechange = function () {
          if (xhr.readyState === XMLHttpRequest.DONE) {
            // Handle the response, if needed
            console.log(xhr.responseText);

            // Remove the HTML row for the deleted product
            var productRow = document.querySelector('tr[data-slug="' + slug + '"]');
            if (productRow) {
              productRow.remove();
            }

            // Reload the page
            location.reload();
          }
        };

        // Send the DELETE request
        xhr.send();
      }
    </script>

  </table>
 </div>
</body>
</html>