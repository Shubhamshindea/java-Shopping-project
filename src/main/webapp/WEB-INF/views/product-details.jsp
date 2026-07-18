<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.fashionstore.model.Product" %>

<%
Product p = (Product) request.getAttribute("product");
if (p == null) {
%>
    <h2>Product not found</h2>
<%
    return;
}
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%=p.getProductName()%> - Fashion Store</title>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/theme.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/product-details.css">

    <script>
        function selectSize(el) {
            let sizes = document.querySelectorAll(".size-box");
            sizes.forEach(s => s.classList.remove("active"));
            el.classList.add("active");
            document.getElementById("selectedSize").value = el.innerText;
        }
    </script>
</head>

<body>

<jsp:include page="partials/navbar.jsp" />

<div class="page-container details-page">

    <div class="details-card">

        <!-- IMAGE -->
        <div class="image-box">
            <img src="<%=request.getContextPath()%>/<%= (p.getImageUrl() != null && !p.getImageUrl().isEmpty()) ? p.getImageUrl() : "assets/images/sample.jpg" %>" alt="<%=p.getProductName()%>">
        </div>

        <!-- DETAILS -->
        <div class="info-box">
            
            <p class="brand"><%=p.getBrand()%></p>
            
            <h2><%=p.getProductName()%></h2>

            <p class="price">₹ <%=p.getPrice()%></p>

            <h4>Description</h4>
            <p class="desc"><%=p.getDescription()%></p>

            <h4>Select Size</h4>
            <div class="sizes">
                <div class="size-box active" onclick="selectSize(this)">S</div>
                <div class="size-box" onclick="selectSize(this)">M</div>
                <div class="size-box" onclick="selectSize(this)">L</div>
                <div class="size-box" onclick="selectSize(this)">XL</div>
            </div>

            <!-- FORM -->
            <form action="<%=request.getContextPath()%>/cart" method="post">
                <input type="hidden" name="action" value="add">
                <input type="hidden" name="productId" value="<%=p.getProductId()%>">
                <input type="hidden" name="quantity" value="1">
                <input type="hidden" id="selectedSize" name="size" value="S">

                <div class="buttons">
                    <button type="submit" class="btn-primary add-btn">Add to Cart</button>
                    <a href="<%=request.getContextPath()%>/products" class="btn-secondary back-btn">
                        Cancel
                    </a>
                </div>
            </form>

        </div>

    </div>

</div>

<jsp:include page="partials/footer.jsp" />

</body>
</html>