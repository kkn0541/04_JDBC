package edu.kh.todoList.service;

import java.util.Map;

import edu.kh.todoList.dto.Todo;

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

	/**할 일 상세 조회 서비스 
	 * @param todoNo
	 * @return null 또는 todo 객체 
	 * @throws Exception 
	 */
	Todo todoDetailView(int todoNo) throws Exception;

	/**완료 여부 변경 서비스 
	 * @param todoNo
	 * @return
	 */
	int todoComplete(int todoNo) throws Exception;

	/**삭제 서비스
	 * @param todoNo
	 * @return
	 * @throws Exception 
	 */
	int todoDelete(int todoNo) throws Exception ;

	
	
	/**할일 수정 서비스 
	 * @param todoNo
	 * @param title
	 * @param detail
	 * @return
	 */
	int todoUpdate(int todoNo, String title, String detail) throws Exception;



	
	
}
