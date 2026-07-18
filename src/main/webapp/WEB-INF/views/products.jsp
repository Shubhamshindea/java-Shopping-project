<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, com.fashionstore.model.Product, com.fashionstore.model.Category" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Products - Fashion Store</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/theme.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/product.css">
</head>
<body>

<jsp:include page="partials/navbar.jsp" />

<%
    List<Product> products = (List<Product>) request.getAttribute("products");
    String keyword = request.getParameter("keyword");
    String categoryId = request.getParameter("categoryId");
%>

<div class="page-container products-page">

    <div class="products-header">
        <h1>
            <% if (keyword != null && !keyword.trim().isEmpty()) { %>
                Search results for: "<em><%=keyword%></em>"
            <% } else if (categoryId != null && !categoryId.isEmpty()) { %>
                Category Products
            <% } else { %>
                All Products
            <% } %>
        </h1>
        <p class="product-count">
            <% if (products != null) { %>
                <%=products.size()%> product(s) found
            <% } else { %>
                0 products found
            <% } %>
        </p>
    </div>

    <% if (products != null && !products.isEmpty()) { %>

        <div class="product-grid">

            <% for (Product p : products) { %>

            <div class="product-card">

                <a href="<%=request.getContextPath()%>/product-details?id=<%=p.getProductId()%>" class="product-image-container">
                    <img src="<%=request.getContextPath()%>/<%= (p.getImageUrl() != null && !p.getImageUrl().isEmpty()) ? p.getImageUrl() : "assets/images/sample.jpg" %>" alt="<%=p.getProductName()%>">
                    <div class="product-overlay">
                        <span>View Details</span>
                    </div>
                </a>

                <div class="product-info">
                    <div class="product-brand"><%=p.getBrand()%></div>
                    <a href="<%=request.getContextPath()%>/product-details?id=<%=p.getProductId()%>">
                        <h3 class="product-title"><%=p.getProductName()%></h3>
                    </a>
                    <div class="product-price">₹ <%=p.getPrice()%></div>

                    <div class="product-actions">
                        <form action="<%=request.getContextPath()%>/cart" method="post" style="flex:1;">
                            <input type="hidden" name="action" value="add">
                            <input type="hidden" name="productId" value="<%=p.getProductId()%>">
                            <input type="hidden" name="quantity" value="1">
                            <button type="submit" class="add-cart-btn">Add to Cart</button>
                        </form>
                        <a href="<%=request.getContextPath()%>/product-details?id=<%=p.getProductId()%>" class="view-btn">View</a>
                    </div>
                </div>

            </div>

            <% } %>

        </div>

    <% } else { %>

        <div class="no-products">
            <div class="no-products-icon">🔍</div>
            <h3>No products found</h3>
            <p>Try a different search term or browse all categories.</p>
            <a href="<%=request.getContextPath()%>/products" class="btn-primary">Browse All Products</a>
        </div>

    <% } %>

</div>

<jsp:include page="partials/footer.jsp" />

</body>
</html>