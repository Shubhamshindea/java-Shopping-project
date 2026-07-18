<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, com.fashionstore.model.Product, com.fashionstore.model.Category" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Fashion Store - Discover Your Style</title>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/theme.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/home.css">
</head>

<body>
<jsp:include page="partials/navbar.jsp" />

<div class="page-container">

    <!-- HERO SECTION -->
    <div class="hero">
        <div class="hero-content">
            <h1>Elevate Your Wardrobe</h1>
            <p>Discover the latest premium trends, curated just for you.</p>
            <a href="<%=request.getContextPath()%>/products" class="btn-primary">Shop New Arrivals</a>
        </div>
    </div>

    <!-- CATEGORIES -->
    <section class="categories-section">
        <div class="section-header">
            <h2 class="section-title">Shop by Category</h2>
        </div>

        <div class="category-list">
            <%
                List<Category> categories = (List<Category>) request.getAttribute("categories");
                if (categories != null && !categories.isEmpty()) {
                    for (Category c : categories) {
            %>
                <a href="<%=request.getContextPath()%>/products?categoryId=<%=c.getCategoryId()%>" 
                   class="category-card">
                   <%= c.getCategoryName() %>
                </a>
            <%
                    }
                }
            %>
        </div>
    </section>

    <!-- TRENDING PRODUCTS -->
    <section class="trending-section">
        <div class="section-header">
            <h2 class="section-title">Trending Now</h2>
            <a href="<%=request.getContextPath()%>/products" class="section-link">View All →</a>
        </div>

        <div class="product-grid">
            <%
                List<Product> products = (List<Product>) request.getAttribute("products");
                if (products != null && !products.isEmpty()) {
                    for (Product p : products) {
            %>
            <div class="product-card">
                
                <a href="<%=request.getContextPath()%>/product-details?id=<%= p.getProductId() %>" class="product-image-container">
                    <img src="<%=request.getContextPath()%>/<%= (p.getImageUrl() != null && !p.getImageUrl().isEmpty()) ? p.getImageUrl() : "assets/images/sample.jpg" %>" alt="<%= p.getProductName() %>">
                </a>

                <div class="product-info">
                    <div class="product-brand"><%= p.getBrand() %></div>
                    <a href="<%=request.getContextPath()%>/product-details?id=<%= p.getProductId() %>">
                        <h3 class="product-title"><%= p.getProductName() %></h3>
                    </a>
                    <div class="product-price">₹ <%= p.getPrice() %></div>

                    <div class="product-actions">
                        <form action="<%=request.getContextPath()%>/cart" method="post">
                            <input type="hidden" name="action" value="add">
                            <input type="hidden" name="productId" value="<%= p.getProductId() %>">
                            <input type="hidden" name="quantity" value="1">
                            <button type="submit" class="add-to-cart-btn">Add to Cart</button>
                        </form>
                    </div>
                </div>

            </div>
            <%
                    }
                } else {
            %>
                <p style="color: var(--color-text-muted);">No trending products available right now.</p>
            <%
                }
            %>
        </div>
    </section>

</div>

<jsp:include page="partials/footer.jsp" />

</body>
</html>