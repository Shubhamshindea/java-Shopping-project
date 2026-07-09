<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, com.fashionstore.model.Product, com.fashionstore.model.Category" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Products - Fashion Store</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/product.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>

<jsp:include page="partials/navbar.jsp" />

<%
    List<Product> products = (List<Product>) request.getAttribute("products");
    String keyword = request.getParameter("keyword");
    String categoryId = request.getParameter("categoryId");
%>

<div class="products-page">

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

                <a href="<%=request.getContextPath()%>/product-details?id=<%=p.getProductId()%>"
                   class="product-link">

                    <div class="product-image-wrapper">
                        <img src="<%=request.getContextPath()%>/<%=p.getImageUrl()%>"
                             alt="<%=p.getProductName()%>"
                             onerror="this.src='<%=request.getContextPath()%>/assets/images/sample.jpg'">
                        <div class="product-overlay">
                            <span>View Details</span>
                        </div>
                    </div>

                    <div class="product-info">
                        <h3 class="product-name"><%=p.getProductName()%></h3>
                        <p class="product-brand"><%=p.getBrand()%></p>
                        <p class="product-price">₹ <%=p.getPrice()%></p>
                    </div>

                </a>

                <div class="product-actions">
                    <form action="<%=request.getContextPath()%>/cart" method="post">
                        <input type="hidden" name="action" value="add">
                        <input type="hidden" name="productId" value="<%=p.getProductId()%>">
                        <input type="hidden" name="quantity" value="1">
                        <button type="submit" class="add-cart-btn">🛒 Add to Cart</button>
                    </form>
                    <a href="<%=request.getContextPath()%>/product-details?id=<%=p.getProductId()%>"
                       class="view-btn">View</a>
                </div>

            </div>

            <% } %>

        </div>

    <% } else { %>

        <div class="no-products">
            <div class="no-products-icon">🔍</div>
            <h3>No products found</h3>
            <p>Try a different search term or browse all categories.</p>
            <a href="<%=request.getContextPath()%>/products" class="browse-all-btn">Browse All Products</a>
        </div>

    <% } %>

</div>

<jsp:include page="partials/footer.jsp" />

</body>
</html>