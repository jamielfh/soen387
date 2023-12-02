<%@ page import="shop.models.Product" %>
<%@ page import="java.util.ArrayList" %>
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

<!--Admin Page-->
<h1 style="justify-content: center">PRODUCT CATALOGUE</h1>
<div class="container" style="display: flex; justify-content: flex-end">
    <a href="<%= request.getContextPath() %>/admin/add-product">
        <button type="button" class="btn" style="background-color: #b5ff9e">Add Product</button>
    </a>
</div>
<div class="container" style="display: flex; justify-content: flex-end; padding-top: 10px">
    <a href="<%= request.getContextPath() %>/products/download">
        <button type="button" class="btn" style="background-color: #85adff">Download<i class="bi bi-download"></i></button>
    </a>
</div>
<div class="container">
    <table class="table table-loght">
        <thead>
        <tr>
            <th>SKU</th>
            <th>Product Name</th>
            <th>Description</th>
            <th>Price</th>
            <th>Vendor</th>
            <th>Slug</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <%
            ArrayList<Product> productList = (ArrayList<Product>) request.getAttribute("products");
            if (productList != null) {
                for (Product product : productList) {
        %>
        <tr>
            <td><%= product.getSku()%></td>
            <td><%= product.getName() %></td>
            <td><%= product.getDescription() %></td>
            <td><%= product.getPrice() %></td>
            <td><%= product.getVendor() %></td>
            <td><a href="<%= request.getContextPath() %>/products/<%= product.getSlug() %>">LINK</a></td>
            <td>
                <a href="<%= request.getContextPath() %>/admin/<%=product.getSlug()%>">
                    <button type="button" class="btn" style="background-color: lightgrey">Edit Product</button>
                </a>
            </td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="5">No products in catalogue.</td>
        </tr>
        <%
            }
        %>
        </tbody>

    </table>
</div>

</body>