
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

<!--Customer View Order Details-->
<h1>Order Details</h1>
<div class="container">
    <div class="row">
        <div class="col-md-4 order-md-2 mb-4">
            <h4 class="d-flex justify-content-between align-items -center-mb-3">
                <span class="text-muted">Order ID</span>
                <span>trackingNumber</span>
            </h4>
            <ul class="list-group mb-3 sticky-top">
                <li class="list-group-item d-flex justify-content-between lh-condensed">
                    <div>
                        <h5 class="my-0">Product Name</h5>
                        <small class="text-muted">Product Description</small>
                    </div>
                    <span class="text-muted">Price</span>
                </li>
                <li class="list-group-item d-flex justify-content-between">
                    <span>Total</span>
                    <strong>Total Price</strong>
                </li>
            </ul>
        </div>
    </div>
    <div class="row">
        <div class="col-md-6 mb-3">
            <p>First Name</p>
        </div>
        <div class="col-md-6 mb-3">
            <p>Last Name</p>
        </div>
    </div>
    <div class="mb-3">
        <p>Address</p>
    </div>
    <div class="row">
        <div class="col-md-4 mb-4">
            <p>Country</p>
        </div>
        <div class="col-md-4 mb-4">
            <p>City</p>
        </div>
        <div class="col-md-4 mb-4">
            <p>Postal Code</p>
        </div>

    </div>

</div>

</body>
