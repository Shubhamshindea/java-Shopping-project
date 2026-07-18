<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - Fashion Store</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/auth.css">
</head>
<body>

<a href="<%=request.getContextPath()%>/home" class="back-home">← Back to Store</a>

<div class="auth-page">
    <div class="auth-card register">
        
        <div class="auth-header">
            <h1>Create an Account</h1>
            <p>Join our fashion community today</p>
        </div>

        <% 
            String errorMsg = (String) request.getAttribute("error");
            if (errorMsg != null) {
        %>
            <div class="auth-alert error"><%=errorMsg%></div>
        <% } %>

        <form action="<%=request.getContextPath()%>/register" method="post" class="auth-form">
            
            <div class="form-row">
                <div class="form-group">
                    <label for="fullName">Full Name</label>
                    <input type="text" id="fullName" name="fullName" placeholder="John Doe" required>
                </div>
                
                <div class="form-group">
                    <label for="email">Email Address</label>
                    <input type="email" id="email" name="email" placeholder="you@example.com" required>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="phone">Phone Number</label>
                    <input type="tel" id="phone" name="phone" placeholder="+91 9876543210" required>
                </div>
                
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" placeholder="••••••••" required>
                </div>
            </div>

            <button type="submit" class="btn-primary auth-btn">Register</button>
        </form>

        <div class="auth-footer">
            Already have an account? <a href="<%=request.getContextPath()%>/login">Sign in here</a>
        </div>
        
    </div>
</div>

</body>
</html>