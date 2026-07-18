<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<footer class="footer">
    <div class="footer-container">

        <!-- BRAND -->
        <div class="footer-brand">
            <h2>Fashion<span>Store</span></h2>
            <p>Elevating everyday style with premium quality apparel and accessories. Shop the latest trends and discover your unique aesthetic.</p>
        </div>

        <!-- QUICK LINKS -->
        <div class="footer-links">
            <h3>Shop</h3>
            <ul>
                <li><a href="<%=request.getContextPath()%>/products">New Arrivals</a></li>
                <li><a href="<%=request.getContextPath()%>/products">Trending</a></li>
                <li><a href="<%=request.getContextPath()%>/products">Men's Wear</a></li>
                <li><a href="<%=request.getContextPath()%>/products">Women's Wear</a></li>
            </ul>
        </div>

        <div class="footer-links">
            <h3>Support</h3>
            <ul>
                <li><a href="#">FAQ</a></li>
                <li><a href="#">Shipping & Returns</a></li>
                <li><a href="#">Size Guide</a></li>
                <li><a href="#">Contact Us</a></li>
            </ul>
        </div>

        <!-- NEWSLETTER -->
        <div class="footer-newsletter">
            <h3>Stay in the Loop</h3>
            <p>Subscribe to get special offers, free giveaways, and once-in-a-lifetime deals.</p>
            <form class="newsletter-form" onsubmit="event.preventDefault();">
                <input type="email" placeholder="Enter your email" required>
                <button type="submit">Subscribe</button>
            </form>
        </div>

    </div>

    <!-- BOTTOM BAR -->
    <div class="footer-bottom">
        <p>&copy; 2026 Fashion Store. All rights reserved.</p>
        <div class="social-links">
            <a href="#">📱</a>
            <a href="#">📸</a>
            <a href="#">🐦</a>
        </div>
    </div>
</footer>