<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Upload Form</title>
<script src = "http://code.jquery.com/jquery-latest.js"></script>
<script>
    function fn_addFile(){
        $("#d_file").append("<br>" + "<input type='file' name='files' multiple='multiple'/>");
        cnt++;
    }
</script>
</head>
<body>
<h3>Spring boot 파일 업로드 테스트</h3>
<form action="/files/upload" method="post" enctype="multipart/form-data">
   작성자 <input type="text" name="writer" value="smith"><br>
   설명  <input type="text" name="comments"><br>
   File <input type="file" name="files" multiple="multiple"><br>
   <input type="button" value="파일 추가" onClick="fn_addFile()"><br>
            <div id="d_file">
            
            </div>
   <br>
   <button type="submit">업로드</button>
</form>
</body>
</html>