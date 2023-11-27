<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<title>웹툰 업로드</title>
<form action="wt_json_file_upload" method="post"
	enctype="multipart/form-data">

		<label for="jsonFile" class="form-label">json파일 업로드</label> <br>
		<input type="file" name="jsonFile" id="jsonFile"> <input
			type="submit" value="Upload JSON File">
</form>




<form action="wt_json_upload" method="post"
	enctype="multipart/form-data">

	<label for="WTJson" class="form-label">json String 업로드</label> <br>
	<textarea rows="30" cols="200" name="WTJson" id="WTJson"></textarea>
	<br>
	<button type="submit">등록</button>
</form>

<button onclick="redirectToMain()">메인으로</button>

<script>
    function redirectToMain() {
        window.location.href = '/page/main';
    }
</script>
