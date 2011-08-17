
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;



public class MainThread extends Thread {
	int option;
	Socket socket = null;
	private ObjectInputStream ois = null;
	private ObjectOutputStream oos = null;
	Connection conn = null;
	DTO receive_dto;
	DTO send_dto;

	public MainThread(Socket socket) {
		this.socket = socket;
		send_dto = new DTO();
	}

	@Override
	public void run() {
		
		try {

			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(socket.getInputStream());
		   			
			receive_dto = (DTO) ois.readObject();
				
			option = receive_dto.getOption();
			Functions_Server f = new Functions_Server();
			
			conn = DBConnection.Connect();
			
		switch (option) {
			case 0://회원가입			
				send_dto.setResult(f.register(receive_dto, conn));// 요청처리 &결과저장
				oos.writeObject(send_dto);// 결과전송
			
				if(receive_dto.isImage()){//이미지 받음
					receive_Image();
				}
				ois.close();oos.close();
				break;

			case 1:// 로그인
				send_dto.setResult(f.logIn(receive_dto, conn));
				oos.writeObject(send_dto);// 결과전송
				ois.close();oos.close();
				break;
			
			case 2:
				break;// 게시물 검색
			
			case 3:// 게시물 등록(이미지 처리)
				send_dto.setResult(f.post(receive_dto,conn));
				oos.writeObject(send_dto);
				if(receive_dto.isImage()){
					receive_Image();					
				}
				ois.close();oos.close();
				break;			
			case 4:// 게시물 수정(이미지처리)
				send_dto.setResult(f.modify_Post(receive_dto,conn));
				oos.writeObject(send_dto);
				if(receive_dto.isImage()){
					receive_Image();					
				}
				ois.close();oos.close();
				break;
			case 5:// 게시물 삭제
				send_dto.setResult(f.delete_Post(receive_dto,conn));
				oos.writeObject(send_dto);
				ois.close();oos.close();				
				break;
			case 6:// 게시물 상세 정보
			
				
				break;
			case 7:// 게시물에 대한 관심자로 등록(하나의 게시물에 대한 복수의 관심자)
			
				
				break;
			case 8:// 프로필 확인
				 
				 send_dto=(f.detail_Profile(receive_dto,conn));
				 String imageName =send_dto.getImageName();
				 oos.writeObject(send_dto);
				 
				 if(receive_dto.isImage()){
				 send_Image(imageName);
								 }
				 ois.close();oos.close();
				break;
				
			case 9:// 프로필 수정(이미지 처리)
				send_dto.setResult(f.modify_Post(receive_dto,conn));
				oos.writeObject(send_dto);
				if(receive_dto.isImage()){
					receive_Image();					
				}
				ois.close();oos.close();
				break;
			
			case 10:// 메세지 발신, 수신
				break;
			
			case 11: // 로그아웃
				send_dto.setResult(f.logOut(receive_dto,conn));
				oos.writeObject(send_dto);// 결과전송
				ois.close();oos.close();
				break;
			case 12:// 회원탈퇴
				send_dto.setResult(f.out_Member(receive_dto,conn));
				oos.writeObject(send_dto);
				ois.close();oos.close();				
				break;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void receive_Image(){
		InputStream is;
		BufferedReader br;
		try {
			is = socket.getInputStream();
			br=new BufferedReader(new InputStreamReader(is));
			
			String fileName=br.readLine();
			
			File file=new File("c:/image/"+receive_dto.getId()+".png");
			FileOutputStream fos=new FileOutputStream(file);
			    		
			int i=0;
			while((i=is.read())!=-1){
			  fos.write((char)i);
	     		}
			br.close();	is.close(); fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	public void send_Image(String name){
		File file = new File(name+".png");
				 	
		try {							  
		    BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		    System.out.println("파일명 : "+file);
		    bw.write(file+"\n"); bw.flush();
		   
		    FileInputStream dis=(new FileInputStream(file));
		    DataOutputStream dos=new DataOutputStream(socket.getOutputStream());
		    				    				  
		    int b=0;
		    int index =0; 
		    
		    while( (b=dis.read()) != -1 ){
		     dos.writeByte(b);
			    dos.flush();
		     index++;
		    }		
		    System.out.println("index:"+index);
		    dis.close(); dos.close(); 
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}