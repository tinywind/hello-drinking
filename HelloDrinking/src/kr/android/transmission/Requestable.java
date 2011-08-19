package kr.android.transmission;


public interface Requestable {
	
	boolean register(String id, String name, String password, String age, String sex, String phone_Number,String job);        //ȸ����
	
    boolean logIn(String id,String password);          //�α���
   
	void search();         //�Խù��˻�     
	
	boolean post(String id, String commnet);	          //�Խù����    
	
	boolean modify_Post(String id,String postNum,String comment);    //�Խù� ����

	boolean delete_Post(String id,String postNum);    //�Խù� ���� 

	void detail_Post();    //�Խù� �� ����
	
	void add_following(); //�Խù��� ���� ����ڷ� ���
	
	String detail_Profile(String id);//������Ȯ��
	
	boolean modify_Profile(String id, String name, String password, String age, String sex, String phone_Number,String job); //������ ����
	
	void send_Message();   //�޼��� �߽�
	
	boolean logOut(String id);    //�α׾ƿ�     
	
	boolean out_Member(String id,String password);     //ȸ��Ż��
}
