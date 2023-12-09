<%@ page import="shop.models.Order" %>
<%@ page import="java.util.ArrayList" %>
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

<!--Customer View All Orders-->
<h1>All Orders</h1>
<div class="container">
  <table class="table table-loght">
    <thead>
    <tr>
      <th>Order ID</th>
      <th>Order Link</th>
      <th>Shipping Status</th>
      <th>Tracking Number</th>
      <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <%
      ArrayList<Order> orderList = (ArrayList<Order>) request.getAttribute("orders");
      if (orderList != null) {
        for (Order order : orderList) {
    %>
    <tr>
      <td><%=order.getId()%></td>
      <td>
        <a class="viewOrder" href="<%= request.getContextPath() %>/orders/<%= order.getId()%>">View Order Details</a>
      </td>
      <td>
        <%
        String shippingStatus = order.getTrackingNumber() == null ? "Not shipped" : "Shipped";
        %>
        <%=shippingStatus%></td>
      <td>
        <%
          String trackingNumber = order.getTrackingNumber() == null ? "-" : order.getTrackingNumber();
        %>
        <%=trackingNumber%>
      </td>
      <td>
        <a href="<%= request.getContextPath() %>/shipping/<%=order.getId()%>">
          <button type="button" class="btn" style="background-color: lightgrey">Ship Order</button>
        </a>
      </td>
    </tr>
    <%
      }
    } else {
    %>
    <tr>
      <td colspan="5">No orders.</td>
    </tr>
    <%
      }
    %>
    </tbody>
  </table>
</div>
</body>
