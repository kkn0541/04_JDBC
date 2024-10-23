<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%--   
 "/" (최상위 경로) 요청시 index.jsp 화면이 보여지게 되는데 
 여기서 다른 servlet이 응답할수 있또록 "/" 로 온 요청을 위임 
 
 "/" 요청이 오면 "/main" 서블릿으로 여청을 위임 
프로젝트 실행 -> "/" -> index.jsp ->"/main" -> mainservlet->main.jsp
--%>
    
    
<jsp:forward page="/main"/>