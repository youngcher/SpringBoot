<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript">
function toSave(){
		$.ajax({
		url:'/board/save',
		method:'post',
		cache: false,
		data : $('#write_form').serialize(),
		dataType:'json',
		success:function(res){
			alert(res.result ? '저장 성공' : '저장 실패');
			location.href="/user/login";
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
<form id="write_form" onsubmit="return toSave();">
	<!-- input type="hidden" name="pcode" value="${board.pcode}" -->
<div><label>타이틀</label><input type="text" id="title" name="title"></div>
<div><label>내용</label><textarea id="contents" name="contents" placeholder="글 입력..."></textarea></div>
<button type="submit">저장</button>
<button type="reset">취소</button>
</form>
</body>
</html>