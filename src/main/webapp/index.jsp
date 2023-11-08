
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

<!--Front Page-->

<div class="px-width">
    <div id="carouselExample" class="carousel slide">
        <div class="carousel-inner">
            <div class="carousel-item active">
                <img src="<%= request.getContextPath() %>/img/cat1.jpg" class="d-block w-100" alt="cat" style="">
                <div class="carousel-caption d-none d-md-block">
                    <h3 style="color: black">Make Your Furiends Happy With Lunar Shadow</h3>
                </div>
            </div>
            <div class="carousel-item">
                <img src="<%= request.getContextPath() %>/img/cat2.jpeg" class="d-block w-100" alt="cat">
                <div class="carousel-caption d-none d-md-block">
                    <h3 style="color: black">Satisfaction Guaranteed</h3>
                </div>
            </div>
            <div class="carousel-item">
                <img src="<%= request.getContextPath() %>/img/cat3.jpg" class="d-block w-100" alt="cat">
                <div class="carousel-caption d-none d-md-block">
                    <h3 style="color: black">Only at Lunar Shadow</h3>
                </div>
            </div>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#carouselExample" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#carouselExample" data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
        </button>
    </div>
</div>
</body>
