<%@ page import="shop.models.User" %>
<script type="text/javascript">
  let isLoggedIn = <%= session.getAttribute("user") != null %>;
  let isStaff = <%= session.getAttribute("user") != null && ((User) session.getAttribute("user")).isStaff() %>;

  function toggleStaffButtons() {
    if (isLoggedIn) {
        document.getElementById("order-button").style.display = "block";
      document.getElementById("logout-button").style.display = "block";
      document.getElementById("login-button").style.display = "none";
    } else {
      document.getElementById("logout-button").style.display = "none";
      document.getElementById("login-button").style.display = "block";
    }

    if (isStaff) {
      document.getElementById("admin-button").style.display = "block";
    } else {
      document.getElementById("admin-button").style.display = "none";
    }
  }

  window.onload = toggleStaffButtons;
</script>

<header>
    <h1><%= "Lunar Shadow Shop" %></h1>
    <br/>

    <!-- Navigation BAR -->
    <nav class="navbar navbar-expand-lg bg-body-tertiary">
        <div class="container-fluid">
            <a class="navbar-brand" href="<%= request.getContextPath() %>/">HOME<i class="bi bi-cloud-moon-fill"></i></a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="<%= request.getContextPath() %>/products">Products</a>
                    </li>
                    <li class="nav-item hidden" id="order-button">
                        <a class="nav-link" aria-current="page" href="<%= request.getContextPath() %>/orders/">Orders</a>
                    </li>
                    <li class="nav-item hidden" id="login-button">
                        <a class="nav-link"  aria-current="page" href="<%= request.getContextPath() %>/login">Login</a>
                    </li>
                    <li class="nav-item hidden" id="admin-button">
                        <a class="nav-link" href="<%= request.getContextPath() %>/admin/home">Admin</a>
                    </li>
                    <li class="nav-item hidden" id="logout-button">
                        <a class="nav-link"  aria-current="page" href="<%= request.getContextPath() %>/logout">Log out</a>
                    </li>
                </ul>
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item" >
                        <a class="nav-link active" id="cart" aria-current="page" href="<%= request.getContextPath() %>/cart/products/" >CART
                            <i class="bi bi-cart"></i>
                            <span class="position-absolute top-0 end-0"></span>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</header>