
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
<h1><%= "Lunar Shadow Shop" %></h1>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" ></script>
<br/>

<!-- Navigation BAR -->
<nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
        <a class="navbar-brand" href="index.jsp">HOME</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="products">Products</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link"  aria-current="page" href="login.jsp">Login</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link"  aria-current="page" href="adminEdit.jsp">Admin</a>
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

<!--Admin Page-->
<h1 style="justify-content: center">PRODUCT CATALOGUE</h1>
<div class="container" style="display: flex; justify-content: flex-end">
    <button type="button" class="btn" style="background-color: #b5ff9e">Add Product</button>
</div>
<div class="container" style="display: flex; justify-content: flex-end; padding-top: 10px">
    <button type="button" class="btn" style="background-color: #85adff">Download<i class="bi bi-download"></i></button>
</div>
<div class="container">
    <table class="table table-loght">
        <thead>
        <tr>
            <th>SKU</th>
            <th>Product Name</th>
            <th>Description</th>
            <th>Price</th>
            <th>Vendor</th>
            <th>Slug</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>1111</td>
            <td>Product1</td>
            <td>A product</td>
            <td>1.99</td>
            <td>Vendor1</td>
            <td><a href="#">LINK</a></td>
            <td>
                <button type="button" class="btn" style="background-color: lightgrey">Edit</button>
            </td>
        </tr>
        </tbody>
    </table>
</div>

</body>