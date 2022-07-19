<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>INDEX JSP</title>
</head>
<body>
<%
	String msg = "안녕하세요";
%>
<form action="/index/gugu">
<div>몇단?: <input type="number" name="gugudan"></div>
<div><button type="submit">입력</button></div>
</form>
<h3><%=msg%></h3>
</body>
</html>