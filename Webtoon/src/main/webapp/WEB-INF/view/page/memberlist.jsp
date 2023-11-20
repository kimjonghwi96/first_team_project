<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원목록</title>

<style>
body {
	min-height: 100vh;
	overflow-y: scroll;
}

/* 테이블 가운데 정렬을 위한 스타일 */
.center-table {
    margin: 0 auto;
    float: none;
}
    
</style>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet">

</head>
<body>

	<!-- 부트스트랩 및 jQuery 스크립트를 포함합니다 -->
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.min.js"></script>

<%@ include file="../include/menu.jsp"%>

	<table class="table table-info table-bordered">
		<thead>
			<th scope="col" style="width: 5%;"><strong>#</strong></th>
			<th scope="col" style="width: 12%;"><strong>아이디</strong></th>
			<th scope="col" style="width: 12%;"><strong>닉네임</strong></th>
			<th scope="col" style="width: 12%;"><strong>이메일</strong></th>
			<th scope="col" style="width: 12%;"><strong>휴대폰번호</strong></th>
			<th scope="col" style="width: 15%;"><strong>가입일</strong></th>
			<th scope="col" style="width: 15%;"><strong>최종접속일</strong></th>
			<th scope="col" style="width: 5%;"><strong>권한</strong></th>
			<th scope="col" style="width: 12%;"><strong>관리</strong></th>
		</thead>
		<c:forEach var="row" items="${list}">
		<tbody>
			<tr class="table-light">
				<td class="table-light"></td>
				<td class="table-light">${row.user_id}</td>
				<td class="table-light">${row.name}</td>
				<td class="table-light">${row.email}</td>
				<td class="table-light">${row.phone}</td>
				<td class="table-light"></td>
				<td class="table-light"></td>
				<td class="table-light">${row.admin_code}</td>
				<td class="table-light"></td>
			</tr>
		</tbody>
		</c:forEach>
	</table>

<%@ include file="../include/footer.jsp"%>
</body>
</html>