package kr.android.hellodrinking.transmission;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import kr.android.hellodrinking.HelloDrinkingApplication;
import kr.android.hellodrinking.transmission.dto.PostBean;
import kr.android.hellodrinking.transmission.dto.RequestBeanPackege;
import kr.android.hellodrinking.transmission.dto.ResponceBeanPackege;
import kr.android.hellodrinking.transmission.dto.UserBean;

public class Request implements Requestable {
	private Socket mSocket = null;
	private InputStream mReader = null;
	private OutputStream mWriter = null;
	private ObjectInputStream mObjectReader = null;
	private ObjectOutputStream mObjectWriter = null;

	private String serverip = "";
	private int port = 0;

	public Request() {
		serverip = HelloDrinkingApplication.mServerIp;
		port = HelloDrinkingApplication.mServerPort;

		try {
			createSocekt();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Request(String server, int port) {
		this.serverip = server;
		this.port = port;

		try {
			createSocekt();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createSocekt() throws UnknownHostException, IOException {
		mSocket = new Socket(serverip, port);
		mReader = mSocket.getInputStream();
		mWriter = mSocket.getOutputStream();
		mObjectReader = new ObjectInputStream(mReader);
		mObjectWriter = new ObjectOutputStream(mWriter);
		mWriter.flush();
	}

	public void close() {
		try {
			if (mReader != null)
				mReader.close();
			if (mWriter != null)
				mWriter.close();
			if (mSocket != null)
				mSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ResponceBeanPackege register(String id, String name, String password, String age, String sex, String phone, String job,
			String imageFilePath) throws OptionalDataException, IOException {
		UserBean user = new UserBean();
		user.setId(id);
		user.setName(name);
		user.setPassword(password);
		user.setAge(age);
		user.setSex(sex);
		user.setPhone(phone);
		user.setJob(job);
		user.setImageFilePath(imageFilePath);
		return register(user);
	}

	@Override
	public ResponceBeanPackege register(UserBean user) throws OptionalDataException, IOException {
		RequestBeanPackege request = new RequestBeanPackege(RequestBeanPackege.Request.Register, user);
		ResponceBeanPackege responce = sendRequestAndGetResponce(request);

		TEST(responce);
		return responce;
	}

	private ResponceBeanPackege sendRequestAndGetResponce(RequestBeanPackege request) throws OptionalDataException, IOException {
		try {
			mObjectWriter.writeObject(request);
			mObjectWriter.flush();

			ResponceBeanPackege responce = (ResponceBeanPackege) mObjectReader.readObject();

			return responce;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ResponceBeanPackege login(String id, String password) throws OptionalDataException, IOException {
		return login(new UserBean(id, password));
	}

	@Override
	public ResponceBeanPackege login(UserBean user) throws OptionalDataException, IOException {
		RequestBeanPackege request = new RequestBeanPackege(RequestBeanPackege.Request.Login, user);
		ResponceBeanPackege responce = sendRequestAndGetResponce(request);

		TEST(responce);
		return responce;
	}

	@Override
	public ResponceBeanPackege modifyUser(String id, String name, String password, String age, String sex, String phone, String job,
			String imageFilePath) throws OptionalDataException, IOException {
		UserBean user = new UserBean();
		user.setId(id);
		user.setName(name);
		user.setPassword(password);
		user.setAge(age);
		user.setSex(sex);
		user.setPhone(phone);
		user.setJob(job);
		user.setImageFilePath(imageFilePath);
		return modifyUser(user);
	}

	@Override
	public ResponceBeanPackege modifyUser(UserBean user) throws OptionalDataException, IOException {
		RequestBeanPackege request = new RequestBeanPackege(RequestBeanPackege.Request.UserModify, user);
		ResponceBeanPackege responce = sendRequestAndGetResponce(request);

		TEST(responce);
		return responce;
	}

	private void TEST(ResponceBeanPackege responce) {
		System.out.println("is Successed? : " + responce.isSuccessed());
		if (!responce.isSuccessed())
			System.out.println("Reason : " + responce.getException().getMessage());
	}

	@Override
	public ResponceBeanPackege getUser(String id) throws OptionalDataException, IOException {
		return getUser(new UserBean(id));
	}

	@Override
	public ResponceBeanPackege getUser(UserBean user) throws OptionalDataException, IOException {
		RequestBeanPackege request = new RequestBeanPackege(RequestBeanPackege.Request.GetUser, user);
		ResponceBeanPackege responce = sendRequestAndGetResponce(request);

		TEST(responce);
		return responce;
	}

	@Override
	public ResponceBeanPackege post(String id, String comment, String imageFilePath, double longitude, double latitude) throws OptionalDataException,
			IOException {
		return post(new PostBean(id, comment, imageFilePath, longitude, latitude));
	}

	@Override
	public ResponceBeanPackege post(PostBean post) throws OptionalDataException, IOException {
		RequestBeanPackege request = new RequestBeanPackege(RequestBeanPackege.Request.Post, post);
		ResponceBeanPackege responce = sendRequestAndGetResponce(request);

		TEST(responce);
		return responce;
	}

	@Override
	public ResponceBeanPackege getPosts(double longitude, double latitude, int distance) throws OptionalDataException, IOException {
		RequestBeanPackege request = new RequestBeanPackege(RequestBeanPackege.Request.GetPosts, longitude, latitude, distance);
		ResponceBeanPackege responce = sendRequestAndGetResponce(request);

		TEST(responce);
		return responce;
	}
}
