package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample2 {
	public static void main(String[] args) {

		// 입력 받은 급여보다 초과해서 받는 사원의
		// 사번 , 이름 ,급여 조회

		// 1. jdbc 객체 참조용 변수 선언
		Connection conn = null;

		Statement stmt = null;

		ResultSet rs = null;
		// 2. DriverManger 객체를 이용해서 Connection 객체 생성
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2-2 ) DB연결 정보 작성
			String type = "jdbc:oracle:thin:@"; // 드라이버의 종류

			String host = "localhost"; // DB 서버 컴퓨터의 IP또는 도메인 주소
										// localhost == 현재 컴퓨터
			String port = ":1521"; // 프로그램 연결을 위한 port 번호

			String dbName = ":XE"; // DBMS 이름(XE == eXpress Edition)

			// jdbc:oracle:thin:@localhost:1521:XE

			String userName = "kh_kkn"; // 사용자 계정명

			String password = "kh1234"; // 계정 비밀번호

			// 2-3) DB연결 정보와 DriverManager 를 이용해서 connetcion 객체 생성

			conn = DriverManager.getConnection(type + host + port + dbName, userName, password);

			System.out.println(conn);

			// Connection 객체가 잘 생성되었는지 확인(객체 주소 반환)
			// == db연결 정보에 오타 없는지 확인
			// -> DB 연결 정보가 잘못되거나 객체 생성에 문제가 생기

			// 3. sql 작성

		//	String sql = "SELECT EMP_ID, EMP_NAME, SALARY FROM EMPLOYEE ORDER BY SALARY ";
			
			Scanner sc = new Scanner(System.in);
			System.out.println("급여입력");
			int input = sc.nextInt();


			String sql ="SELECT EMP_ID, EMP_NAME, SALARY FROM EMPLOYEE WHERE SALARY > " +input;
			
			
			
			// 4. statement 객체 생성
			stmt = conn.createStatement();
			// 5. statement 객체를 이용하여 sql 수행 후 결과 반환 받기
			rs = stmt.executeQuery(sql);
			// excuteQeury() :select 실행 ,resultSET 반환

			// 입력받은 급여 ->scanner 필요
			// int input =sc.nextInt() - 급여 담기

			while (rs.next()) {

				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				int salary = rs.getInt("SALARY");

				System.out.printf("사번 : %s / 이름 : %s / 급여: %d원 \n", empId, empName, salary);
				
				
//				if (input < salary) {
//					System.out.printf("사번 : %s / 이름 : %s / 급여: %d \n", empId, empName, salary);
//
//				}
			}

			// excuteUpdate() : DML 실행 , 결과 행의 개수 반환(int)

			// 6. 조회 결과가 담겨 있는 ResultSet 을
			// 커서이용해 1행씩 접근해
			// 각행에 작성된 컬럼값 얻어오기
			// -> while문 안에서 꺼낸 데이터 출력

			// 201/송중기 /600000원
			// ...

		} catch (Exception e) {
			// 최상위 예외인 Exception 을 이용해서 모든 예외를 처리
			// ->다형성 업캐스팅 적용

			System.out.println("해당 class를 찾을 수 없습니다.");
			e.printStackTrace();
		} finally {

			// 7. 사용완료된 jdbc 객체 자원 반환(close)
			// ->생성된 역순으로 close
			// try catch
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
}
