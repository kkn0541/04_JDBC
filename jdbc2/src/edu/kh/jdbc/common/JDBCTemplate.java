package edu.kh.jdbc.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/* Template :양식 , 틀 ,주형 
 * -> "미리 만들어뒀다" 의미 
 * 
 * JDBCTemplate :
 * 	JDBC 관련 작업을 위한 코드를 
 *	미리 작성해서 제공하는 클래스 
 * 
 *  -Connection 생성
 *  -AutoCommit false 
 *  -commit / rollback 
 *  -각종 close() 
 * 
 * 
 * ******중요 *******
 * 어디서든 jdbctemplate 클래스를
 * 객체로 만들지 않고도 메서들 사용할수 있도록 하기 위해 
 * 모든 메서드를 public static 으로 선언  (실행될때마다 static 영역에 담아놓고 사용)
 * 
 * 
 * 
 * 
 * 
 * 
 * */
public class JDBCTemplate {

	 //필드 
	private static Connection conn = null;
	//-> static 메서드에서 사용 가능한 필드로 static 필드 선언 
	
	//메서드 
	
	//반환현이 커넥션
	
	/** 호출 시 Connection 객체를 생성해서 반환하는 메서드 +AutoCommit 끄기 
	 * @return conn 
	 */
	public static Connection getConnection() {
		
		
		return null;
	}
	
	//---------------------------------------------------
		
	/**전달받은 커넥션에서 수행한 sql을 commit 하는 메서드 
	 * @param conn
	 */
	public static void commit(Connection conn) {
		
	}
	
	/**전달받은 커넥션에서 수행한 sql을 ROLLBACK 하는 메서드 
	 * @param conn
	 */
	public static void rollback(Connection conn) {
		
	}
	//-----------------------------------------------------
	
	/**전달받은 커넥션을 close(자원 반환)하는 메서드 
	 * @param conn
	 */
	public static void close(Connection conn) {
	
	}

	
	/**전달 받은 statement + preparedstatment 둘다 close() 
	 *+ 다형성 업캐스팅 적용 
	 *  -> PreparedStatement 는 Statement 자식 
	 * @param stmt
	 */
	public static void close(Statement stmt) {
		
		
	}
	
	
	/** 전달 받은 ResultSet 을 close() 하는 메서드 
	 * @param rs
	 */
	public static void close(ResultSet rs) {
		
	}
	
	
	
	
	
	
	
}
