package kr.android.hellodrinking.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;

import kr.android.hellodrinking.server.connection.DefaultConnect;
import kr.android.hellodrinking.transmission.dto.BeanController;

public class ClientThread extends Thread {
	private Socket mSocket = null;
	private InputStream mReader = null;
	private OutputStream mWriter = null;
	private Connection mConnection = null;

	public ClientThread(Socket socket) throws IOException, ClassNotFoundException, SQLException {
		mSocket = socket;
		mConnection = DefaultConnect.getInstance().getConnection();
		mReader = mSocket.getInputStream();
		mWriter = mSocket.getOutputStream();
	}

	@Override
	public void run() {
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		try {
			oos = new ObjectOutputStream(mSocket.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(mSocket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			while (true) {
				BeanController controller = (BeanController) ois.readObject();
				(new RequestProcess(controller, mConnection, ois, oos)).processing();
			}
		} catch (ClassNotFoundException e) {
			System.out.println(mSocket.getInetAddress() + " : " + e.getMessage());
		} catch (IOException e) {
			System.out.println(mSocket.getInetAddress() + " : " + e.getMessage());
		} finally {
			System.err.println("socket close : " + mSocket.getInetAddress());
			close();
		}
	}

	public void close() {
		try {
			mSocket.close();
			mReader.close();
			mWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}