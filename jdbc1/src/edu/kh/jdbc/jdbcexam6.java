package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class jdbcexam6 {

	public static void main(String[] args) {
		
		Connection conn = null;
		
		PreparedStatement pstmt = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
//				String type = "jdbc:oracle:thin:@"; // 드라이버의 종류
			//
//				String host = "localhost"; // DB 서버 컴퓨터의 IP또는 도메인 주소
//				// localhost == 현재 컴퓨터
//				String port = ":1521"; // 프로그램 연결을 위한 port 번호
			//
//				String dbName = ":XE"; // DBMS 이름(XE == eXpress Edition)
			
			// jdbc:oracle:thin:@localhost:1521:XE
			
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			
			String userName = "kh_kkn"; // 사용자 계정명
			
			String password = "kh1234"; // 계정 비밀번호
			
			conn = DriverManager.getConnection(url, userName, password);
			
			System.out.println(conn);
			
			
			Scanner sc = new Scanner(System.in);
			
			System.out.println("아이디 입력");
			String id = sc.nextLine();
			
			System.out.println("비밀번호 입력");
			String pw = sc.nextLine();
			
			System.out.println("이름입력");
			String name =sc.next();
			
			String sql ="""
					INSERT INTO TB_USER
					VALUES(SEQ_USER_NO.NEXTVAL, ?,?,?, DEFAULT)
					""";
			
			
			
			
			conn.setAutoCommit(false);
			
			//빵꾸난 SQL 버스 
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, name);
			
			int result = pstmt.executeUpdate();
			
			if(result >0) {
				System.out.println(name +"님이 추갇 ㅚ었습니다.");
			}else {
				System.out.println("추가 실패");
				conn.rollback();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
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
