package edu.kh.todoList.service;

import java.util.Map;

public interface TodoListSerivce {

	/**할 일 목록 반환 서비스 
	 * @return todoList+ 완료 개수 (map으로 묶어서 반환)
	 */
	Map<String, Object> todolistdFullView() throws Exception;

	/** 할 일 추가 서비스 
	 * @param title
	 * @param detail
	 * @return int 성공 시 추가 된 행의 개수 / 실패 시 0 반환
	 * @throws Exception 
	 */
	int todoAdd(String title, String detail) throws Exception;



	
	
}
