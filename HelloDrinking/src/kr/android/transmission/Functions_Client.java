package kr.android.transmission;

public class Functions_Client implements Requestable {
	DTO send_dto; 
	DTO receive_dto; 
	Access access; 
	//(ID, Name, Password, Image, age, sex, phone_number, job, log)

	@Override
	public boolean register(String id, String name, String password, String age, String sex, String phoneNumber,String job) {
		access = new Access();
		
		send_dto= new DTO();
		receive_dto =new DTO();
		
		send_dto.setOption(0);
		send_dto.setId(id);
		send_dto.setName(name);
		send_dto.setPassword(password);
		send_dto.setAge(age);
		send_dto.setSex(sex);
		send_dto.setPhoneNumber(phoneNumber);
		send_dto.setJob(job);
		String a="a";//�̹������ ���ߴ�.
		receive_dto=access.request(send_dto);
		
		return receive_dto.isResult();
	}

	@Override
	public boolean logIn(String id, String password) {//�α��� ����
		//�׼��� Ŭ������ dto �ѱ��� �׼���Ŭ�������� dto���
		access = new Access();
		send_dto =new DTO();
		receive_dto = new DTO();
		
		send_dto.setOption(1);
		send_dto.setId(id);
		
		send_dto.setPassword(password);
		
		receive_dto=access.request(send_dto);
		return receive_dto.isResult();
		
		
	}

	@Override
	public void search() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean post(String id, String comment) {
		access = new Access();
		send_dto=new DTO();
		receive_dto= new DTO();
		
		send_dto.setOption(3);
		send_dto.setId(id);
		send_dto.setComment(comment);
		String path="a";
		receive_dto=access.request(send_dto,path);
		
		return receive_dto.isResult();
		
	}

	@Override
	public boolean modify_Post(String id,String postNum,String comment) {
		// TODO Auto-generated method stub
		access = new Access();
		send_dto=new DTO();
		receive_dto= new DTO();
		
		send_dto.setOption(4);
		send_dto.setId(id);
		send_dto.setPostNum(postNum);
		send_dto.setComment(comment);
		
		String path="a";
		if (path=="a")//�̹����� ���� ���ο� ��� �ٸ� �Լ� ȣ�� 
			receive_dto=access.request(send_dto,path);
		else
			receive_dto=access.request(send_dto);
		
		return receive_dto.isResult();
		
	}

	@Override
	public boolean delete_Post(String id,String postNum) {
		// TODO Auto-generated method stub
		
		access = new Access();
		send_dto=new DTO();
		receive_dto= new DTO();
		
		send_dto.setOption(5);
		send_dto.setId(id);
		send_dto.setPostNum(postNum);
		
		receive_dto=access.request(send_dto);
		return receive_dto.isResult();
	}

	@Override
	public void detail_Post() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add_following() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String detail_Profile(String id) {
		// TODO Auto-generated method stub
		access= new Access();
		send_dto=new DTO();
		
		send_dto.setOption(8);
		send_dto.setImage(true);
		send_dto.setId(id);
		
		receive_dto=access.request(send_dto);
		
		return receive_dto.getName();		
	}

	@Override
	public boolean modify_Profile(String id, String name, String password, String age, String sex, String phoneNumber,String job) {
		// TODO Auto-generated method stub
       access = new Access();
		
		send_dto= new DTO();
		receive_dto =new DTO();
		
		send_dto.setOption(9);
		send_dto.setId(id);
		send_dto.setName(name);
		send_dto.setPassword(password);
		send_dto.setAge(age);
		send_dto.setSex(sex);
		send_dto.setPhoneNumber(phoneNumber);
		send_dto.setJob(job);
		
		String path="a";//�̹������ ���ߴ�.(�ʿ��ϴٸ� ���ڰ����� �޾Ƶ���)
		if (path=="a")//�̹����� ���� ���ο� ��� �ٸ� �Լ� ȣ�� 
			receive_dto=access.request(send_dto,path);
		else
			receive_dto=access.request(send_dto);
		return receive_dto.isResult();	
	}

	@Override
	public void send_Message() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean logOut(String id) {
		// TODO Auto-generated method stub
		access = new Access();
		send_dto =new DTO();
		receive_dto = new DTO();
		
		send_dto.setOption(11);
		send_dto.setId(id);
		
		receive_dto=access.request(send_dto);
		
		return receive_dto.isResult();		
	}

	@Override
	public boolean out_Member(String id, String password) {
		// TODO Auto-generated method stub
		
		access = new Access();
		send_dto =new DTO();
		receive_dto = new DTO();
		
		send_dto.setOption(12);
		send_dto.setId(id);
		send_dto.setPassword(password);
		
		receive_dto=access.request(send_dto);
		return receive_dto.isResult();		
	}
}
