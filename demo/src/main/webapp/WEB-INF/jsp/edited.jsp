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
	$.ajax({
		url: '/mybatis/board/edited',
		method: 'post',
		cache: false,
		data: $('#edited').serialize(),
		dataType: 'json',
		success: function(res){
			if(res.result){
				alert('수정 성공');
				location.href="/mybatis/board/list";
			} else {
				alert('수정 실패');
				location.href="/mybatis/board/list"
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
<form id="edited" onsubmit="return edited();">
<input type="hidden" name="num" value="${board.num}">
<table>
<tr><td>index</td><td>${board.num}</td></tr>
<tr><td>제목</td><td><input type="text" name="title" value="${board.title}"></td></tr>
<tr><td>내용</td><td><input type="text" name="contents" value="${board.contents}"></td></tr>
<tr><td>작성자</td><td>${board.author}</td></tr>
<tr><td>작성일</td><td>${board.wdate}</td></tr>
<tr><td>pcode</td><td>${board.pcode}</td></tr>
</table>
<div><button type="submit">수정</button></div>
</form>
</body>
</html>