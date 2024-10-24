<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>${todo.title}수정 페이지</title>
</head>
<body>
  
  <h4>할 일 수정</h4>

  <%-- 
    /todo/update - POST 방식 요청
                -> Servlet의 doPost() 오버라이딩
  --%>
  <form action="/todo/update" method="post" id="updateForm">
    <div>
      제목 : <input type="text" name="title" value="${todo.title}">
    </div>
    <div>
      <textarea name="detail" 
        rows="3" cols="50" placeholder="상세 내용">${todo.detail}</textarea>
    </div>

    <%-- todoNo 도 수정 요청 시 파라미터로 보내기
    	왜 필요하냐 ? 
    	어떤 todoNo를 가진행을 수정하고자 하는지 
    	sql의 조건문에서 이용해야하기 때문이다.
    
    
    
     param. ->url(/todo/update?todoNo=10)에 있는 ? todoNo=10
    EL 표현식에서 ${param.key}-> ${param.todoNo} -> 10 이 반환 
     --%>
    <input type="hidden" name="todoNo" value="${param.todoNo}">
    
    
    <button>수정</button>
  </form>

	<%-- session 스코프에 message 있으면 alert 출력하기 --%>
	<c:if test="${not empty sessionScope.message}">
		<script>
			alert("${message}");
		</script>

		<c:remove var="message" scope="session" />


	</c:if>


</body>
</html>