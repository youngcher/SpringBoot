<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div><a href="/mybatis/board/write_form">글쓰기</a></div>
<table>
<tr><th>번호</th><th>타이틀</th><th>내용</th><th>작성자</th><th>작성날짜</th><th>pcode</th></tr>
<c:forEach var="board" items="${pageInfo.list}">
<!--c:forEach var="board" items="${pageInfo.list}"-->
<tr>
<td>${board.num}</td>
<td><a href="/mybatis/board/detail/${board.num}">${board.title}</a></td>
<td>${board.contents}</td>
<td>${board.author}</td>
<td>${board.wdate}</td>
<td>${board.pcode}</td>
</tr>
</c:forEach>
</table>
<br>
<c:forEach var="i" items="${pageInfo.navigatepageNums}">
	<c:choose>
		<c:when test="${i==pageInfo.pageNum}">
			[${i}]
		</c:when>
		<c:otherwise>
			[<a href="/mybatis/board/pageList/page/${i}">${i}</a>]
		</c:otherwise>
	</c:choose>
</c:forEach>
<br>
<form method="post" action=/mybatis/board/serch>
	<select name="serchType">
		<option value="title">title</option>
		<option value="author">author</option>
		<option value="contents">contents</option>
	</select>
	<input type="text" name="serchKey">
	<input type="submit" value="검색하기">
</form>
</body>
</html>