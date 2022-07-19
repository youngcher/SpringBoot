<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript">
function writed(){
	$.ajax({
		//url: '/jdbc/board/write',
		url: '/mybatis/board/write',
		method: 'post',
		cache: false,
		data: $('#writed_form').serialize(),
		dataType: 'json',
		success: function(res){
			if(res.result){
				alert('등록 성공');
				//location.href="/jdbc/board/list";
				location.href="/mybatis/board/list";
			} else {
				alert('등록 실패');
				//location.href="/jdbc/board/list"
				location.href="/mybatis/board/list";
			}
		},
		error: function(xhr, status, err){
			alert('Error:'+err);
		}
	});
	return false;
}

</script>
</head>
<body>
<form id="writed_form" onsubmit="return writed();">
<div>title : <input type="text" name="title"> </div>
<div>contents : <input type="text" name="contents"></div>
<div>author : <input type="text" name="author"></div>
<div>pcode : <input type="number" name="pcode" value="0"></div>
<div>
<button type="submit">저장</button>
<button type="reset">취소</button>
</div>
</form>
</body>
</html>