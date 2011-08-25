package kr.android.hellodrinking.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.SQLException;

import kr.android.hellodrinking.server.db.DBAccess;
import kr.android.hellodrinking.transmission.dto.PostBean;
import kr.android.hellodrinking.transmission.dto.RequestBeanPackege;
import kr.android.hellodrinking.transmission.dto.ResponceBeanPackege;
import kr.android.hellodrinking.transmission.dto.UserBean;
import kr.android.hellodrinking.transmission.exception.LoginException;
import kr.android.hellodrinking.transmission.exception.Message;

public class RequestProcess {
	private RequestBeanPackege mRequst;
	private ObjectInputStream mReader;
	private ObjectOutputStream mWriter;
	private DBAccess mDBAccess;

	public RequestProcess(RequestBeanPackege controller, Connection connection, ObjectInputStream reader, ObjectOutputStream writer)
			throws IOException {
		mRequst = controller;
		mDBAccess = new DBAccess(connection);
		mReader = reader;
		mWriter = writer;
	}

	public void processing() {
		if (mRequst.request == RequestBeanPackege.Request.Register) {
			register();
		} else if (mRequst.request == RequestBeanPackege.Request.Login) {
			login();
		} else if (mRequst.request == RequestBeanPackege.Request.UserModify) {
			useModify();
		} else if (mRequst.request == RequestBeanPackege.Request.GetUser) {
			getUser();
		} else if (mRequst.request == RequestBeanPackege.Request.Post) {
			post();
		}
	}

	private boolean getUser() {
		UserBean user = mRequst.user;
		Exception exception = mDBAccess.getUser(user.getId());

		ResponceBeanPackege responce = null;

		if (exception instanceof Message) {
			responce = new ResponceBeanPackege(true);
			responce.setObject(((Message) exception).getObject());
		} else {
			responce = new ResponceBeanPackege(exception);
		}

		return sendResponce(responce);
	}

	private boolean useModify() {
		UserBean user = mRequst.user;
		Exception exception = mDBAccess.userModify(user.getId(), user.getName(), user.getPassword(), user.getAge(), user.getSex(), user.getPhone(),
				user.getJob(), "");

		ResponceBeanPackege responce = new ResponceBeanPackege(exception);

		if (responce.isSuccessed()) {
			user.setImageFilePath(writerImageFileAndGetImageFilePath(user.getImageFilePath(), user.getBuffer()));
			mDBAccess.setImageAtUserInfo(user.getId(), user.getImageFilePath());
		}

		return sendResponce(responce);
	}

	private boolean register() {
		UserBean user = mRequst.user;
		Exception exception = mDBAccess.register(user.getId(), user.getName(), user.getPassword(), user.getAge(), user.getSex(), user.getPhone(),
				user.getJob(), "");

		ResponceBeanPackege responce = new ResponceBeanPackege(exception);

		if (responce.isSuccessed()) {
			user.setImageFilePath(writerImageFileAndGetImageFilePath(user.getImageFilePath(), user.getBuffer()));
			mDBAccess.setImageAtUserInfo(user.getId(), user.getImageFilePath());
		}

		return sendResponce(responce);
	}

	private boolean post() {
		PostBean post = mRequst.post;
		Exception exception = mDBAccess.post(post.getId(), post.getComment(), "", post.getLongitude(), post.getLatitude());

		ResponceBeanPackege responce = new ResponceBeanPackege(exception);

		if (responce.isSuccessed()) {
			post.setImageFilePath(writerImageFileAndGetImageFilePath(post.getImageFilePath(), post.getBuffer()));
			mDBAccess.setImageAtPostInfo(post.getId(), post.getImageFilePath());
		}

		return sendResponce(responce);
	}

	private String writerImageFileAndGetImageFilePath(String filepath, byte[] buffer) {
		File dir = new File(HelloDrinkingServer.DEFAULT_IMAGEFILE_DIRECTORY);
		if (!dir.exists() || !dir.isDirectory())
			dir.mkdir();

		File file = new File(filepath);
		if (file.getName().equals(""))
			return null;
		File imagefile = new File(dir, file.getName());
		FileOutputStream fileWriter = null;
		for (int temp = 1;; temp++) {
			if (imagefile.exists()) {
				String filename = file.getName();
				String prefix = "";
				String postfix = "";

				int indexOfDot = filename.lastIndexOf('.');
				if (indexOfDot < 1) {
					prefix = filename;
				} else {
					prefix = filename.substring(0, indexOfDot - 1);
					postfix = filename.substring(indexOfDot);
				}

				imagefile = new File(dir, prefix + "[" + temp + "]" + postfix);
			} else {
				break;
			}
		}

		try {
			fileWriter = new FileOutputStream(imagefile);
			fileWriter.write(buffer);
			fileWriter.flush();

			return imagefile.getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
			if (imagefile.exists())
				imagefile.delete();
			return null;
		} finally {
			try {
				fileWriter.close();
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
	}

	private boolean login() {
		boolean isSuccessed;

		UserBean user = mRequst.user;
		Exception exception = mDBAccess.getUserPassword(user.getId());
		ResponceBeanPackege responce = null;
		if (exception instanceof Message) {
			if (user.getPassword().equals(exception.getMessage())) {
				isSuccessed = true;
				responce = new ResponceBeanPackege(true);
			} else {
				isSuccessed = false;
				responce = new ResponceBeanPackege(new LoginException("ID와 Password가 일치하지 않습니다.", LoginException.State.NotMatch));
			}
		} else {
			isSuccessed = false;
			responce = new ResponceBeanPackege(exception);
		}
		mDBAccess.addLoginHistory(user.getId(), isSuccessed);

		return sendResponce(responce);
	}

	private boolean sendResponce(ResponceBeanPackege responce) {
		try {
			mWriter.writeObject(responce);
			mWriter.flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
