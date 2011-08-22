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
		mReader = socket.getInputStream();
		mWriter = socket.getOutputStream();
		mWriter.flush();	
		mConnection = DefaultConnect.getInstance().getConnection();
	}

	@Override
	public void run() {
		try {
			ObjectInputStream ois = new ObjectInputStream(mReader);
			ObjectOutputStream oos = new ObjectOutputStream(mWriter);
			
			while(true){
				BeanController controller = (BeanController) ois.readObject();
				(new RequestProcess(controller, mReader, mWriter)).run();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}		
	}
	
	public void close(){
		try {
			mSocket.close();
			mReader.close();
			mWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}