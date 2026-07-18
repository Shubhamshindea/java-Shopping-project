<%@ page session="true" %>
<%@ page import="com.fashionstore.model.User" %>

<header class="navbar">
    <div class="navbar-container">

        <!-- LOGO -->
        <div class="logo">
            <a href="<%=request.getContextPath()%>/home">
                Fashion<span>Store</span>
            </a>
        </div>

        <!-- SEARCH -->
        <form class="search-bar"
              action="<%=request.getContextPath()%>/products"
              method="get">
            <input type="text" name="keyword"
                   placeholder="Search for premium fashion..." />
            <button type="submit">🔍</button>
        </form>

        <!-- NAV LINKS -->
        <nav class="nav-links">
            <a href="<%=request.getContextPath()%>/home">Home</a>
            <a href="<%=request.getContextPath()%>/products">Shop</a>

            <!-- CART WITH COUNT -->
            <a href="<%=request.getContextPath()%>/cart" class="cart-link">
                <span class="cart-icon">🛒</span> Cart 
                <span class="cart-count">0</span>
            </a>

            <%
                User user = (User) session.getAttribute("user");
                if (user != null) {
            %>
                <span class="user-name">
                    <%= user.getFullName() %>
                </span>
                <a href="<%=request.getContextPath()%>/logout" class="logout-btn">
                    Logout
                </a>
            <%
                } else {
            %>
                <a href="<%=request.getContextPath()%>/login" class="login-btn">Log In</a>
                <a href="<%=request.getContextPath()%>/register" class="register-btn">Sign Up</a>
            <%
                }
            %>
        </nav>

    </div>
</header>