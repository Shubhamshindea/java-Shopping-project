<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Fashion Store</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/auth.css">
</head>
<body>

<a href="<%=request.getContextPath()%>/home" class="back-home">← Back to Store</a>

<div class="auth-page">
    <div class="auth-card">
        
        <div class="auth-header">
            <h1>Welcome Back</h1>
            <p>Sign in to continue shopping</p>
        </div>

        <% 
            String errorMsg = (String) request.getAttribute("error");
            String successMsg = (String) request.getAttribute("success");
            
            if (errorMsg != null) {
        %>
            <div class="auth-alert error"><%=errorMsg%></div>
        <% } %>
        
        <% if (successMsg != null) { %>
            <div class="auth-alert success"><%=successMsg%></div>
        <% } %>

        <form action="<%=request.getContextPath()%>/login" method="post" class="auth-form">
            <div class="form-group">
                <label for="email">Email Address</label>
                <input type="email" id="email" name="email" placeholder="you@example.com" required>
            </div>
            
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" placeholder="••••••••" required>
            </div>
            
            <button type="submit" class="btn-primary auth-btn">Sign In</button>
        </form>

        <div class="auth-footer">
            Don't have an account? <a href="<%=request.getContextPath()%>/register">Create one here</a>
        </div>
        
    </div>
</div>

</body>
</html>