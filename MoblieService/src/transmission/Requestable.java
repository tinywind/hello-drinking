package transmission;


public interface Requestable {
	
	public int register();        //회원가입
	
    public void logIn();          //로그인
   
	public void search();         //게시물검색     
	
	public void post();	          //게시물등록    
	
	public void modify_Post();    //게시물 수정

	public void delete_Post();    //게시물 삭제 

	public void detail_Post();    //게시물 상세 정보
	
	public void add_following(); //게시물에 대한 관심자로 등록
	
	public void detail_Profile();//프로필확인
	
	public void modify_Profile(); //프로필 수정
	
	public void send_Message();   //메세지 발신
	
	public void logOut();    //로그아웃     
	
	public void out_Member();     //회원탈퇴
}
