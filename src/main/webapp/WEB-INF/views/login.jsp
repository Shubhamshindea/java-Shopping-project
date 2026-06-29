<%@ page contentType="text/html;charset=UTF-8" %>

<%
boolean showRegister = "register".equals(request.getParameter("mode"));
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Fashion Store</title>

<link rel="stylesheet"
href="<%=request.getContextPath()%>/assets/css/auth.css">

</head>

<body>

<div class="auth-wrapper">

    <div class="auth-container">

        <!-- LEFT SIDE -->

        <div class="auth-left">

            <h1>Fashion Store</h1>

            <p>
                Shop the latest fashion trends and collections.
            </p>

            <img
            src="<%=request.getContextPath()%>/assets/images/shopping.png"
            alt="shopping">

        </div>

        <!-- RIGHT SIDE -->

        <div class="auth-right">

            <% if(!showRegister){ %>

            <h2>Welcome Back</h2>

            <form action="<%=request.getContextPath()%>/login"
                  method="post">

                <input type="email"
                       name="email"
                       placeholder="Email"
                       required>

                <input type="password"
                       name="password"
                       placeholder="Password"
                       required>

                <button type="submit">
                    Login
                </button>

            </form>

            <div class="links">

                <a href="<%=request.getContextPath()%>/login?mode=register">
                    Create Account
                </a>

                <a href="<%=request.getContextPath()%>/home">
                    Continue as Guest
                </a>

            </div>

            <% } else { %>

            <h2>Create Account</h2>

            <form action="<%=request.getContextPath()%>/register"
                  method="post">

                <input type="text"
                       name="name"
                       placeholder="Full Name"
                       required>

                <input type="email"
                       name="email"
                       placeholder="Email"
                       required>

                <input type="password"
                       name="password"
                       placeholder="Password"
                       required>

                <button type="submit">
                    Register
                </button>

            </form>

            <div class="links">

                <a href="<%=request.getContextPath()%>/login">
                    Already have account? Login
                </a>

            </div>

            <% } %>

        </div>

    </div>

</div>

</body>
</html>