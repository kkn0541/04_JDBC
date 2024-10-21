package edu.kh.jdbc.view;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.dto.User;
import edu.kh.jdbc.service.UserService;

public class UserView {
	// 필드
	private Scanner sc = new Scanner(System.in);
	private UserService service = new UserService();

	/**
	 * JDBCTemplte 사용 테스트
	 */
	public void test() {

		// 입력된 id와 일치하는 user 정보 조회
		System.out.print("ID입력 : ");
		String input = sc.nextLine();

		// 서비스 호출 후 결과 반환 받아오기
		User user = service.selectId(input);

		// 결과출력
		System.out.println(user);
	}

	/**
	 * User 관리 프로그램 메인 메뉴
	 */
	public void mainMenu() {

		int input = 0;

		do {
			try {

				System.out.println("\n===== User 관리 프로그램 =====\n");
				System.out.println("1. User 등록(INSERT)");
				System.out.println("2. User 전체 조회(SELECT)");
				System.out.println("3. User 중 이름에 검색어가 포함된 회원 조회 (SELECT)");
				System.out.println("4. USER_NO를 입력 받아 일치하는 User 조회(SELECT)");
				System.out.println("5. USER_NO를 입력 받아 일치하는 User 삭제(DELETE)");
				System.out.println("6. ID, PW가 일치하는 회원이 있을 경우 이름 수정(UPDATE)");
				System.out.println("7. User 등록(아이디 중복 검사)");
				System.out.println("8. 여러 User 등록하기");
				System.out.println("0. 프로그램 종료");

				System.out.print("메뉴 선택 : ");
				input = sc.nextInt();
				sc.nextLine(); // 버퍼에 남은 개행문자 제거

				switch (input) {
				case 1:
					insertUser();
					break;
				case 2:
					selectAll();
					break;
				case 3:
					selectName();
					break;
				case 4:
					selectUser();
					break;
				case 5:
					/* deleteUser(); */ break;
				case 6:
					/* updateName(); */ break;
				case 7:
					/* insertUser2(); */ break;

				case 8:
					/* multiInsertUser(); */ break;

				case 0:
					System.out.println("\n[프로그램 종료]\n");
					break;
				default:
					System.out.println("\n[메뉴 번호만 입력하세요]\n");
				}

				System.out.println("\n-------------------------------------\n");

			} catch (InputMismatchException e) {
				// Scanner를 이용한 입력 시 자료형이 잘못된 경우
				System.out.println("\n***잘못 입력 하셨습니다***\n");

				input = -1; // 잘못 입력해서 while문 멈추는걸 방지
				sc.nextLine(); // 입력 버퍼에 남아있는 잘못된 문자 제거

			} catch (Exception e) {
				// 발생되는 예외를 모두 해당 catch 구문으로 모아서 처리
				e.printStackTrace();
			}

		} while (input != 0);

	} // mainMenu() 종료

	/**
	 * user 등록하기
	 * 
	 * @throws Exception
	 */
	public void insertUser() throws Exception {
		System.out.println("\n===== 1. user 등록======= ");
		System.out.println("id ");
		String userId = sc.next();

		System.out.println("pw ");
		String userPw = sc.next();

		System.out.println("name ");
		String userName = sc.next();

		// 입력 받은 값 3개를 한번에 묶어서 전달할수 있도록
		// User DTO 객체를 생성한 후 필드에 값을 세팅
		User user = new User();

		// setter 이용
		user.setUserId(userId);
		user.setUserPw(userPw);
		user.setUserName(userName);

		// 서비스 호출(INSERT) 후(int,삽입된 행의 개수) 결과 반환 받기
		int result = service.insertUser(user);

		// 반환된결과에 따라 출력할 내용을 선택
		if (result > 0) {
			System.out.println("\n" + userId + "사용자가 등록되었습니다. \n");
		} else {
			System.out.println("\n **등록 실패 \n ");
		}

	}

	/**
	 * 2. User 전체 조회 (select)
	 */
	public void selectAll() throws Exception {
		System.out.println("\n===2.User 전체조회 (select) ==== \n");

		// 서비스 호출(SELECT) 후 결과(List<User>) 반환 받기
		List<User> userList = service.selectAll();

		// 조회 결과가 없을 경우
		if (userList.isEmpty()) {
			System.out.println("\n *** 조회 결과가 없습니다. ***\n");
			return;
		}

		// 조회 결과가 있을 경우 userList에 있는 user객체 출력
		for (User user : userList) {
			System.out.println(user);
			// tostring 호출
		}

	}

	public void selectName() throws Exception {
		System.out.println("\n ====이름 검색==== \n");

		System.out.print("이름입력 : ");
		String input = sc.nextLine();

		List<User> searchList = service.selectName(input);

		if (searchList.isEmpty()) {
			System.out.println("\n *** 검색 결과가 없습니다. ***\n");
			return;
		}
		for (User user : searchList) {
			System.out.println(user);
			// tostring 호출
		}

	}

	public void selectUser() throws Exception{
		System.out.println("\n ====user_no 검색==== \n");
		System.out.println("숫자 입력");
		
		int input = sc.nextInt();
		User user = service.selectUser(input);
		
		
		if (user==null) {
			System.out.println("\n *** 검색 결과가 없습니다. ***\n");
			return;
		}else {
			System.out.println(user);
		}
		
		
	}

}