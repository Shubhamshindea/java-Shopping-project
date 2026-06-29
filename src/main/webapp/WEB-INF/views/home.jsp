<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, com.fashionstore.model.Product, com.fashionstore.model.Category" %>

<!DOCTYPE html>
<html>
<head>
    <title>Fashion Store</title>

    <!-- CSS -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/home.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style.css">
</head>

<body>
<!-- NAVBAR -->
<jsp:include page="partials/navbar.jsp" />

<div class="container">

    <!-- HERO SECTION -->
    <div class="hero">
        <h1>Discover Your Style</h1>
        <p>Latest trends, best prices, fast delivery</p>
        <a href="<%=request.getContextPath()%>/products" class="btn">Shop Now</a>
    </div>

    <!-- ================= CATEGORIES ================= -->
    <div class="categories">
        <h2 class="section-title">Categories</h2>

        <div class="category-list">

<%
    List<Category> categories = (List<Category>) request.getAttribute("categories");

    if (categories != null && !categories.isEmpty()) {
        for (Category c : categories) {
%>

            <a href="<%=request.getContextPath()%>/products?categoryId=<%=c.getCategoryId()%>" 
               class="category-btn">
               <%= c.getCategoryName() %>
            </a>

<%
        }
    }
%>

        </div>
    </div>

    <!-- ================= PRODUCTS ================= -->
    <div class="products">
        <h2 class="section-title">Trending Products</h2>

        <div class="product-grid">

<%
    List<Product> products = (List<Product>) request.getAttribute("products");

    if (products != null && !products.isEmpty()) {
        for (Product p : products) {
%>

        <div class="product-card">

            <!-- IMAGE -->
            <img src="<%=request.getContextPath()%>/assets/images/sample.jpg" alt="product">

            <!-- INFO -->
            <div class="product-info">
                <h3><%= p.getProductName() %></h3>
                <p class="brand"><%= p.getBrand() %></p>
                <p class="price">₹ <%= p.getPrice() %></p>

                <button class="btn">Add to Cart</button>
            </div>

        </div>

<%
        }
    } else {
%>

        <h3>No products available</h3>

<%
    }
%>

        </div>
    </div>

</div>

<!-- FOOTER -->
<jsp:include page="partials/footer.jsp" />

</body>
</html>