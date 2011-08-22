package kr.android.hellodrinking.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import kr.android.hellodrinking.transmission.dto.BeanController;

public class RequestProcess extends Thread {
	public static final String DEFAULT_IMAGEFILE_DIRECTORY = "C:/HelloDrinking/Image/";
	private BeanController mController;
	private InputStream mReader;
	private OutputStream mWriter;

	public RequestProcess(BeanController controller, InputStream reader, OutputStream writer) throws IOException {
		mController = controller;
		mReader = reader;
		mWriter = writer;
	}

	@Override
	public void run() {
		if (mController.request == BeanController.Request.Register) {
			register();
		} else if (mController.request == BeanController.Request.Login) {
			login();
		}
	}

	private void register() {
		mController.user.setImageFilePath(writerImageFileAndGetImageFilePath());
	}

	private String writerImageFileAndGetImageFilePath() {
		File file = new File(mController.user.getImageFilePath());
		File imagefile = new File(HelloDrinkingServer.DEFAULT_IMAGEFILE_DIRECTORY + file.getName());
		FileOutputStream fileWriter = null;
		for (int temp = 1;; temp++) {
			if (imagefile.exists())
				imagefile = new File(HelloDrinkingServer.DEFAULT_IMAGEFILE_DIRECTORY + file.getName() + "[" + temp + "]");
			else
				break;
		}

		try {
			fileWriter = new FileOutputStream(imagefile);
			int length = 0;
			byte[] buf = new byte[512];
			while ((length = mReader.read(buf)) != -1) {
				fileWriter.write(buf, 0, length);
				fileWriter.flush();
			}
			return imagefile.getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
			if (imagefile.exists())
				imagefile.delete();
			return null;
		} finally {
			if (fileWriter != null)
				try {
					fileWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	private void login() {

	}
}
