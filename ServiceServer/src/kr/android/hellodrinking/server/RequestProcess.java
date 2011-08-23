package kr.android.hellodrinking.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.SQLException;

import kr.android.hellodrinking.server.db.DBAccess;
import kr.android.hellodrinking.transmission.dto.RequestBeanPackege;
import kr.android.hellodrinking.transmission.dto.ResponceBeanPackege;
import kr.android.hellodrinking.transmission.dto.UserBean;
import kr.android.hellodrinking.transmission.exception.LoginException;
import kr.android.hellodrinking.transmission.exception.Message;

public class RequestProcess {
	private RequestBeanPackege mController;
	private ObjectInputStream mReader;
	private ObjectOutputStream mWriter;
	private DBAccess mDBAccess;

	public RequestProcess(RequestBeanPackege controller, Connection connection, ObjectInputStream reader, ObjectOutputStream writer)
			throws IOException {
		mController = controller;
		mDBAccess = new DBAccess(connection);
		mReader = reader;
		mWriter = writer;
	}

	public void processing() {
		if (mController.request == RequestBeanPackege.Request.Register) {
			register();
		} else if (mController.request == RequestBeanPackege.Request.Login) {
			login();
		}
	}

	private boolean register() {
		UserBean user = mController.user;
		Exception exception = mDBAccess.register(user.getId(), user.getName(), user.getPassword(), user.getAge(), user.getSex(), user.getPhone(),
				user.getJob(), "");

		ResponceBeanPackege responce = new ResponceBeanPackege(exception);

		if (responce.isSuccessed()) {
			user.setImageFilePath(writerImageFileAndGetImageFilePath());
			mDBAccess.setImageAtUserInfo(user.getId(), user.getImageFilePath());
		}

		return sendResponce(responce);
	}

	private String writerImageFileAndGetImageFilePath() {
		File dir = new File(HelloDrinkingServer.DEFAULT_IMAGEFILE_DIRECTORY);
		if (!dir.exists() || !dir.isDirectory())
			dir.mkdir();

		File file = new File(mController.user.getImageFilePath());
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
			fileWriter.write(mController.user.getBuffer());
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

		UserBean user = mController.user;
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
