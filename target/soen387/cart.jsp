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
      <tr>
        <th scope="row"></th>
        <td>Product Name</td>
        <td>
          <form action="" method="post" class="form-inline">
            <input type="hidden" name="id" value="1" class="form-input">
            <div class="form-group d-sm-flex justify-content-sm-center">
              <a class="btn btn-sm btn-incre" href="#"><i class="bi bi-plus-square"></i></a>
              <input type="text" name="quantity" class="form-control" id="autoSizingInput" value="1" readonly>
              <a class="btn btn-sm btn-decre" href="#"><i class="bi bi-dash-square"></i></a>
            </div>

          </form>
        </td>
        <td>20.99</td>
        <td><a class="btn btn-sm btn-danger">Delete</a></td>
      </tr>
    </tbody>

  </table>
 </div>
</body>
</html>