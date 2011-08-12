package transmission;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Access {
	private Socket client;
	private ObjectInputStream ois = null;
	private ObjectOutputStream oos = null;
	
	String ip="127.0.0.1";
	int port=5555;
	DTO receive_dto;
	
	public DTO request(DTO send_dto){
		//소켓을 생성하여 서버에 접속한후 스트림 연결한다.
		//dto를 서버에 송신하고 결과값을 수신한다.	
		try {
			client = new Socket(ip,port);
			oos = new ObjectOutputStream(client.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(client.getInputStream());
			
			oos.writeObject(send_dto);
			receive_dto = (DTO)ois.readObject();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	return receive_dto;
	}
}
