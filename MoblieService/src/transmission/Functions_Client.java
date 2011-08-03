package transmission;

public class Functions_Client implements Requestable {
	DTO dto = new DTO();
	
	
	@Override
	public int register() {
		// TODO Auto-generated method stub
		
		return 0;
	}

	@Override
	public void logIn(String id, String password) {
		// TODO Auto-generated method stub
		//액세스 클래스로 dto 넘긴후 액세스클래스에서 dto전송
		dto.setId(id);
		dto.setPassword(password);
		
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
