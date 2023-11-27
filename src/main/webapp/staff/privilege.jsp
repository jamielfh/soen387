<%@ page import="shop.models.Product" %>
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

<div class="wrapper">
    <form>
    <label>User ID
        <input type="text" class="form-control" required>
    </label>
        <h3>Choose Role</h3>
        <input type="radio" id="customer">
        <label for="customer">Customer</label>
        <input type="radio" id="staff">
        <label for="staff">Staff</label>
    </form>
</div>
<div>
    <button class="btn btn-primary" type="submit">Change Role</button>
</div>

</body>

