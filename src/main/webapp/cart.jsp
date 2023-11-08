<%@ page import="shop.models.Product" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="shop.models.CartProduct" %>
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
      ArrayList<CartProduct> productList = (ArrayList<CartProduct>) request.getAttribute("items");
      if (productList != null && !productList.isEmpty()) {
        int index = 0; // To keep track of the index for unique identifiers
        for (CartProduct product : productList) {
    %>
    <tr>
      <th scope="row"><%= index + 1 %></th>
      <td><%= product.getProduct().getName() %></td>
      <td>
        <form action="<%= request.getContextPath() %>/cart/products/<%= product.getProduct().getSlug() %>" method="post" class="form-inline">
          <input type="hidden" name="id" value="<%= index + 1 %>" class="form-input">
          <div class="form-group d-sm-flex justify-content-sm-center">
            <input type="text" name="quantity" class="form-control" id="autoSizingInput"
                   value="<%= product.getQuantity() %>" >
          </div>
        </form>
      </td>
      <td><%= product.getProduct().getPrice() %></td>
      <td>
        <!-- Button that triggers the DELETE request using JavaScript -->
        <button onclick="deleteProduct('<%= product.getProduct().getSlug() %>')">Delete Product</button>
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
   <a href="checkout.jsp">
     <button class="btn btn-primary justify-content-center" type="submit">Place Order</button>
   </a>
 </div>




</body>
</html>