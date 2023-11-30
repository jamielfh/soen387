
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

<!--Change Passcode-->
<h1 style="padding-left: 250px">Change your Passcode</h1>

<div class="wrapper">
  <form method="post">
    <div class="input-box">
      <label style="font-size: larger">Old Passcode<input type="password" required></label>
    </div>
    <div class="input-box">
      <label style="font-size: larger">New Passcode<input type="password" required></label>
    </div>
    <div class="input-box">
      <label style="font-size: larger">Confirm Passcode<input type="password" required></label>
    </div>
    <button type="submit" class="btn">Submit</button>
  </form>
</div>


</body>