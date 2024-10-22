package edu.kh.jdbc.dao;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.dto.User;

//DAO (Data Acess Object) 
//데이터가 저장된 곳에 접근하는 용도의 객체 
//-> DB에 접근하여 JAVA 에서 원하는 결과를 얻기위해 
// SQL을 수행하고 결과를 반환 받는 역할 
public class UserDAO {

	// 필드
	// -DB 접근 관련한 JDBC 객체 참조 변수 미리 선언
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null; // SELECT로 받아올떄

	/**
	 * 전달 받은 connection을 이용해서 DB에 접근하여 전달 받은 아이디와 일치하는 User 정보 조회하기
	 * 
	 * @param conn  :service에서 생성한 Connection 객체
	 * @param input : View 에서 입력받은 아이디
	 * @return 아이디가 일치하는 회원의 user 객체 또는 null
	 */
	public User selectId(Connection conn, String input) {
		// 결과 저장용 변수
		User user = null; // 결과 저장용 변수

		try {
			// sql 작성
			String sql = "SELECT * FROM TB_USER WHERE USER_ID=?";

			// preparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);

			// ? 위치 홀더에 알맞은 값 대입
			pstmt.setString(1, input);

			// sql 수행 후 결과 반환 받기
			rs = pstmt.executeQuery();

			// 조회 결과가 있을 경우
			// -> 중복되는 아이디가 없을 경우
			// 1행만 조회되기 때문에 while문 보다는 if문을 사용하는게 효과적

			if (rs.next()) {
				// 첫 행에 데이터가 존재한다면

				// 각 컬럼의 값 얻어오기

				int userNo = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");

				// JAVA.SQL.DATE 활용
				Date enrollDate = rs.getDate("ENROLL_DATE");

				// 조회된 컬럼값을 이용해서 user 객체 생성
				// enrollDate 는 타입이 date인데 string으로 넣어야 하니 .tostring()
				user = new User(userNo, userId, userPw, userName, enrollDate.toString());

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 사용한 jdbc 객체 자원 반환
			// 오버로딩 rs , pstmt 매개변수가 다르니까 가능
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);

			// connection 객체는 생성된 service 에서 close !!

		}

		return user; // 결과 반환(생성된 User 또는 null)
	}

	/**
	 * User 등록 DAO 메서드
	 * 
	 * @param conn : DB 연결 정보가 담겨 있는 Connection 객체
	 * @param user : 입력받은 id , pw , name이 세팅된 User 객체
	 * @return INSERT 결과 행의 개수
	 */
	public int insertUser(Connection conn, User user) throws Exception {

		// sql 수행 중 발생하는 예외를
		// catch 로 처리하지 않고 , throws 를 이용해서 호출부로 던져 처리
		// -> catch문 필요 없다

		// 1. 결과 저장용 변수 선언
		int result = 0;
		// 제대로 수행되면 0이아닌값

		try {

			// 2. sql 작성
			String sql = """
					INSERT INTO TB_USER
					VALUES(SEQ_USER_NO.NEXTVAL, ?, ?, ?, DEFAULT)

					""";
			// 3. PreparedStatement 생성
			pstmt = conn.prepareStatement(sql);

			// 4. ?(위치 홀더) 알맞은 값 대입

			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getUserPw());
			pstmt.setString(3, user.getUserName());

			// 5. sql 수행 후 결과값 반환 (executeUpdate(삽입된 행의 개수) 후 결과 반환받기
			result = pstmt.executeUpdate();

		} finally {
			// 6. 사용한 jdbc 객체 자원 반환 (close)
			// import static edu.kh.jdbc.common.JDBCTemplate.*;
			close(pstmt);
		}

		// 결과 저장용 변수에 저장된 값 반환
		return result;
	}

	/**
	 * User 전체 조회용 DAO 메서드
	 * 
	 * @param conn
	 * @return userList
	 */
	public List<User> selectAll(Connection conn) throws Exception {

		// 1. 결과 저장용 변수 선언
		// -> List 같은 컬렉션을 반환하는 경우에는
		// 변수 선언 시 객체도 같이 생성해두자 !
		List<User> userList = new ArrayList<User>();

		try {

			// 2. sql 작성

			String sql = """
					SELECT USER_NO, USER_ID , USER_PW, USER_NAME,
					TO_CHAR(ENROLL_DATE,'YYYY"년" MM"월" DD"일"')ENROLL_DATE
					FROM TB_USER
					ORDER BY USER_NO ASC
					""";

			// 3. PrepareStaement 플레이스홀더 없어도 이거 써도됨
			pstmt = conn.prepareStatement(sql);

			// 4. ? 에 알맞은 값 대입 ( 없으면 패스)

			// 5. sql(select) 수행 후
			// 결과 반환 받기(ResultSet)받기
			rs = pstmt.executeQuery();

			// 6. 조회 결과를 1행씩 접근하여 컬럼 값 얻어오기

			// 몇행이 조회될지 모른다. -> while
			// 무조건 1행이 조회된다. -> if
			while (rs.next()) {

				int userNo = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");
				// -java.sql.date 타입으로 값을 저장하지 않은 이유
				// -> TO_CHAR() 를 이용해서 문자열로 변환했기 때문 !

				// userList 에 추가
				// ->User 객체를 생성해 조회된 값을 담고
				// userList에 추가하기

				User user = new User(userNo, userId, userPw, userName, enrollDate);

				userList.add(user);

			}

		} finally {
			close(rs);
			close(pstmt);
		}

		// 조회 결과가 담긴 List 반환
		return userList;
	}

	/**
	 * 이름에 검색어가 포함되는 회원 모두 조회 DAO
	 * 
	 * @param conn
	 * @param keyword
	 * @return searchList
	 * @throws Exception
	 */
	public List<User> selectName(Connection conn, String keyword) throws Exception {
		// TODO Auto-generated method stub

		// 1. 결과 저장용 변수 선언
		List<User> searchList = new ArrayList<User>();

		// 2. SQL 작성
		try {
			String sql = """
					SELECT USER_NO, USER_ID , USER_PW, USER_NAME,
					TO_CHAR(ENROLL_DATE,'YYYY"년" MM"월" DD"일"')ENROLL_DATE FROM TB_USER
					WHERE USER_NAME LIKE '%'||?||'%'
					ORDER BY USER_NO ASC
					""";

			// 3. PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// 4. ?에 알맞은 값 대입하기
			pstmt.setString(1, keyword);

			// 5. db 수행 후 결과 반환 받기
			rs = pstmt.executeQuery();

			while (rs.next()) {

				int userNo = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");

				User user = new User(userNo, userId, userPw, userName, enrollDate);

				searchList.add(user);

			}

		} finally {
			close(rs);
			close(pstmt);
		}

		return searchList;

	}

	/**
	 * 4. userno를 입력바다 일치하는 user조회 dao
	 * 
	 * @param conn
	 * @param input
	 * @return user객체 or null
	 * @throws Exception
	 */
	public User selectUser(Connection conn, int input) throws Exception {

		// 1. 결과 저장용 변수 선언
		User user = null;

		try {
			// 2. SQL작성
			String sql = """
					SELECT USER_NO, USER_ID , USER_PW, USER_NAME,
					TO_CHAR(ENROLL_DATE,'YYYY"년" MM"월" DD"일"')ENROLL_DATE
					FROM TB_USER
					WHERE USER_NO = ?
					""";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, input);

			rs = pstmt.executeQuery();

			if (rs.next()) {

				int userNo = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");

				user = new User(userNo, userId, userPw, userName, enrollDate);

//				user.setUserNo(userNo);
//				user.setUserId(userId);
//				user.setUserPw(userPw);
//				user.setUserName(userName);
//				user.setEnrollDate(enrollDate);
			}

		} finally {

			close(rs);
			close(pstmt);

		}

		return user;
	}

	/**
	 * 5. USER_NO를 입력 받아 일치하는 User 삭제 DAO (DELETE)")
	 * 
	 * @param conn
	 * @param input
	 * @return result - 행의 개수
	 * @throws Exception
	 */
	public int deleteUser(Connection conn, int input) throws Exception {
		int result = 0;

		try {
			String sql = """
					DELETE FROM TB_USER
					WHERE USER_NO =?
					""";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, input);

			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}
		return result;
	}

	/**
	 * id pw 가 일치하는 회원의 user_no 조회 dao
	 * 
	 * @param conn
	 * @param userId
	 * @param userPw
	 * @return userNo
	 */
	public int selectUser(Connection conn, String userId, String userPw) throws Exception {
		// TODO Auto-generated method stub

		int userNo = 0;// 결과저장용 변수 선언

		try {
			String sql = """
					SELECT USER_NO
					FROM TB_USER
					WHERE USER_ID=?
					AND USER_PW =?
					""";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, userPw);

			rs = pstmt.executeQuery();

			// 조회된 1행이 있을 경우
			if (rs.next()) {
				userNo = rs.getInt("USER_NO");
			}

		} finally {
			close(rs);
			close(pstmt);
		}

		return userNo; // 조회 성공 user_no 실패시 0 반환
	}

	/**
	 * userNo가 일치하는 user의 이름 수정 dao
	 * 
	 * @param conn
	 * @param userName
	 * @param userNo
	 * @return result
	 */
	public int updateName(Connection conn, String userName, int userNo) throws Exception {

		int result = 0;

		try {
			String sql = """
					UPDATE TB_USER
					SET USER_NAME =?
					WHERE USER_NO=?
					""";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userName);
			pstmt.setInt(2, userNo);

			result = pstmt.executeUpdate();

		} finally {

			close(pstmt);
		}

		return result;
	}

	/**아이디 중복확인 DAO
	 * @param conn
	 * @param userId
	 * @return
	 */
	public int idcheck(Connection conn, String userId) throws Exception {

		int count = 0;
		
		try {
			String sql = """
					SELECT COUNT(*)  
					FROM TB_USER 
					WHERE USER_ID =?					
					""";
			
			pstmt =conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				count=rs.getInt(1); // 조회된 컬럼순서를 이용해 컬럼값 얻어오기 
			}
			
			
		} finally {
			close(rs);
			close(pstmt);
		
		}
		
		
		return count;
	}

}
