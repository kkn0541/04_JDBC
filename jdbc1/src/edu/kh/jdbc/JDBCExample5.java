package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class JDBCExample5 {
	public static void main(String[] args) {

		// 아이디 , 비밀번호 , 이름을 입력 받아
		// TB_USER 테이블에 삽입(INSERT)하기

		// 1. JDBC 객체 참조 변수 선언
		Connection conn = null;

		/*
		 * java.sql.PreparedStatement (준비 된 statement ) - SQL 중간에 ? (위치홀더 ,
		 * placeholder)를 작성하여
		 * 
		 * 장점 1: sql 작성이 간다해짐 장점 2: ?에 값 대입 시 자료형에 맞는 형태의 리터럴 표기법으로 대입됨 ex)String 대입 ->
		 * '값' (자동으로 '' 추가 ex)int 대입 -> 값 장점 3: 성능 속도에서 우위를 가지고 있음 ** PreparedStament 는
		 * Statement 의 자식 **
		 * 
		 * 
		 * 
		 * 
		 */

		PreparedStatement pstmt = null;
		// resulset은 select문의 결과 -> //SELECT
		// SELECT 가 아니기 때문에 ResultSet 필요 없음

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

//			String type = "jdbc:oracle:thin:@"; // 드라이버의 종류
//
//			String host = "localhost"; // DB 서버 컴퓨터의 IP또는 도메인 주소
//			// localhost == 현재 컴퓨터
//			String port = ":1521"; // 프로그램 연결을 위한 port 번호
//
//			String dbName = ":XE"; // DBMS 이름(XE == eXpress Edition)

			// jdbc:oracle:thin:@localhost:1521:XE

			String url = "jdbc:oracle:thin:@localhost:1521:XE";

			String userName = "kh_kkn"; // 사용자 계정명

			String password = "kh1234"; // 계정 비밀번호

			// 2-3) DB연결 정보와 DriverManager 를 이용해서 connetcion 객체 생성

			conn = DriverManager.getConnection(url, userName, password);

			System.out.println(conn);

			// 3. SQL 작성
			Scanner sc = new Scanner(System.in);

			System.out.println("아이디 입력");
			String id = sc.nextLine();

			System.out.println("비밀번호 입력");
			String pw = sc.nextLine();

			System.out.println("이름 입력");
			String name = sc.nextLine();

			String sql = """
					INSERT INTO TB_USER
					VALUES(SEQ_USER_NO.NEXTVAL, ?, ?, ?, DEFAULT)
					""";

			/*
			 * AutoCommit 끄기 ! -> 왜 끄는 건가 ? 개발자가 트랜잭션을 마음대로 제어하기 위해서
			 * 
			 */

			conn.setAutoCommit(false);

			// 4. PreparedSatement 객체 생성
			// -> 객체 생성과 동시에 sql이 담겨지게됨
			// -> 미리 ? (placeholder)에 값을 받을 준비를 해야되기 때문에
			// 빵구가 나있는 구문이 타고 있는 버스
			pstmt = conn.prepareStatement(sql);

			// 5. 위치홀더에 알맞은 값 대입
			// pstmt.set자료형(sql에서 ?의 순서 ,대입할값)
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, name);
			// ->여기까지 실행하면 SQL이 작성 완료된 상태!

			// 6. SQL(INSERT) 수행 후 결과(int) 반환받기
			// excuteQuery() : select 수행 , ResultSet 반환
			// excuteUpdate() : DML 수행 결과 행 개수(int) 반환
			// -> 보통 DML 실패시 0, 성공 시 0초과 된 값이 반환

			// PreparedStatement 를 이요하여 SQL 실행 시
			// excuteQuery() / excuteUpdate() 매개변수 자리에 아무것도 없어야함
			int result = pstmt.executeUpdate();

			// 7.result 값에 따른 결과 처리 + 트랜잭션 제어처리
			if (result > 0) { // INSERT 성공 시
				System.out.println(name + "님이 추가 되었습니다.");
				conn.commit(); // commit 수행 -> DB에 INSERT 영구 반영

			} else {
				System.out.println("추가 실패");
				conn.rollback(); // 실패 시 ROLLBACK

			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				//8. 사용한 jdbc 객체 자원 반환 
				if(pstmt !=null) {
					pstmt.close();
				}
				if(conn !=null) {
					conn.close();
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
