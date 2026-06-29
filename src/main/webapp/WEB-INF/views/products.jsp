<% if (products != null && !products.isEmpty()) { %>

    <% for(Product p : products) { %>

    <div class="product-card">

        <a href="<%=request.getContextPath()%>/product-details?id=<%=p.getProductId()%>"
           style="text-decoration:none;color:inherit;">

            <img src="<%=request.getContextPath()%>/<%=p.getImageUrl()%>"
                 alt="<%=p.getProductName()%>">

            <h3><%=p.getProductName()%></h3>

            <p><%=p.getBrand()%></p>

            <strong>₹ <%=p.getPrice()%></strong>

        </a>

        <form action="<%=request.getContextPath()%>/cart"
              method="post">

            <input type="hidden"
                   name="productId"
                   value="<%=p.getProductId()%>">

            <input type="hidden"
                   name="quantity"
                   value="1">

            <button type="submit">
                Add To Cart
            </button>

        </form>

    </div>

    <% } %>

<% } else { %>

    <h3>No products found</h3>

<% } %>