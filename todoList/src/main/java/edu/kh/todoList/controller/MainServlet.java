package edu.kh.todoList.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import edu.kh.todoList.dto.Todo;
import edu.kh.todoList.service.TodoListSerivce;
import edu.kh.todoList.service.TodoListServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//"/main" 요청을 매핑하여 처리하는 서블릿 
@WebServlet("/main")
public class MainServlet extends HttpServlet {

	/*
	 * 왜 "/main" 메인 페이지 요청을 처리하는 서블릿을 만들었는가
	 * 
	 * -servlet(back-end) 에서 추가한 데이터(db에서 조회한 데이터)를 메인페이지에서 사용할수 있게 하려고
	 * 
	 * 
	 * 
	 */

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			
			//service 객체 생성 
			//요청 ->controller -> service 호출-> dao호출 -> db 
			// 응답     <-view <-	        <-           <-
			
			//서비스객체 생성 (객체생성 클래스만 가능)
			TodoListSerivce service = new TodoListServiceImpl();
			
			//전체 할일 목록 + 완료된 todo 개수가 담긴 map을 
			//service에 호출해서 얻어오기 
			Map<String, Object> map = service.todolistdFullView();
			
			
			//Map에 저장된 값 풀어내기  
			//(value값이 object로 넘어왓기떄문에 데이터형식맞지않음 
			//다운캐스팅 해야함 
			List<Todo> todoList=(List<Todo>)map.get("todoList");
			int completeCount =(int)map.get("completeCount");
			
			//request scope에 객체 값 추가하기 
			
			req.setAttribute("todoList", todoList);
			req.setAttribute("completeCount", completeCount);
			
			//메인 페이지 응답을 담당하는 jsp에 요청 위임
			String path="/WEB-INF/views/main.jsp";
			
			req.getRequestDispatcher(path).forward(req, resp);
			
		
					
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
