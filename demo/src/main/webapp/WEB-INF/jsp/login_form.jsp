<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div><h3>login form</h3></div>
<form method="post" action="/user/login">
<div><label>id: </label><input type="text" id="uid" name="uid"></div>
<div><label>pw: </label><input type="password" id="upw" name="upw"></div>
<button type="submit">저장</button>
<button type="reset">취소</button>

</form></body>
</html>