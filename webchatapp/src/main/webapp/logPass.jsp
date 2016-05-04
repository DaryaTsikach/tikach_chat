<%--
  Created by IntelliJ IDEA.
  User: Даша
  Date: 04.05.2016
  Time: 20:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Form</title>
</head>
<body>
<form action="/login" method="post">
    <input name="username">
    <input name="pass" type="password">
    <button type="submit">Submit</button>
    <span class="error">${error}</span>
</form>

</body>
</html>
