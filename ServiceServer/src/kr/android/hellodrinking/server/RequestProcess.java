package kr.android.hellodrinking.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.Connection;

import kr.android.hellodrinking.transmission.dto.BeanController;

public class RequestProcess {
	private BeanController mController;
	private ObjectInputStream mReader;
	private ObjectOutputStream mWriter;
	private Connection mConnection;

	public RequestProcess(BeanController controller, Connection connection, ObjectInputStream reader, ObjectOutputStream writer) throws IOException {
		mController = controller;
		mConnection = connection;
		mReader = reader;
		mWriter = writer;
	}

	public void processing() {
		if (mController.request == BeanController.Request.Register) {
			register();
		} else if (mController.request == BeanController.Request.Login) {
			login();
		}

		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(mWriter);
			oos.writeObject(mController);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean register() {
		mController.user.setImageFilePath(writerImageFileAndGetImageFilePath());
		// DB에 저장.

		// 결과 되돌리기
		// TEST
		try {
			mWriter.writeObject(mController);
			mWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
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
				if(indexOfDot < 0){
					prefix = filename;
				}
				
				imagefile = new File(dir, file.getName() + "[" + temp + "]");
			} else{
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

	private void login() {

	}
}
