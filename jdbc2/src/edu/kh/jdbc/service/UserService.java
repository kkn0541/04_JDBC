package edu.kh.jdbc.service;

//import static : 지정된 경로에 존재하는 static 구문을 모두 얻어와 
//클래스명 , 메서드명() 이 아닌 메서드명() 만 작성해도 호출 가능하게 함 
import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.dao.UserDAO;
import edu.kh.jdbc.dto.User;

public class UserService {

	// 필드
	private UserDAO dao = new UserDAO();

	/**
	 * 전달받은 아이디와 일치하는 user정보 반환 서비스
	 * 
	 * @param input (입력된 아이디)
	 * @return 아이디가 일치하는 회원정보 , 없으면 null
	 */
	public User selectId(String input) {
		// 서비스단에서는 항상
		// 커넥션 생성
		Connection conn = JDBCTemplate.getConnection();

		// DAO 메서드 호출 후 결과 반환 받기
		// 커넥션 같이 보내주기
		User user = dao.selectId(conn, input);

		// 다쓴 커넥션 닫기
		JDBCTemplate.close(conn);

		// 메서드가 User 반환 이니까 return도 user
		return user; // DB 조회 결과 반환
	}

	/**
	 * USER 등록 서비스
	 * 
	 * @param user : 입력 받은 ID , PW , NAME 이 세팅된 객체
	 * @return 삽입 성공한 결과 행의 개수
	 * @throws Exception
	 */
	public int insertUser(User user) throws Exception {

		// 1. 커넥션 생성
		// 위에 import 하면 getconnection 만 쓸수있음
		Connection conn = JDBCTemplate.getConnection();

		// 2. 데이터 가공 (할게 없으면 넘어감)

		// 3. DAO 메서드 호출(INSERT) 후
		// 결과(삽입 성공한 행 개수 , int) 반환 받기
		int result = dao.insertUser(conn, user);

		// 4. INSERT 수행 결과에 따라 트랜잭션 제어처리하기
		if (result > 0) { // INSERT 성공
			commit(conn);

		} else { // INSERT 실패
			rollback(conn);
		}

		// 5. CONNECTION 반환하기
		close(conn);

		// 6. 결과 반환
		return result;
	}

	/**
	 * User 전체 조회 서비스
	 * 
	 * @return 조회된 user가 담긴 List
	 * 
	 */
	public List<User> selectAll() throws Exception {

		// 1. 커넥션 생성
		Connection conn = getConnection();

		// 2. 데이터 가공 (없으면 넘어감)

		// 3. DAO 메서드 호출(select) 후 결과 반환(List<User>받기 
		//dao 에  conn 전달
		List<User> userList = dao.selectAll(conn);

		// 4. DML 인경우 트랜잭션 처리
		// SELECT 는 안해도 된다.

		// 5. 다쓴 connection 반환
		close(conn);
		
		// 6. 결과 반환
		return userList;
	}

	public List<User> selectName(String input) throws Exception {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		List<User> searchList =dao.selectName(conn,input);
		
		close(conn);
		
		return searchList;
	}

	public User selectUser(int input) throws Exception {

		Connection conn = getConnection();
		User user = dao.selectUser(conn,input);
		
		close(conn);
		
		
		return user;
	}

}
