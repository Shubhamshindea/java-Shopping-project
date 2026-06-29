<div class="auth-wrapper">

    <div class="auth-container">

        <!-- LEFT PANEL -->
        <div class="auth-left">
            <h2>Fashion Store</h2>
            <p>Shop the latest trends</p>
        </div>

        <!-- RIGHT PANEL -->
        <div class="auth-right">

            <h2>Login</h2>

            <form action="<%=request.getContextPath()%>/login" method="post">

                <div class="input-group">
                    <input type="email" name="email" required>
                    <label>Email</label>
                </div>

                <div class="input-group">
                    <input type="password" name="password" required>
                    <label>Password</label>
                </div>

                <button class="auth-btn">Login</button>

            </form>

            <p class="bottom-text">
                Don't have account?
                <a href="<%=request.getContextPath()%>/register">Register</a>
            </p>

        </div>

    </div>

</div>