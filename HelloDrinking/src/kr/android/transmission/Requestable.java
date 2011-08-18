package kr.android.transmission;


public interface Requestable {
	
	int register();        //회원가입
	
    DTO logIn(String id,String password);          //로그인
   
	void search();         //게시물검색     
	
	void post();	          //게시물등록    
	
	void modify_Post();    //게시물 수정

	void delete_Post();    //게시물 삭제 

	void detail_Post();    //게시물 상세 정보
	
	void add_following(); //게시물에 대한 관심자로 등록
	
	void detail_Profile();//프로필확인
	
	void modify_Profile(); //프로필 수정
	
	void send_Message();   //메세지 발신
	
	void logOut();    //로그아웃     
	
	void out_Member();     //회원탈퇴
}
