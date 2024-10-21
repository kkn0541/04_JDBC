package edu.kh.jdbc.run;

import edu.kh.jdbc.view.UserView;

//RUN -> VIEW ->SERVICE ->DAO 역순으로 돌아감  

public class Run {
	public static void main(String[] args) {

		UserView view = new UserView();

	//	view.test();
		
		view.mainMenu();
	}

}
