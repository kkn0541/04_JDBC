//할일 상제 조회 페이지에서 쿼리 스트링 값 얻어오기 
//url 에서 얻어오면 된다 (쿼리스트링 부분 ?todoNo=4)

//location.search :쿼리 스트링만 얻어오기 
//URLSearchParams : 쿼리 스트링을 객체 형태로 다룰수 있는 객체 

const todoNo= new URLSearchParams(location.search).get("todoNo"); //할일 번호 
//?todoNo=4
// -> todoNo=4 

//console.log(todoNo);

//목록으로 버튼 눌었을때 동작하는거 
const goToList = document.querySelector("#goToList");
//목록으로 버튼 요소 자체 

//목록으로 버튼이 클릭된 경우 
goToList.addEventListener("click",() => {
	 // "/"메인페이지 요청 (GET 방식)
	 location.href="/";
});


//완료 여부 변경 버튼 클릭 시 


const completeBtn = document.querySelector("#completeBtn");
completeBtn.addEventListener("click", () =>{
	//현재 보고 있는 Todo의 완료 여부 
	// o(true) <-> x(false) 변경 요청하기 (GET 요청)
	location.href="/todo/complete?todoNo="+todoNo;
	
});



//수정 버튼 클릭 시 
const updateBtn = document.querySelector("#updateBtn");
updateBtn.addEventListener("click",() =>{
	//수정 할수 있는 화면을 요청 (GET 요청)
	location.href = "/todo/update?todoNo="+todoNo;
	
	
});



//삭제 버튼 클릭시 

const deleteBtn = document.querySelector("#deleteBtn");
deleteBtn.addEventListener("click", () =>{
	
	//.1 정말 삭제 할 것인지 confirm()을 이용해서 확인 
	// confirm() 확인 클릭 시 true , 취소 클릭 시 false 반환 
	
	//취소 클릭 시 
	if(!confirm("정말 삭제 하시겠습니까")) return;
	
	//확인 클릭 시 
	// /todo/delete?todoNo=4 Get 방식 요청 보내기 
	location.href ="/todo/delete?todoNo="+todoNo; 
		
	//삭제 성공시 : "할일이 삭제 되었습니다." alert 창 띄우기 
	// 삭제 실패 시 : todo가 존재하지 않습니다. alert창 띄귀
	// 성공 / 실패 메인페이지로 redirect
	
	
});

/*
const deleteBtn1 = document.querySelector("#deleteBtn");
deleteBtn.addEventListener("click", () => {

  // 1. 정말 삭제할 것인지 confirm() 을 이용해서 확인
  // confirm()은 확인 클릭 시 true, 취소 클릭 시 false 반환

  // 취소 클릭 시
  if( !confirm("정말 삭제 하시겠습니까?") ) return;

  // 확인 클릭 시
  // /todo/delete?todoNo=4 GET 방식 요청 보내기
  location.href = "/todo/delete?todoNo=" + todoNo;

  // Controller 이름 : DeleteServlet
  // 삭제 성공 시 : "할 일이 삭제 되었습니다" alert창 띄우기
  // 삭제 실패 시 : "todo가 존재하지 않습니다" alert창 띄우기
  // 성공/실패 모두 메인페이지로 redirect
});
*/

