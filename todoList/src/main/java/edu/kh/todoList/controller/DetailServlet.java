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
import jakarta.servlet.http.HttpSession;

//할일 상세 조회 요청 처리 servlet 
@WebServlet("/todo/detail")
public class DetailServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			//요청시 전달받은 파라미터 얻어오기 string 형식이기 떄문에 int형으로 형변환 
			int todoNo = Integer.parseInt(req.getParameter("todoNo"));
			
			//service 객체 생성 
			TodoListSerivce service = new TodoListServiceImpl();
			
			Todo todo =service.todoDetailView(todoNo); 
			//TODO_NO 컬럼값이 todoNo와 같은 todo가 없으면 null 반환 
			// 있으면 알맞은 todo 객체 반환 
			
			//todo가 존재하지 않을 경우 
			//->메인 페이지(/) redirect 후 
			// "할일이 존재하지 않ㅅ습니다
			//alert 출력 
			//System.out.println(req.getSession());
			
			if(todo== null) {
				
				// session 객체 생성 후 message 셋팅하기 
				HttpSession session = req.getSession();
				session.setAttribute("message", "할일이 존재하지 않습니다.");
				
				resp.sendRedirect("/");
				return;
												
			}
			
			//todo가 존재하는 경우 
			//detail.jsp 로 forward해서 응답 
			req.setAttribute("todo", todo);
			
			//JSP 파일 경로 (webapp폴더 기준작성)
			String path ="/WEB-INF/views/detail.jsp";
			
			//요청 발송자를 이용해서 요청 위임  ( 실제 위임코드 .forward(req, resp);
			req.getRequestDispatcher(path).forward(req, resp);
					
					
			
						
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
