package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class jbcexam7 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");

//			
				String url = "jdbc:oracle:thin:@localhost:1521:XE";

				String userName = "kh_kkn"; // 사용자 계정명

				String password = "kh1234"; // 계정 비밀번호

				// 2-3) DB연결 정보와 DriverManager 를 이용해서 connetcion 객체 생성

				conn = DriverManager.getConnection(url, userName, password);

				System.out.println(conn);

				Scanner sc = new Scanner(System.in);
				System.out.println("성별");
				String gender = sc.next().toUpperCase();

				System.out.println("급여범위");
				System.out.println("최소");
				int min = sc.nextInt();

				System.out.println("최대");
				int max = sc.nextInt();

				System.out.println("정렬 1 OR 2");
				int sort = sc.nextInt();
			
			
			String sql ="""
					SELECT EMP_ID ,EMP_NAME ,DECODE(SUBSTR(EMP_NO,8,1),'1','M','2','F')GENDER,SALARY ,JOB_NAME, DEPT_TITLE
					FROM EMPLOYEE
					JOIN DEPARTMENT ON(DEPT_ID=DEPT_CODE)
					JOIN JOB USING(JOB_CODE)
					WHERE DECODE(SUBSTR(EMP_NO,8,1),'1','M','2','F') =?
					AND SALARY BETWEEN ? AND ? 
					ORDER  BY SALARY  
					
					
					""";
				
				if(sort==1) {
					sql+="ASC";
				}
			
				if(sort==2) {
					sql+="DESC";
				}
				
				
				conn.setAutoCommit(false);
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, gender);
				pstmt.setInt(2,min );
				pstmt.setInt(3, max);
								
				rs= pstmt.executeQuery();
				
				System.out.println("사번 | 이름   | 성별 | 급여    | 직급명 | 부서명");
				System.out.println("--------------------------------------------------------");

				while (rs.next()) {
					
					String empId = rs.getString("EMP_ID");
					String empName = rs.getString("EMP_NAME");
					String gen = rs.getString("GENDER");
					int salary = rs.getInt("SALARY");
					String jobName =rs.getString("JOB_NAME");
					String deptTitle = rs.getString("DEPT_TITLE");
					
					System.out.printf("%-4s | %3s | %-4s | %7d | %-3s  | %s \n",
							empId, empName, gen, salary, jobName, deptTitle);
					
				}
				
				
				
				
				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
