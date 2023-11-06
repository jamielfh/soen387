
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
<jsp:include page="../header.jsp" />

<!-- Shipping Order-->
<h1>Shipping Form</h1>

<div class="container">
    <div class="row">
        <h4 class="d-flex justify-content-between align-items-center mb-3">
            <span class="text-muted">Order ID</span>
        </h4>
    </div>
    <ul class="list-group mb-3 sticky-top">
        <li class="list-group-item d-flex justify-content-between lh-condensed">
            <div>
                <h5 class="my-0">Product Name</h5>
                <small class="text=muted">Product Description</small>
            </div>
            <span class="text-muted">2$</span>
        </li>
        <li class="list-group-item d-flex justify-content-between">
            <span>Total</span>
            <strong>Total Price</strong>
        </li>
    </ul>

    <div class="row">
        <p>Customer Name</p>
        <p>Customer Address</p>

        <h3>Shipping Company</h3>
        <input type="radio" id="canadaPost">
        <label for="canadaPost">Canada Post</label>
        <input type="radio" id="ups">
        <label for="ups">UPS</label>


        <div class="row">
            <h3>Delivery Time</h3>
            <input type="radio" id="standard">
            <label for="standard">Standard (5-7 days)</label>
            <input type="radio" id="express">
            <label for="express">Express (1-2 days)</label>
        </div>
    </div>
    <div>
        <a href="#">
            <button class="btn btn-primary">Ship Order</button>
        </a>
    </div>

</div>
</body>
