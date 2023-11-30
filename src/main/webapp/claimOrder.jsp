
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
<h1>Claim Order</h1>
<!-- Display error message -->
<% String message = (String)request.getAttribute("message"); %>
<% if (message != null) { %>
<div class="error-message" style="color:red">
    <%= message %>
</div>
<% } %>
<div class="wrapper">
    <form method="post">
        <br>
        <div class="input-box">
            <label>Enter your Order ID: <input type="text" name="orderId" placeholder="123456" required></label>
        </div>
        <br>
        <button class="btn btn-primary justify-content-center" type="submit">Claim Order</button>
    </form>
</div>

</body>
