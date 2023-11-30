
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lunar Shadow Shop</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="../style.css">
</head>
<body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<jsp:include page="../header.jsp" />

<!--Staff Home Page-->
<h1>Admin Home Page</h1>
<div class="container">
    <div class="card mb-3" style="width: 18rem;">
        <div class="card-body">
            <h5 class="card-title">View and Edit All Products</h5>
            <p class="card-text">To view all products, to edit products, and to download the whole catalogue.</p>
            <a href="<%= request.getContextPath() %>/admin/" class="btn btn-primary">All Products</a>
        </div>
    </div>

    <div class="card mb-3" style="width: 18rem;">
        <div class="card-body">
            <h5 class="card-title">Add a Product</h5>
            <p class="card-text">To add a new product in the store catalogue.</p>
            <a href="<%= request.getContextPath() %>/admin/add-product" class="btn btn-primary">Add Product</a>
        </div>
    </div>

    <div class="card mb-3" style="width: 18rem;">
        <div class="card-body">
            <h5 class="card-title">View All Orders</h5>
            <p class="card-text">To view all orders, view specific order details, and to ship orders.</p>
            <a href="<%= request.getContextPath() %>/orders/" class="btn btn-primary">View All Orders</a>
        </div>
    </div>

    <div class="card mb-3" style="width: 18rem;">
        <div class="card-body">
            <h5 class="card-title">Privilege Setting</h5>
            <p class="card-text">To view all orders, view specific order details, and to ship orders.</p>
            <a href="<%= request.getContextPath() %>/admin/change-permission" class="btn btn-primary">Go to Role Settings</a>
        </div>
    </div>

    <div class="card mb-3" style="width: 18rem;">
        <div class="card-body">
            <h5 class="card-title">Change Passcode</h5>
            <p class="card-text">To view all orders, view specific order details, and to ship orders.</p>
            <a href="<%= request.getContextPath() %>/passcode/change" class="btn btn-primary">Change Passcode</a>
        </div>
    </div>

</div>
</body>