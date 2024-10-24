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

@WebServlet("/todo/delete")
public class DeleteServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			int todoNo=Integer.parseInt(req.getParameter("todoNo"));
									
			TodoListSerivce service = new TodoListServiceImpl();

			int result = service.todoDelete(todoNo);

			
			
			
			//result값 받고나서 할일 
			HttpSession session = req.getSession();
			
			if(result> 0) {
				
				// session 객체 생성 후 message 셋팅하기 
				session.setAttribute("message", "삭제완료...");
				resp.sendRedirect("/");
				return;
												
			}else {
				//삭제 실패시 
				// 메인페이지로 redirect
				// ->message 로 "todo 가 존재핮 ㅣ않습니다.
				
				session.setAttribute("message", "todo가 존재하지 않습니다..");
				resp.sendRedirect("/");
			
			
			}
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
