<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, com.fashionstore.model.CartItem, com.fashionstore.model.Product" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Checkout - Fashion Store</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/theme.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/checkout.css">
</head>
<body>

<jsp:include page="partials/navbar.jsp" />

<%
    List<CartItem> cartItems = (List<CartItem>) request.getAttribute("cartItems");
    List<Product> cartProducts = (List<Product>) request.getAttribute("cartProducts");
    double totalPrice = 0;
    if (request.getAttribute("totalPrice") != null) {
        totalPrice = (double) request.getAttribute("totalPrice");
    }
    String errorMsg = (String) request.getAttribute("error");
%>

<div class="page-container checkout-page">

    <div class="checkout-header">
        <h1>Secure Checkout</h1>
    </div>

    <% if (errorMsg != null) { %>
        <div class="alert-error">❌ <%= errorMsg %></div>
    <% } %>

    <form action="<%=request.getContextPath()%>/checkout" method="post" class="checkout-layout">

        <!-- CHECKOUT FORM (LEFT) -->
        <div class="checkout-form-container">
            
            <div class="form-section">
                <h2>1. Shipping Information</h2>
                
                <div class="form-row">
                    <div class="form-group">
                        <label>First Name</label>
                        <input type="text" name="firstName" required>
                    </div>
                    <div class="form-group">
                        <label>Last Name</label>
                        <input type="text" name="lastName" required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group full-width">
                        <label>Address</label>
                        <input type="text" name="address" required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label>City</label>
                        <input type="text" name="city" required>
                    </div>
                    <div class="form-group">
                        <label>Postal Code</label>
                        <input type="text" name="postalCode" required>
                    </div>
                </div>
                
                <div class="form-row">
                    <div class="form-group">
                        <label>Phone Number</label>
                        <input type="tel" name="phone" required>
                    </div>
                </div>
            </div>

            <div class="form-section">
                <h2>2. Payment Method</h2>
                
                <div class="payment-options">
                    <label class="payment-option">
                        <input type="radio" name="paymentMethod" value="CREDIT_CARD" checked onchange="toggleQR(false)">
                        <div class="payment-card">💳 Credit Card</div>
                    </label>

                    <label class="payment-option">
                        <input type="radio" name="paymentMethod" value="UPI" onchange="toggleQR(true)">
                        <div class="payment-card">📱 UPI</div>
                    </label>

                    <label class="payment-option">
                        <input type="radio" name="paymentMethod" value="COD" onchange="toggleQR(false)">
                        <div class="payment-card">💵 Cash on Delivery</div>
                    </label>
                </div>

                <div id="upi-qr-section" style="display: none; text-align: center; margin-top: 24px; padding: 20px; border: 1px dashed var(--color-primary); border-radius: var(--radius-md);">
                    <h3 style="margin-bottom: 12px; color: var(--color-primary);">Scan to Pay</h3>
                    <img src="<%=request.getContextPath()%>/assets/images/upi_qr.jpg" alt="UPI QR Code" style="max-width: 200px; border-radius: 8px; margin: 0 auto;">
                    <p style="margin-top: 12px; font-size: 14px; color: var(--color-text-muted);">Please scan the QR code using PhonePe, GPay, or Paytm.</p>
                </div>
                
                <script>
                    function toggleQR(show) {
                        document.getElementById('upi-qr-section').style.display = show ? 'block' : 'none';
                    }
                </script>
            </div>

        </div>

        <!-- ORDER SUMMARY (RIGHT) -->
        <div class="checkout-summary">
            <h2>Order Summary</h2>
            
            <div class="summary-items">
                <% 
                    if (cartItems != null && cartProducts != null) {
                        for (int i = 0; i < cartItems.size(); i++) {
                            CartItem item = cartItems.get(i);
                            Product prod = cartProducts.get(i);
                            if (prod == null) continue;
                %>
                <div class="summary-item">
                    <span class="item-name"><%=item.getQuantity()%>x <%=prod.getProductName()%></span>
                    <span class="item-price">₹ <%=Math.round(prod.getPrice().doubleValue() * item.getQuantity())%></span>
                </div>
                <%      }
                    } 
                %>
            </div>

            <div class="summary-totals">
                <div class="summary-row">
                    <span>Subtotal</span>
                    <span>₹ <%=String.format("%.2f", totalPrice)%></span>
                </div>
                <div class="summary-row">
                    <span>Shipping</span>
                    <span style="color: var(--color-success); font-weight:700;">FREE</span>
                </div>
                <div class="summary-row summary-total">
                    <span>Total</span>
                    <span>₹ <%=String.format("%.2f", totalPrice)%></span>
                </div>
            </div>

            <button type="submit" class="place-order-btn">Place Order</button>
        </div>

    </form>

</div>

<jsp:include page="partials/footer.jsp" />

</body>
</html>
