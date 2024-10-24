package edu.kh.todoList.controller;

import java.io.IOException;

import edu.kh.todoList.service.TodoListSerivce;
import edu.kh.todoList.service.TodoListServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/todo/complete")
public class CompleteServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
						//전달받은 파라미터 얻어오기 
			int todoNo=Integer.parseInt(req.getParameter("todoNo"));
			
			//할일 여부를 변경하는 서비스 호출 후 결과 반환받기 
			TodoListSerivce service = new TodoListServiceImpl();
			
			int result = service.todoComplete(todoNo);

			//변경 성공 시 
			// ->원래 보고 있떤 상세 페이지로 redirect 
			// ->message로 "완료 여부가 변경되었습니다."
			
			//변경 실패 시 
			// -> 메인 페이지로 redirect
			// -> message
			
			//session scope 객체 얻어오기 
			HttpSession session = req.getSession();
			
			if(result> 0) {
				
				// session 객체 생성 후 message 셋팅하기 
				session.setAttribute("message", "완료여부가 변경되었습니다...");
				resp.sendRedirect("/todo/detail?todoNo="+todoNo);
				return;
												
			}
				//변경 실패시 
				// 메인페이지로 redirect
				// ->message 로 "todo 가 존재핮 ㅣ않습니다.
				
				session.setAttribute("message", "todo가 존재하지 않습니다..");
				resp.sendRedirect("/");
			
			
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
	
	
	
	
	
	}
}
