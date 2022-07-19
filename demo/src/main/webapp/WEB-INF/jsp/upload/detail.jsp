<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript">
function deleted(){
	$.ajax({
		url:'/files/remove/${vo.num}',
		method: 'get',
		cache: false,
		dataType: 'json',
		success: function(res){
			if(res.result){
				alert('삭제 성공');
				location.href="/files/detail/${vo.num}";
			} else {
				alert('삭제 실패');
				return false;
			}
		},
		error: function(xhr, status, err){
			alert('Error:'+err);
		}
	});
	return false;
}

function addfiles(){
	var formData = new FormData($("#add")[0]);
	$.ajax({
		url:'/files/addfiles',
		enctype: 'multipart/form-data',
		processData: false,
		contentType: false,
		method: 'post',
		cache: false,
		data : formData,
		dataType: 'json',
		success: function(res){
			if(res.result){
				alert('추가 성공');
				location.href="/files/detail/${vo.num}";
			} else {
				alert('추가 실패');
				return false;
			}
		},
		error: function(xhr, status, err){
			alert('Error:'+err);
		}
	});
	return false;
}

function allDeleted(){
	$.ajax({
		url:'/files/allremove/${vo.num}',
		method: 'get',
		cache: false,
		dataType: 'json',
		success: function(res){
			if(res.result){
				alert('삭제 성공');
				location.href="/files/upload";
			} else {
				alert('삭제 실패');
				return false;
			}
		},
		error: function(xhr, status, err){
			alert('Error:'+err);
		}
	});
	return false;
}

</script>
<title>Insert title here</title>
</head>
<body>
글번호: ${vo.num}
작성자: ${vo.writer}
작성일: ${vo.udate}
설명: ${vo.comments}
파일명:
<c:forEach var="fname" items="${vo.fname}">
<a href="/files/download/${vo.num}/${fname}">${fname}</a>,
</c:forEach>
<br>
<a href="javascript:deleted();">첨부파일 삭제</a>

<form id="add" onsubmit="return addfiles();">
   <input type="hidden" name="num" value="${vo.num}"> 
   첨부파일 추가 <input type="file" name="files" multiple="multiple"><br>
   <button type="submit">업로드</button>
</form>
<br>
<a href="javascript:allDeleted();">게시물 삭제</a>

</body>
</html>