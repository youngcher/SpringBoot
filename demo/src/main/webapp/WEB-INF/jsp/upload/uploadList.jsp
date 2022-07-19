<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:forEach var="map" items="${list}">
글번호: <a href="/files/detail/${map.num}">${map.num}</a>
작성자: ${map.writer}
작성일: ${map.udate}
설명: ${map.comments}
파일명:
<c:forEach var="fname" items="${map.fname}">
<a href="/files/download/${map.num}/${fname}">${fname}</a>,
</c:forEach>
<br>
</c:forEach>
</body>
</html>