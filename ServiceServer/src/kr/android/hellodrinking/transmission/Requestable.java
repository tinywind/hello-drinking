package kr.android.hellodrinking.transmission;

import java.io.IOException;
import java.io.OptionalDataException;

import kr.android.hellodrinking.transmission.dto.PostBean;
import kr.android.hellodrinking.transmission.dto.ResponceBeanPackege;
import kr.android.hellodrinking.transmission.dto.UserBean;

public interface Requestable {
	public ResponceBeanPackege register(String id, String name, String password, String age, String sex, String phone, String job, String imageFilePath) throws OptionalDataException, IOException;
	public ResponceBeanPackege register(UserBean user) throws OptionalDataException, IOException;
	public ResponceBeanPackege login(String id, String password) throws OptionalDataException, IOException ;
	public ResponceBeanPackege login(UserBean user) throws OptionalDataException, IOException;
	public ResponceBeanPackege modifyUser(String id, String name, String password, String age, String sex, String phone, String job, String imageFilePath) throws OptionalDataException, IOException;
	public ResponceBeanPackege modifyUser(UserBean user) throws OptionalDataException, IOException;
	public ResponceBeanPackege getUser(String id) throws OptionalDataException, IOException;
	public ResponceBeanPackege getUser(UserBean user) throws OptionalDataException, IOException;
	public ResponceBeanPackege post(String id, String comment, String imageFilePath, double longitude, double latitude) throws OptionalDataException, IOException;
	public ResponceBeanPackege post(PostBean post) throws OptionalDataException, IOException;
	public ResponceBeanPackege getPosts(String id);
	public ResponceBeanPackege getPosts(int distance);
	public ResponceBeanPackege getPosts(PostBean post);
//	public boolean modify_Post(String id, String postNum, String comment);
//	public boolean delete_Post(String id, String postNum);
//	public void detail_Post();
//	public void add_following();
//	public String detail_Profile(String id);
//	public void send_Message();
//	public boolean logOut(String id);
//	public boolean out_Member(String id, String password);
}
