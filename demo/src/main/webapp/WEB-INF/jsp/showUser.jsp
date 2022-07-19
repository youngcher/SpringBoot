<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript">
function logout(){
	$.ajax({
	url:'/user/logout',
	method:'post',
	cache: false,
	dataType:'json',
	success:function(res){
		alert(res.result ? '로그아웃 성공' : '로그아웃 실패');
	},
	error:function(xhr,status,err){
		alert(err);
	}
	});
	return false;
}
</script>
</head>
<body>
<div><a href="/user/write">글쓰기</a></div>
<div><a href="javascript:logout();">로그아웃</a></div>
</body>
</html>