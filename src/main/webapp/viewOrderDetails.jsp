<%@ page import="shop.models.Order" %>
<%@ page import="shop.models.OrderProduct" %>
<%@ page import="java.math.BigDecimal" %>
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
            <%Order order = (Order) request.getAttribute("order");%>
            <h4 class="d-flex justify-content-between align-items -center-mb-3">
                <span class="text-muted"><%=order.getId()%></span>
                <span>
                    <%
                    String trackingNumber = order.getTrackingNumber() == null ? "-" : order.getTrackingNumber();
                    %>
                    <%=trackingNumber%>
                </span>
            </h4>
            <ul class="list-group mb-3 sticky-top">
                <%
                    BigDecimal totalPrice = new BigDecimal(0);
                    for (OrderProduct orderProduct : order.getOrderProducts()) {
                %>
                <li class="list-group-item d-flex justify-content-between lh-condensed">
                    <div>
                        <h5 class="my-0"><%=orderProduct.getProduct().getName()%></h5>
                        <small class="text-muted"><%=orderProduct.getProduct().getDescription()%></small>
                        <p>Quantity: <%=orderProduct.getQuantity()%></p>
                    </div>
                    <span class="text-muted"><%=orderProduct.getProduct().getPrice().multiply(new BigDecimal(orderProduct.getQuantity()))%></span>
                </li>
                <%
                        totalPrice = totalPrice.add(orderProduct.getProduct().getPrice().multiply(new BigDecimal(orderProduct.getQuantity())));
                    }
                %>
                <li class="list-group-item d-flex justify-content-between">
                    <span>Total</span>
                    <strong><%=totalPrice%></strong>
                </li>
            </ul>
        </div>
    </div>
    <!--div class="row">
        <div class="col-md-6 mb-3">
            <p>First Name</p>
        </div>
        <div class="col-md-6 mb-3">
            <p>Last Name</p>
        </div>
    </div-->
    <div class="mb-3">
        <h5>Shipping Address</h5>
        <p><%=order.getShippingAddress()%></p>
    </div>
    <!--div class="row">
        <div class="col-md-4 mb-4">
            <p>Country</p>
        </div>
        <div class="col-md-4 mb-4">
            <p>City</p>
        </div>
        <div class="col-md-4 mb-4">
            <p>Postal Code</p>
        </div>

    </div-->

</div>

</body>
