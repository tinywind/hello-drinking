package kr.android.hellodrinking.transmission;

import kr.android.hellodrinking.transmission.dto.UserBean;
import kr.android.hellodrinking.transmission.exception.*;

public interface Requestable {
	public void register(String id, String name, String password, String age, String sex, String phone, String job, String ImageFilePath);
	public void register(UserBean bean);
	public void login(String id, String password) ;
	public void login(UserBean bean);
//	public boolean post(String id, String comment, String ImageFilePath);
//	public boolean modify_Post(String id, String postNum, String comment);
//	public boolean delete_Post(String id, String postNum);
//	public void detail_Post();
//	public void add_following();
//	public String detail_Profile(String id);
//	public boolean modify_Profile(String id, String name, String password, String age, String sex, String phone, String job, String ImageFilePath);
//	public void send_Message();
//	public boolean logOut(String id);
//	public boolean out_Member(String id, String password);
}
