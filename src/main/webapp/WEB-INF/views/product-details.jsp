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

<html>
<head>
    <title><%=p.getProductName()%></title>

    <link rel="stylesheet"
          href="<%=request.getContextPath()%>/assets/css/product-details.css">

    <script>
        function selectSize(el) {
            // remove active from all
            let sizes = document.querySelectorAll(".size-box");
            sizes.forEach(s => s.classList.remove("active"));

            // add active to clicked
            el.classList.add("active");

            // set hidden value
            document.getElementById("selectedSize").value = el.innerText;
        }
    </script>
</head>

<body>
<div class="details-container">

    <div class="details-card">

        <!-- IMAGE -->
        <div class="image-box">
            <img src="<%=request.getContextPath()%>/<%= (p.getImageUrl() != null ? p.getImageUrl() : "assets/images/sample.jpg") %>">
        </div>

        <!-- DETAILS -->
        <div class="info-box">

            <h2><%=p.getProductName()%></h2>

            <p class="brand"><%=p.getBrand()%></p>

            <p class="price">₹ <%=p.getPrice()%></p>

            <h4>Description</h4>
            <p class="desc"><%=p.getDescription()%></p>

            <!-- SIZE -->
            <h4>Select Size</h4>

            <div class="sizes">
                <div class="size-box" onclick="selectSize(this)">S</div>
                <div class="size-box" onclick="selectSize(this)">M</div>
                <div class="size-box" onclick="selectSize(this)">L</div>
                <div class="size-box" onclick="selectSize(this)">XL</div>
            </div>

            <!-- FORM -->
            <form action="<%=request.getContextPath()%>/cart" method="post">

                <input type="hidden" name="productId" value="<%=p.getProductId()%>">
                <input type="hidden" id="selectedSize" name="size">

                <div class="buttons">
                    <button type="submit" class="add-btn">Add to Cart</button>

                    <a href="<%=request.getContextPath()%>/products">
                        <button type="button" class="back-btn">Back</button>
                    </a>
                </div>

            </form>

        </div>

    </div>

</div>

</body>
</html>