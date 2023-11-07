
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

<!--Customer View All Orders-->
<h1>All Orders</h1>
<div class="container">
  <table class="table table-loght">
    <thead>
    <tr>
      <th>Order ID</th>
      <th>Total</th>
      <th>Order Link</th>
      <th>Shipping Status</th>
      <th>Tracking Number</th>
    </tr>
    </thead>
    <tbody>
    <tr>
      <td>OrderID</td>
      <td>totalPrice</td>
      <td>URL</td>
      <td>Shipped</td>
      <td>123456789</td>
    </tr>
    </tbody>
  </table>
</div>
</body>
