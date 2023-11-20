<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>





<div class="form-floating">
  <textarea class="form-control" placeholder="Leave a comment here" id="NewComment" name="NewComment"></textarea>
  <label for="floatingTextarea">한줄평 : <span id="charCount">50자</span></label>
  
  <br>

   <div class="d-grid gap-2">
	  <button id="CommentSubmitButton" class="btn btn-primary">등록</button>
   </div>

</div>

<hr>



<ol class="list-group">
  <c:forEach items="${cmtList}" var="cmtList">
  <li class="list-group-item d-flex justify-content-between align-items-start">
    <div class="ms-2 me-auto">
      <div class="fw-bold text-primary"><c:out value="${cmtList.name}" /></div>
      <c:out value="${cmtList.comments_content}" />
    </div>
    
    <c:if test="${member.admin_code == 1}">
					
<!-- 					&nbsp; <a class="btn btn-primary mt-auto" href="">수정하기</a> &nbsp; -->
<%-- 					<c:set var="Webtoon_id" value="${webtoon_id}" /> --%>
					<input type="hidden" value="<c:out value="${Webtoon_id}"/>">
					<button class="btn btn-danger mt-auto" onclick="">삭제</button>
					
	</c:if>    
  </li>
  </c:forEach>
  
</ol>
<!-- createElement -->

<%@ include file="./wt_recommend.jsp" %>

<script>


$("#CommentSubmitButton").click(function() {
	$.ajax({
		type : 'POST',
		url : 'wt_contents', // 서버 측 코드의 URL
		data : {
			user_id : user_id.value,
			webtoon_id : webtoon_id.value,
			NewComment: $('#NewComment').val()
			
		},
		cache : false, // 캐시 사용하지 않음
		dataType : 'json', // 서버에서 받아온 데이터의 형식
		success : function(commentSuccess) {
			
// 			alert(commentSuccess)
			if(commentSuccess == 1){
// 				alert("한줄평이 등록되었습니다.");
				location.reload();
				
			} else {
				alert("이미 한줄평을 등록하셨습니다.");
				
			}


		},
		error : function(xhr, textStatus, errorThrown) {
			// 서버 요청 자체가 실패한 경우 (예: 네트워크 문제 등)
			// 에러 처리를 수행하거나 사용자에게 오류 메시지를 표시할 수 있음
			console.error('서버 요청 실패:', textStatus, errorThrown);
			alert("요청실패")
		}
	});

});

//HTML 요소를 가져옵니다.
const textarea = document.getElementById('NewComment');
const charCount = document.getElementById('charCount');

// 최대 글자 수를 정의합니다.
const maxLength = 50;

// textarea의 입력 이벤트를 모니터링합니다.
textarea.addEventListener('input', function() {
    const text = textarea.value;
    const remainingChars = maxLength - text.length;

    // 글자 수를 업데이트합니다.
    charCount.textContent = remainingChars;

    // 글자 수가 제한을 초과하면 잘라냅니다.
    if (remainingChars < 0) {
        textarea.value = text.slice(0, maxLength);
        charCount.textContent = 0;
    }
});

// $("#CommentSubmitButton").click(function() {
// 	$.ajax({
// 		type : 'GET',
// 		url : 'wt_detail', // 서버 측 코드의 URL
// 		cache : false, // 캐시 사용하지 않음
// 		dataType : 'json', // 서버에서 받아온 데이터의 형식
// 		success : function(commentSuccess) {
			
// 			alert(commentSuccess)
// 			if(commentSuccess == 1){
				
				
// 			} else {
				
// 			}


// 		},
// 		error : function(xhr, textStatus, errorThrown) {
// 			// 서버 요청 자체가 실패한 경우 (예: 네트워크 문제 등)
// 			// 에러 처리를 수행하거나 사용자에게 오류 메시지를 표시할 수 있음
// 			console.error('서버 요청 실패:', textStatus, errorThrown);
// // 			alert("요청실패")
// 		}
// 	});

// });

</script>




<!-- 회원과 관리자만 보이는것 -->
<!-- 회원 -->
<!--     <a href="../page/wtcmt_modify" class="btn btn-primary btn-sm">수정</a> -->
<!-- 	<button type="button" class="btn btn-danger btn-sm">삭제</button> -->
<!-- 관리자 -->
<!-- 	<button type="button" class="btn btn-danger btn-sm">삭제</button> -->
