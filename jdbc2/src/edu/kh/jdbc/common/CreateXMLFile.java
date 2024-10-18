package edu.kh.jdbc.common;

import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Scanner;

public class CreateXMLFile {

	public static void main(String[] args) {
		//xml (eXtensible Markup Language : 단순화된 데이터 기술 형식 
		
		//xml에 저장되는 데이터 형식 key : value (Map) 형식이다. 
		//-> key , value 모두 string (문자열) 형식

		//xml 파일을 읽고 , 쓰기 위한 IO 관련 클래스 필요 
		
		// * PROPERTIES 컬렉션 객체 
		// -Map 후손 클래스 
		// - key , value 모두 string(문자열 형식)
		// -xml 파일을 읽고 쓰는데 특화된 메서드 제공 
		
		try {
			Scanner sc = new Scanner(System.in);
			
			//Properties 객체 생성 
			// * Properties = k:v 가 모두 string 으로 제한된 Map * 
			
			Properties prop = new Properties();
			
			System.out.println("생성할 파일 이름 : ");
			String fileName = sc.next();
			
			// FileOutputStream 생성 
			FileOutputStream fos = new FileOutputStream(fileName + ".xml");
			
			//Properties 객체를 이용해서 xml 파일 생성 
			//현재 클래스 가있는 프로젝트 안에 생성 
			
			prop.storeToXML(fos, fileName+".xml file!!!");
			
			System.out.println(fileName+".xml 파일 생성 완료");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
