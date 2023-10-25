
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

<!--LOGIN PAGE-->

<div class="wrapper" id="login">
    <form method="post" action="login">
        <h1>Staff Login</h1>
        <div class="input-box">
            <input type="password" name="password" placeholder="Password" aria-label="Password" required><i class="bi bi-lock"></i>
        </div>
        <button type="submit" class="btn">Login</button>
    </form>
</div>
</body>
