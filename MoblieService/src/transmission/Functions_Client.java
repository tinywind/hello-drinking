package transmission;

public class Functions_Client implements Requestable {
	DTO send_dto = new DTO();
	DTO receive_dto = new DTO();
	Access access = new Access();
	
	@Override
	public int register() {
		// TODO Auto-generated method stub
		
		return 0;
	}

	@Override
	public DTO logIn(String id, String password) {
		// TODO Auto-generated method stub
		//액세스 클래스로 dto 넘긴후 액세스클래스에서 dto전송
		send_dto.setId(id);
		send_dto.setPassword(password);
		receive_dto=access.request(send_dto);
	
		return receive_dto;
	}

	@Override
	public void search() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void post() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modify_Post() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete_Post() {
		// TODO Auto-generated method stub
		
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
	public void detail_Profile() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modify_Profile() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void send_Message() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void logOut() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void out_Member() {
		// TODO Auto-generated method stub
		
	}
	     
}
