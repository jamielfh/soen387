<%@ page import="shop.models.CartProduct" %>
<%@ page import="java.util.ArrayList" %>
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


<!--Checkout Page -->

<div class="container">
    <div class="pu-5 text-center">
        <h2>Cart Checkout</h2>
    </div>
    <div class="row">
        <div class="col-md-4 order-md-2 mb-4">
            <h4 class="d-flex justify-content-between align-items-center mb-3">
                <span class="text-muted">Your Cart</span>
            </h4>
            <ul class="list-group mb-3 sticky-top">
                <%
                    ArrayList<CartProduct> productList = (ArrayList<CartProduct>) request.getAttribute("items");
                    if (productList != null && !productList.isEmpty()) {
                        BigDecimal totalPrice = new BigDecimal(0);
                        for (CartProduct product : productList) {
                %>
                <li class="list-group-item d-flex justify-content-between lh-condensed">
                    <div>
                        <h5 class="my-0"><%=product.getProduct().getName()%></h5>
                        <small class="text=muted"><%=product.getProduct().getDescription()%>></small>
                        <p>Quantity: <%=product.getQuantity()%></p>
                    </div>
                    <div>
                        <span class="text-muted">$<%=product.getProduct().getPrice().multiply(new BigDecimal(product.getQuantity()))%></span>
                    </div>

                </li>
                <%
                        totalPrice = totalPrice.add(product.getProduct().getPrice().multiply(new BigDecimal(product.getQuantity())));
                    }
                %>
                <li class="list-group-item d-flex justify-content-between">
                    <span>Total</span>
                    <strong><%=totalPrice%></strong>
                </li>
                <%
                } else {
                %>
                <li class="list-group-item d-flex justify-content-between lh-condensed">
                    </z><h5 class="my-0">No items in cart.</h5>
                </li>
                <%
                    }
                %>
            </ul>
        </div>

        <div class="col-md-8 order-md-1">
            <h4>Billing Address</h4>
            <form action="<%= request.getContextPath() %>/orders/" method="post">
                <div class="mb-3">
                    <div class="input-group">
                        <label>Address
                            <input type="text" class="form-control" id="address" name="address" placeholder="1111 street A" required="required">
                        </label>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4 mb-4">
                        <div class="input-group">
                            <label>Country
                                <input type="text" class="form-control" id="country" name="country" placeholder="Canada" required="required">
                            </label>
                        </div>
                    </div>
                    <div class="col-md-4 mb-3">
                        <label>City</label>
                        <input type="text" class="form-control" id="city" name="city" placeholder="Montreal" required="required">
                    </div>
                    <div class="col-md-4 mb-3">
                        <label>Postal Code</label>
                        <input type="text" class="form-control" id="postal" name="postal" placeholder="1A1 2B2" required="required">
                    </div>
                </div>
                <button class="btn btn-primary btn-block" type="submit">Place Order</button>
            </form>
        </div>
    </div>
</div>

</body>
