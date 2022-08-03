<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="utf-8">
<head>
    <title>Log in with your credentials</title>
</head>
<body>

   <!-- Spring Security default action url is '/login' -->
    <form method="POST" action="/doLogin">
       <%-- <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> --%>
        <h2>Log in</h2>
        <div>
            <input name="id" type="text" value="employee" autofocus="true"/>
        </div>
        <div>
            <input name="pw" type="password" value="employee"/>
      </div>
      <div>
            <button type="submit">Log In</button>
        </div>
    </form>
</html>