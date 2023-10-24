<%@ page import="shop.models.Product" %>
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


<!--Editing Products Page-->
<h1 style="padding-left: 20px">Edit Product</h1>
<div class="container" style="padding-top: 50px">
    <div class="row">
        <div class="col-md-5">
            <h2>Product Image</h2>
        </div>
        <div class="col-md-7">
            <%Product product = (Product) request.getAttribute("product");%>
            <form id="editProductForm" action="/products/<%=product.getSlug()%>" method="post">
                <label for="sku">SKU:</label>
                <input class="form-control" type="text" id="sku" name="sku" value="<%=product.getSku()%>">
                <br>
                <label for="name">Name:</label>
                <input class="form-control" type="text" id="name" name="name" value="<%=product.getName()%>">
                <br>
                <label for="description">Description:</label>
                <input class="form-control" type="text" id="description" name="description" value="<%=product.getDescription()%>">
                <br>
                <label for="vendor">Vendor:</label>
                <input class="form-control" type="text" id="vendor" name="vendor" value="<%=product.getVendor()%>">
                <br>
                <label for="slug">Slug:</label>
                <input class="form-control" type="text" id="slug" name="slug" value="<%=product.getSlug()%>">
                <br>
                <label for="price">Price:</label>
                <input class="form-control" type="text" id="price" name="price" value="<%=product.getPrice()%>">
                <br>
                <button type="submit" style="background-color: lightgrey; border: none; border-radius: 5px; padding: 5px; display: inline-block">Submit</button>
            </form>
        </div>




    </div>

</div>

</body>
