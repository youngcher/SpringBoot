<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div><a href="/jpa/board/write_form">글쓰기</a></div>
<table>
<tr><th>번호</th><th>타이틀</th><th>내용</th><th>작성자</th><th>작성날짜</th></tr>
<c:forEach var="board" items="${pageinfo.content}">
<!--c:forEach var="board" items="${pageInfo.list}"-->
<tr>
<td>${board.num}</td>
<td><a href="/jpa/board/detail/${board.num}">${board.title}</a></td>
<td>${board.contents}</td>
<td>${board.author}</td>
<td>${board.wdate}</td>
</tr>
</c:forEach>
</table>
<br>
<c:forEach var="i" begin="0" end="${pageinfo.totalPages-1}">
	<c:choose>
		<c:when test="${i==pageinfo.number}">
			[${i+1}]
		</c:when>
		<c:when test="${i>=(pageinfo.number-2) && i<=(pageinfo.number+2)}">	
			[<a href="/jpa/board/getpage/list?size=1&page=${i}">${i+1}</a>]
		</c:when>
	</c:choose>
</c:forEach>
<br>
</body>
</html>