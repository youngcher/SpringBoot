<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript">
//function edit_form(empno){
//	var obj = {};
//	obj.empno = empno;
//	$.ajax({
//		url:'/jdbc/emp/edit_form',
//		method: 'post',
//		cache: false,
//		data: obj,
//		dataType: 'json',
//		success:function(res){
//			alert("저장 하시겠어요?")
//		}
//	})
//}


</script>
</head>
<body>
<!--  form id="edit_form" onclick="return edit_form(${emp.empno});"-->
<table>
<tr><th>사원번호</th><th>이름</th><th>입사날짜</th><th>급여</th><th>부서</th></tr>
<tr>
<td>${emp.empno}</td>
<td>${emp.ename}</td>
<td>${emp.hiredate}</td>
<td>${emp.sal}</td>
<td>${emp.deptno}</td>
</tr>
</table>
<a href="/jdbc/emp/edit_form/${emp.empno}">수정</a>
<!--div><button type="submit">수정</button></div-->
</form>
</body>
</html>