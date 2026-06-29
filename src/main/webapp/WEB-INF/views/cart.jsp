<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Shopping Cart</title>

```
<style>
    body{
        font-family:Arial,sans-serif;
        background:#f5f5f5;
        margin:0;
    }

    .cart-container{
        width:80%;
        margin:40px auto;
        background:white;
        padding:30px;
        border-radius:10px;
        box-shadow:0 2px 10px rgba(0,0,0,0.1);
    }

    h2{
        margin-bottom:20px;
    }

    .btn{
        background:#007bff;
        color:white;
        padding:10px 20px;
        text-decoration:none;
        border-radius:5px;
    }
</style>
```

</head>

<body>

<jsp:include page="partials/navbar.jsp" />

<div class="cart-container">

```
<h2>Shopping Cart</h2>

<p>Cart page created successfully.</p>

<a class="btn"
   href="<%=request.getContextPath()%>/products">
    Continue Shopping
</a>
```

</div>

</body>
</html>
