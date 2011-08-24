package kr.android.hellodrinking.transmission;

import kr.android.hellodrinking.transmission.dto.ResponceBeanPackege;
import kr.android.hellodrinking.transmission.dto.UserBean;

public interface Requestable {
	public ResponceBeanPackege register(String id, String name, String password, String age, String sex, String phone, String job, String imageFilePath);
	public ResponceBeanPackege register(UserBean user);
	public ResponceBeanPackege login(String id, String password) ;
	public ResponceBeanPackege login(UserBean user);
	public ResponceBeanPackege userModify(String id, String name, String password, String age, String sex, String phone, String job, String imageFilePath);
	public ResponceBeanPackege userModify(UserBean user);
//	public boolean post(String id, String comment, String ImageFilePath);
//	public boolean modify_Post(String id, String postNum, String comment);
//	public boolean delete_Post(String id, String postNum);
//	public void detail_Post();
//	public void add_following();
//	public String detail_Profile(String id);
//	public void send_Message();
//	public boolean logOut(String id);
//	public boolean out_Member(String id, String password);
}
