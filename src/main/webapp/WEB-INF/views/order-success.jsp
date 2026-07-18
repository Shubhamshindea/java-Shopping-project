<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Successful - Fashion Store</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/theme.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/checkout.css">
</head>
<body>

<jsp:include page="partials/navbar.jsp" />

<div class="page-container success-page">

    <div class="success-card">
        
        <div class="success-icon">✓</div>
        
        <h1>Order Placed Successfully!</h1>
        
        <p>Thank you for shopping with us. Your order has been confirmed and is being processed.</p>

        <% 
            Integer orderId = (Integer) request.getAttribute("orderId");
            if (orderId != null) { 
        %>
            <div class="order-id">Order ID: #<%=orderId%></div>
        <% } %>

        <br>
        
        <a href="<%=request.getContextPath()%>/home" class="btn-primary">Continue Shopping</a>
        
    </div>

</div>

<jsp:include page="partials/footer.jsp" />

</body>
</html>
