
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
<h1><%= "Lunar Shadow Shop" %></h1>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" ></script>
<br/>

<!-- Navigation BAR -->
<nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
        <a class="navbar-brand" href="index.jsp">HOME<i class="bi bi-cloud-moon-fill"></i></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="products">Products</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link"  aria-current="page" href="login.jsp">Login</a>
                </li>
            </ul>
            <ul class="navbar-nav ms-auto">
                <li class="nav-item" >
                    <a class="nav-link active" id="cart" aria-current="page" href="cart.jsp" >CART
                        <i class="bi bi-cart"></i>
                        <span class="position-absolute top-0 end-0"></span>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!--LOGIN PAGE-->

<div class="wrapper" id="login">
    <form action="">
        <h1>Login</h1>
        <div class="input-box">
            <input type="text" placeholder="Username" required><i class="bi bi-person"></i>
        </div>
        <div class="input-box">
            <input type="password" placeholder="Password" required><i class="bi bi-lock"></i>
        </div>
        <button type="submit" class="btn">Login</button>
    </form>
</div>
</body>
