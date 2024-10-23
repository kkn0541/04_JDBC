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


@WebServlet("/todo/add")
public class TodoAddServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		//할 일 추가 
		try {
			
			//1. TodoListServiceimpl 객체 생성 
			TodoListSerivce service = new TodoListServiceImpl();
			
			//2. 요청 시 전달받은 파라미터 얻어오기
			//								(name속성값)
			String title = req.getParameter("title");
			String detail = req.getParameter("detail");
			
			//3. 서비스 호출 후 결과 반환 받기 
			int result = service.todoAdd(title, detail);
			
			//4. 성공 / 실패 메세지 세팅하기 
			String message =null;
			
			if(result>0) {
				message="추가성공";
			}else {
				message="추가실패";
			}
			
			//5. 기존 req를 사용할수 없기 떄문에 
			// session 을 이용해서 message 세팅 
			HttpSession session = req.getSession();
			session.setAttribute("message", message);
			
			
			//6. 메인페이지로 redirect (재요청) (req resp 소멸됨)
			resp.sendRedirect("/");
			// ->@webServlet("/")가 작성된 servlet을 재요청  "/" == index.jsp ->/main
			
			//redirect는 무조건 GET 방식 
			
		
		
		} catch (Exception e) {

		
		}
		
		
		
		
		
	}
	
	
	
}
