<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Confirmed - Fashion Store</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/checkout.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>

<jsp:include page="partials/navbar.jsp" />

<%
    Integer orderId     = (Integer) request.getAttribute("orderId");
    Double totalPrice   = (Double)  request.getAttribute("totalPrice");
    String paymentMethod = (String) request.getAttribute("paymentMethod");
%>

<div class="success-page">

    <div class="success-card">
        <div class="success-icon">✅</div>
        <h1>Order Placed Successfully!</h1>
        <p class="success-subtitle">Thank you for shopping with Fashion Store.</p>

        <div class="order-details-box">
            <div class="order-detail-row">
                <span>Order ID</span>
                <strong>#FS-<%= orderId != null ? orderId : "N/A" %></strong>
            </div>
            <div class="order-detail-row">
                <span>Total Amount</span>
                <strong>₹ <%= totalPrice != null ? String.format("%.2f", totalPrice) : "0.00" %></strong>
            </div>
            <div class="order-detail-row">
                <span>Payment</span>
                <strong><%= paymentMethod != null ? paymentMethod : "N/A" %></strong>
            </div>
            <div class="order-detail-row">
                <span>Status</span>
                <span class="status-badge">PENDING</span>
            </div>
        </div>

        <p class="delivery-message">
            🚚 Your order will be delivered in <strong>3-5 business days</strong>.
        </p>

        <div class="success-actions">
            <a href="<%=request.getContextPath()%>/home" class="home-btn">🏠 Back to Home</a>
            <a href="<%=request.getContextPath()%>/products" class="continue-btn">🛍️ Continue Shopping</a>
        </div>
    </div>

</div>

<jsp:include page="partials/footer.jsp" />

</body>
</html>
