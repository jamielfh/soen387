<%@ page import="shop.models.Product" %>
<%@ page import="shop.models.User" %>
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

<!--Set Privileges-->
<h1 style="justify-content: center">User Privilege Settings</h1>
<br>
<h3>All Staff Members</h3>
<div class="container">
    <table class="table table-loght">
        <thead>
        <tr>
            <th>User ID</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <%
            ArrayList<User> staffList = (ArrayList<User>) request.getAttribute("staff");
            if (staffList != null) {
                for (User staff : staffList) {
        %>
        <tr>
            <td><%=staff.getId()%></td>
            <td>
                <form method="post">
                    <input type="hidden" name="userId" value="<%=staff.getId()%>">
                    <button class="btn btn-primary justify-content-center" type="submit">Remove Staff Access</button>
                </form>
            </td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="5">No staff members found.</td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
</div>
<br>
<h3>All Customers</h3>
<div class="container">
    <table class="table table-loght">
        <thead>
        <tr>
            <th>User ID</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <%
            ArrayList<User> customerList = (ArrayList<User>) request.getAttribute("customers");
            if (customerList != null) {
                for (User customer : customerList) {
        %>
        <tr>
            <td><%=customer.getId()%></td>
            <td>
                <form method="post">
                    <input type="hidden" name="userId" value="<%=customer.getId()%>">
                    <button class="btn btn-primary justify-content-center" type="submit">Grant Staff Access</button>
                </form>
            </td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="5">No customers found.</td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
</div>

</body>

