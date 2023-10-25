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
<jsp:include page="header.jsp" />

<!--Adding Products Page-->
<h1 style="padding-left: 20px">Add New Product</h1>
<div class="container" style="padding-top: 50px">
    <div class="row">
        <div class="col-md-5">
            <h2>Product Image</h2>
        </div>
        <div class="col-md-7">
            <form id="addProductForm" action="/admin/add-product" method="post">
                <label for="sku">SKU:</label>
                <input class="form-control" type="text" id="sku" name="sku" placeholder="SKU">
                <br>
                <label for="name">Name:</label>
                <input class="form-control" type="text" id="name" name="name" placeholder="Name">
                <br>
                <label for="description">Description:</label>
                <input class="form-control" type="text" id="description" name="description" placeholder="Description">
                <br>
                <label for="vendor">Vendor:</label>
                <input class="form-control" type="text" id="vendor" name="vendor" placeholder="Vendor">
                <br>
                <label for="slug">Slug:</label>
                <input class="form-control" type="text" id="slug" name="slug" placeholder="Slug">
                <br>
                <label for="price">Price:</label>
                <input class="form-control" type="text" id="price" name="price" placeholder="Price">
                <br>
                <button type="submit" style="background-color: lightgrey; border: none; border-radius: 5px; padding: 5px; display: inline-block">Submit</button>
            </form>
        </div>

    </div>

</div>

</body>
