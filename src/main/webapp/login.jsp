
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
    <form method="post" action="<%= request.getContextPath() %>/login">
        <h1>Login</h1>
        <div class="input-box">
            <input type="password" name="passcode" placeholder="Passcode" aria-label="Passcode" required><i class="bi bi-lock"></i>

            <!-- Check if there's an error message and display it -->
            <% String error = (String)request.getAttribute("error"); %>
            <% if (error != null) { %>
            <div class="error-message">
                <%= error %>
            </div>
            <% } %>
        </div>
        <button type="submit" class="btn">Login</button>
        <br>
        <div>
            <a href="<%= request.getContextPath() %>/passcode/set">New User? Set your passcode here</a>
        </div>
        <div>
            <a href="<%= request.getContextPath() %>/passcode/change">Change your passcode here</a>
        </div>

    </form>
</div>
</body>
