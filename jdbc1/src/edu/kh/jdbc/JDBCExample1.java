package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//ctrl + f11 실행
public class JDBCExample1 {

	public static void main(String[] args) {

		/*
		 * JDBC (JAVA DATABASE CONNECTIVITY )
		 * 
		 * 
		 * -JAVA 에서 DB 에 연결 할수 있게 해주는
		 * 
		 * JAVA API(JAVA에서 제공하는 코드)
		 * 
		 * ->java.sql 패키지에 존재함
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */

		// java 코드를 이용해
		// EMPLOYEE 테이블에서
		// 사번 , 이름 , 부서코드 , 직급코드 , 급여 , 입사일 조회 후
		// 이클립스 콘솔에 출력

		/* 1.JDBC 객체 참조용 변수 선언 */

		// java.sql.Connection
		// 특정 DBMS와 연결하기 위한 정보를 저장한 객체
		// == DBeaver에서 사용하는 DB 연결과 같은 역할의 객체
		// DB 서버주소(localhost, 포트번호 , db이름 ,계정명 , 비밀번호 )
		Connection conn = null;

		// java.sql.Statement
		// -1 ) sql 을 java 에서 DB로 전달
		// -2) DB에서 SQL 수행 결과를 반환 받아옴 (DB -> JAVA)
		Statement stmt = null;

		// java.sql.ResultSet
		// SELECT 조회 결과를 저장하는 객체
		ResultSet rs = null;

		try {
			/* 2.DriverManeger 객체를 이용해서 Connection 객체 생성하기 */

			// java.sql.DriverManger
			// -DB연결 정보와 JDBC 드라이버를 이용해서
			// 원하는 DB와 연결 할수 있는 CONNECTION 객체를 생성하는 객체

			// 2-1 ) Oracle jdbc driver 객체를 메모리에 로드(적재)하기
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//

			// Class.forName("패키지명 + 클래스명");
			// -해당 클래스를 읽어 메모리에 적재
			// ->JVM이 프로그램 동작에 사용할 객체를 생성하는 구문

			// oracle.jdbc.driver.OracleDriver"
			// -Oracle DBMS 연결시 필요한 코드가 담여빙ㅆ는 클래스
			// Oracle 에서 만들어서 준 클래스

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

			//Connection 객체가 잘 생성되었는지 확인(객체 주소 반환)
			//== db연결 정보에 오타 없는지 확인
			// -> DB 연결 정보가 잘못되거나 객체 생성에 문제가 생기면 SQLException 발생 
			
			System.out.println(conn);
			
			/*3. sql 작성*/
			// !!주의 사항 !!
			// ->jdbc 코드에서 sql 작성 시 
			// 세미콜론(;)을 작성하면 안된다.!!.!.
			// -> sql 명령어가 올바르게 종료되지 않았습니다  예외발생 
			
			//디비버에서 작성하고 가지고오기 
			// 
			String sql ="SELECT "
					+ "EMP_ID, EMP_NAME,DEPT_CODE,JOB_CODE,SALARY,HIRE_DATE "
					+ "FROM EMPLOYEE";
			
			/*4. Statement 객체 생성 */
			//createStaement 는 반환형 statement 라서 위에서 생성한 stmt에 담기
			stmt=conn.createStatement();
			//연결된 db에 sql을 전달하고 결과를 반환 받을 
			//statement 객체를 생성 
			
			/*5. statement 객체를 이용해서 sql 수행 후 결과 반환 받기 */
			// 1) ResultSet statement.excuteQuery(sql);  -> sql 실어서 보내고 결과값도 받아오는 역할
			// 	->sql이 select문일때 결과로 java.sql.ResultSet 반환 
			//select-> excuteQuery(sql)
			
			
			// 2) int statement.excuteUpdate(sql);
			// ->DML (insert, update , delete) 실행 메서드 
			//결과로 int 형 반환 (삽입 , 수정 , 삭제된 행의 개수)
			//DML -> excuteUpdate(sql)
			
			rs=stmt.executeQuery(sql);
			
			/*6. 조회결과가 담겨 있는 resultset을 
			 * 커서(Cursor)를 이용해 
			 * 1행 씩 접근해 각행에 작성된 걸럼 값 얻어오기 
			 * 
			 * 
			 * */			
			
			//rs.next() : 커서를 다음 행으로 이동 시킨 후 
			// 이동된 행에 값이 있으면 true 없으면 false 반환 
			// 맨처음 호출시 1행부터 시작 
			
			
			while(rs.next()) {
				//200	선동일	D9	J1	8000000	1990-02-06 00:00:00.000
				
			//	rs.get(자료형 or 순서))
			// - 현재 행에서 지정된 컬럼의 값을 얻어와 반환 
			// -> 지정된 자료형 형태로 값이 반환됨
			// (자료형을 잘못 지정하면 예외 발생) 
				
			// [java]           [db]
			// String           char, varchar2
			// int,long         number (정수만 저장된 컬럼 )
			// float, double    number(정수 + 실수)
			// java.sql.Date    DATE 
			
			String empId=rs.getString("EMP_ID");
			String empName=rs.getString("EMP_NAME");
			String deptCode = rs.getString("DEPT_CODE");
			String jobCode = rs.getString("JOB_CODE");
			int salary = rs.getInt("SALARY");
			Date hireDate =rs.getDate("HIRE_DATE");
			
			
			System.out.printf(
					"사번 :%s /이름 : %s /부서코드 : %s/ 직급코드 %s / 급여 : %d /입사일 :%s  \n", 
					empId,empName,deptCode,jobCode,salary,hireDate.toString()
					);
			}
			
			
		} catch (ClassNotFoundException e) {

			System.out.println("해당 class를 찾을 수 없습니다.");
			e.printStackTrace();

		} catch (SQLException e) {
			// SQLExeption : DB 연결과 관련된 모든 예외의 최상위 부모
			e.printStackTrace();

		}finally {
				/* 7.사용 완료된 jdbc 객체 자원 반환(close)
				* ->수행하지 않으면 DB와 연결된 CONNECTION이 그대로 남아있어서
				* 	다른 클라이언트가 추가적으로 연결되지 못하는
				* 	문제가 발생할 수 잇다. 
				* */
			//역순으로 닫기 
			// rs -> stmt -> conn 순으로 close
					try {
						//만들어진 역순으로 close 수행하는 것을 권장
						if(rs !=null) {	rs.close();	}
						if(stmt !=null) {	stmt.close();	}
						if(conn !=null) {	conn.close();	}
					
						//if문은 NullpointerException 방지용 구문 
						
					
					} catch (Exception e) {
						e.printStackTrace();
					}
		
		}

	}

}
