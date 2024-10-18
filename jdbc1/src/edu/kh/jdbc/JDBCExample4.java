package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import javax.print.attribute.standard.JobName;

public class JDBCExample4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// 부서명을 입력받아
		// 해당 부서에 근무하는 사원의
		// 사번 , 이름 부서명 , 직금명을
		// 직급코드 오름차순으로 조회

		// 부서명 입력 : 총무부
		// 200 선통일 총무부 대표
		// ....

		// hint - sql에서 문자열은 양쪽에 ' 홑따옴표' 필요
		// ex ) 총무부 입력 ==> '총무부'

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
			System.out.println("부서명 입력");
			String input = sc.next();
			
		String sql ="""
				SELECT EMP_ID ,EMP_NAME,NVL(DEPT_TITLE,'없음') DEPT_TITLE ,JOB_NAME
				FROM EMPLOYEE  
				JOIN JOB USING(JOB_CODE)
				JOIN DEPARTMENT  ON(DEPT_ID=DEPT_CODE)
				WHERE DEPT_TITLE ='"""	+input+ "'ORDER BY JOB_CODE";
				
					
		stmt= conn.createStatement();
		rs=stmt.executeQuery(sql);
			
		boolean flag = true;
		//조회 결과가 있다면 false 없으면 true 
		
		while(rs.next()) {
			flag=false;
			
			String empId = rs.getString("EMP_ID"); //emp_id가 varchar타입
			String empName = rs.getString("EMP_NAME");
			String deptTitle = rs.getString("DEPT_TITLE");
			String jobName = rs.getString("JOB_NAME");
			
			
			System.out.printf("%s / %s / %s /%s \n",empId , empName,deptTitle,jobName);
			
		}
		
			
			if(flag) { // flag == true == while문이 수행된적 없음 
				System.out.println("일치하는 부서가 없습니다.");
			}
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		try {
			if(rs !=null)rs.close();
			if(stmt != null)stmt.close();
			if(conn !=null) stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
