package kr.android.hellodrinking.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;

import kr.android.hellodrinking.server.db.connection.DefaultConnect;
import kr.android.hellodrinking.transmission.dto.RequestBeanPackege;

public class ClientThread extends Thread {
	private Socket mSocket = null;
	private InputStream mReader = null;
	private OutputStream mWriter = null;
	private ObjectInputStream mObjectReader = null;
	private ObjectOutputStream mObjectWriter = null;
	private Connection mConnection = null;
	
	public ClientThread(Socket socket) throws IOException, ClassNotFoundException, SQLException {
		mSocket = socket;
		mConnection = DefaultConnect.getInstance().getConnection();
		mReader = mSocket.getInputStream();
		mWriter = mSocket.getOutputStream();

		mObjectWriter = new ObjectOutputStream(mWriter);
		mObjectReader = new ObjectInputStream(mReader);
	}

	@Override
	public void run() {
		try {
			while (true) {
				RequestBeanPackege controller = (RequestBeanPackege) mObjectReader.readObject();
				(new RequestProcess(controller, mConnection, mObjectReader, mObjectWriter)).processing();
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