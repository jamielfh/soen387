<header>
    <h1><%= "Lunar Shadow Shop" %></h1>
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
</header>