package edu.kh.todoList.controller;

import java.io.IOException;

import edu.kh.todoList.dto.Todo;
import edu.kh.todoList.service.TodoListSerivce;
import edu.kh.todoList.service.TodoListServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/todo/update")
public class UpdateServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			int todoNo = Integer.parseInt(req.getParameter("todoNo"));

			// 수정 화면에는 기존의 제목 , 상세내용이
			// input , textarea 에 채워져있는 상태여야 한다.
			// -> 수정 전 제목 / 내용 조회 == 상세조회 서비스 재호출
			TodoListSerivce service = new TodoListServiceImpl();
			Todo todo = service.todoDetailView(todoNo);

			if(todo==null) {
				//메인 페이지로 redirect
				resp.sendRedirect("/");
				return;
			
			
			}
			
			
			
			
			req.setAttribute("todo", todo);

			// 요청 발송자를 통해 forward
			String path = "/WEB-INF/views/update.jsp";
			req.getRequestDispatcher(path).forward(req, resp);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * 요청 주소가 같을때 데이터 전달 방식이 다르면 (GET/POST) 하나의 서블릿에서 각각의 메서드(doGet() / doPost()) 를
	 * 만들어 처리할수있다.
	 * 
	 * 
	 * 
	 * 할일 제목 / 내용 수정 post 요청
	 * 
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			// 전달 받은 파라미터 얻어오기 (제목 , 상세내용 , todo번호 )
			
			// update.jsp 의 name ="여기값" 
			String title = req.getParameter("title");
			String detail = req.getParameter("detail");
			int todoNo = Integer.parseInt(req.getParameter("todoNo")); //번호 
			
			
			TodoListSerivce service = new TodoListServiceImpl();
			int result = service.todoUpdate(todoNo,title,detail);
					
			//수정 성공 시 
			//상세 조회 페이지로 redirect 
			//"수정되엇습니다." message 출력 
			
			//수정 실패 시 
			//수정 화면으로 redirect 후 
			//"수정 실패" message 출력 
			
			String url = null;
			String message = null;
			
			if(result>0) { // 성공
				url="/todo/detail?todoNo="+todoNo;
				message ="수정 되었습니다.";
				
			}else { // 실패
				url="/todo/update?todoNo="+todoNo;
				message ="수정 실패";
			}
			
			//req 안에 메서드 뭐있는지
			//session 객체에 속성 추가 
			req.getSession().setAttribute("message", message);
			
			
			
			//redirect get 방식 요청 
			resp.sendRedirect(url);
			
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

}
