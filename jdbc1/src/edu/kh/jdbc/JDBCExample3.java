package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample3 {
	public static void main(String[] args) {

		// 입력받은 최소급여 이상
		// 입력받은 최대 급여 이하 를 받는
		// 사원의 사번 , 이름 급여를 급여 내림차순으로 조회
		// -> 이클립스 콘솔 출력

		// 최소급여 :100000
		// 최대급여 : 3000000
		// 사번 / 이름 / 급여
		// 사번 / 이름 / 급여
		// ....

		// 1. jdbc 객체 참조 변수 선언

		Connection conn = null; // 연결통로

		Statement stmt = null; // 쿼리 가지고 왓다 갓다 할 버스

		ResultSet rs = null; // 결과값 sql(select)

		try {

			// DriverManager 객체를 이용해서 Connectino 객체 생성하기

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

			Scanner sc = new Scanner(System.in);
			System.out.println("최소값");
			int min = sc.nextInt();

			System.out.println("최대값");
			int max = sc.nextInt();


//			String sql = "SELECT EMP_ID , EMP_NAME ,SALARY  FROM EMPLOYEE "
//					+ "WHERE SALARY >"+ min 
//					+ " AND SALARY <" +max;
			
			//between 사용 
//		String sql=	"SELECT EMP_ID , EMP_NAME ,SALARY "
//				+ " FROM EMPLOYEE "
//				+ "WHERE SALARY BETWEEN " +min +" AND" +max
//				+ " ORDER BY SALARY DESC" ;
			
			//""" 사이에 sql 작성  """
			
			String sql = """
					SELECT EMP_ID , EMP_NAME ,SALARY  FROM EMPLOYEE
					WHERE SALARY BETWEEN 
					"""+min+ " AND " +max
					+" ORDER BY SALARY DESC" ;
			
			// 4. 가지고 왓다갓다할 객체 생성 (statement) 
			stmt = conn.createStatement();
		
			//5.sql 수행 후 결과 반환 받기   
			//sql 실어서 db로 갓다가 조회한 결과를 다시 가지고 돌아옴
			rs=stmt.executeQuery(sql);
			// 가지고온 결과를 rs에 대입 
			//rs에 조회결과가 담겨짐 
			
			int count=0;
			
			//6.
			//반환된 결과를 통해 while 문 작성 
			//한행씩 접근해서 컬럼값 얻어오기 
			while(rs.next()) {
				count ++;
				
				String empId = rs.getString("EMP_ID"); //emp_id가 varchar타입
				String empName = rs.getString("EMP_NAME");
				int salary = rs.getInt("SALARY");
				
				System.out.printf("사번 : %s / 이름 : %s / 급여: %d원 \n",
									empId, empName, salary);
			}
			
		System.out.println("총원"+ count +"명");
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
				//7. 사용완료한 jdbc 객체 자원 반환 (close)
				//만들어진 역순으로 닫기 rs -> stmt  -> conn
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					rs.close();
				}
				if (conn != null) {
					rs.close();
				}

			} catch (Exception e) {
				// TODO: handle exception
			}
			

		}

	}
}
