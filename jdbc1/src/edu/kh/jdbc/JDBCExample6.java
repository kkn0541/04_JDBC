package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class JDBCExample6 {

	public static void main(String[] args) {

		// 아이디 , 비밀번호 , 이름을 입력받아
		// 아이디 , 비밀번호가 일치하는 사용자 (TB_USER)의
		// 이름을 수정(UPDATE)

		Connection conn = null;

		PreparedStatement pstmt = null;
		

		try {
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			
			String userName = "kh_kkn"; // 사용자 계정명
			
			String password = "kh1234"; // 계정 비밀번호
			
			// 2-3) DB연결 정보와 DriverManager 를 이용해서 connetcion 객체 생성
			
			conn = DriverManager.getConnection(url,userName,password);
			
			System.out.println(conn);
			
			Scanner sc = new Scanner(System.in);
			
			System.out.println("아이디");
			String id = sc.nextLine();
			

			System.out.println("비밀번호");
			String pw = sc.nextLine();
			
			System.out.println("이름");
			String name = sc.nextLine();
			
			
			String sql = """
			UPDATE TB_USER SET USER_NAME= ?
			WHERE USER_ID =? AND USER_PW=?
			""";
			
			
			
			conn.setAutoCommit(false);
			
			pstmt = conn.prepareStatement(sql);
			
			//sql 문 ? ? ? 각각 들어갈 순서 생각 
			pstmt.setString(1, name);
			pstmt.setString(2, id);
			pstmt.setString(3, pw);
			// sql 세팅 끝 -> 실행결과 반환 받기 
			int result = pstmt.executeUpdate();
			
			//성공 시 " 수정 성공 " + commit 
			
			//실패 시 "아이디 또는 비밀번호 불일치 +Rollback 

			
			if( result >0) {
				System.out.println(name +" 님 이름 수정");
				conn.commit();
			}else {
				System.out.println("수정실패");
				conn.rollback();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt !=null) {
					pstmt.close();
				}
				if(conn !=null) {
					pstmt.close();
				
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
