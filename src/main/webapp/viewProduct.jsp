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

<!--VIEW PRODUCTS-->
<h1 style="padding-left: 20px">Details</h1>
<div class="container" style="padding-top: 50px">
    <div class="row">
        <%Product product = (Product) request.getAttribute("product");%>
        <div class="col-md-5">
            <img src="<%= product.getImageURL() %>" class="card-img-top" alt="Image of <%= product.getName() %>">
        </div>
        <div class="col-md-7">
            <h3><%= product.getName()%></h3>
            <h6><%= product.getSku()%></h6>
            <h5><%= product.getDescription()%></h5>
            <h5><%= product.getVendor()%></h5>
            <h4><%= product.getPrice()%></h4>

            <form id="addToCartForm" action="/cart/products/<%=product.getSlug()%>" method="post">
                <button type="submit">Add to Cart</button>
            </form>
        </div>

    </div>

</div>

</body>
