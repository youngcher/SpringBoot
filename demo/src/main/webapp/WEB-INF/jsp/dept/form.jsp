<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>DEPT Test Form</title>
</head>
<body>
<main>
	<h3>DEPT 테스트용 폼</h3>
	<form id="sampleForm" method="post" action="/mybatis/dept/deptList">
		<select name="deptno" multiple>
			<option value="10">10</option>
			<option>20</option>
			<option>30</option>
			<option>40</option>
		</select>
		<div><button type="submit">제출</button><button type="reset">취소</button></div>
	</form>
</main>
</body>
</html>