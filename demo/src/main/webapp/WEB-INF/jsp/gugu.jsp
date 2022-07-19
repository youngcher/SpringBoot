<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript">
	function req(){
		$.ajax({
			url:'/index/res3/dan/5',
			type:'post',
			dataType: 'json',
			success: function(res){
				//alert(res.save ? "저장성공" : "저장실패")
				alert(res.num);
			},
			error:function(xhr,status,err){
				alert('Error:'+err);
			}
		});
		return false;
	}

$(function(){
	$('#fruit').change(function(evt){
		var fruit = this.value;
		$.ajax({
			url:'/index/res4/'+fruit,
			type:'post',
			dataType:'json',
			success: function(res){
				alert(res.selection)
			}
		});
	});
});
</script>
</head>
<body>
<h3>구구단</h3>
<c:forEach var="line" items="${dan}">
<div>${line}</div>
</c:forEach>
<hr>
<div>
<c:forEach var="dan" begin="2" end="9">
	<a href="/index/gugu/${dan}">${dan}</a>
</c:forEach>
</div>
<hr>
<div><button type="button" onclick="req();">저장</button></div>
<hr>
<select name="fruit" id="fruit">
	<option value="apple">사과</option>
	<option value="banana">바나나</option>
	<option value="orange">오렌지</option>
	<option value="rbf">귤</option>
</select>
</body>
</html>