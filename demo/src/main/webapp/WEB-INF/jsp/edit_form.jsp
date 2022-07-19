<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript">
function edited(){
	//alert("수정입니다.");
	$.ajax({
		url: '/jdbc/emp/edit',
		method: 'post',
		cache: false,
		data : $('#edit_form').serialize(),
		dataType: 'json',
		success:function(res){
			if(res.edited){
				alert('수정 성공');
				location.href="/jdbc/emp/all";
			} else {
				alert('수정 실패');
				location.href="/jdbc/emp/all";
			}
		},
		error: function(xhr,status,err){
			alert('Error:'+err);
		}
			
	});
	return false;
}
</script>
</head>
<body>
<form id="edit_form" onsubmit="return edited();">
<input type="hidden" name="empno" value="${emp.empno}">
<table>
<tr><th>사원번호</th><th>이름</th><th>입사날짜</th><th>급여</th><th>부서</th></tr>
<tr>
<td>${emp.empno}</td>
<td>${emp.ename}</td>
<td>${emp.hiredate}</td>
<td><input type="number" name="sal" value="${emp.sal}"></td>
<td><input type="number" name="deptno" value="${emp.deptno}"></td>
</tr>
</table>
<div>
<button type="submit">수정</button>
<button type="reset">취소</button>
</div>
</form>
</body>
</html>