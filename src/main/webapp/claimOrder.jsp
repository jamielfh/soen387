
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

<!--Claim Order Page-->
<h1 style="padding-left: 250px">Claim Order</h1>

<div class="wrapper">
    <form method="post">
        <h4>Claim your order ID with your password</h4>
        <div class="input-box">
            <label>Enter your Order ID<input type="text" placeholder="123456" required></label>
        </div>
        <div class="input-box">
            <label>Enter your Username<input type="text" placeholder="Jane Doe" required></label>
        </div>
        <div class="input-box">
            <label>Enter your password<input type="password" required></label>
        </div>
        <button type="submit" class="btn">Claim Order</button>
    </form>
</div>

</body>
