<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<table>
<tr><th>사원번호</th><th>이름</th><th>입사날짜</th></tr>
<c:forEach var="emp" items="${list}">
<tr><td>${emp.empno}</td><td><a href="/jdbc/emp/detail/${emp.empno}">${emp.ename}</a></td><td>${emp.hiredate}</td></tr>
</c:forEach>
</table>
</body>
</html>