package kr.android.transmission;



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

public class Access {
	private Socket client;
	private ObjectInputStream ois = null;
	private ObjectOutputStream oos = null;
	
	public static final String SERVER_IP = "192.168.17.139";
	public static final  int PORT = 5557;
	DTO mDTO;
			
	public DTO request(DTO send_dto){
		//������ ���Ͽ� ������ �������� ��Ʈ�� �����Ѵ�.
		//dto�� ������ �۽��ϰ� ����� �����Ѵ�.
	
		mDTO=new DTO();
		try {
			
			client = new Socket(SERVER_IP,PORT);
			oos = new ObjectOutputStream(client.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(client.getInputStream());
			oos.writeObject(send_dto);
		
			oos.flush();
			mDTO = (DTO)ois.readObject();
//			
//			if(send_dto.getOption()==8){
//				InputStream is;
//						
//				try {
//					is = client.getInputStream();
//									
//					//�ȵ���̵� ��θ�
//					File file=new File("c:/clientimage/"+mDTO.getId()+".png");
//					FileOutputStream fos=new FileOutputStream(file);
//					
//					int i=0;
//					int index =0; 
//					while((i=is.read())!=-1){
//					  fos.write(i); fos.flush();
//					  index++;
//			     		}
//					System.out.println("index:"+index);
//					is.close(); fos.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}				
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
		
	return mDTO;
	}
	//�̹��� ��� �Լ��� ���ǹ� ����
	//���Ͻ�Ʈ���� ���� ������ Ŭ���̾�Ʈ�� �̹��� �ۼ���
	//name ����� �����̸�
	public DTO request(DTO send_dto,String path){
		send_dto.setImage(true);
        send_dto.setImageName(new File(path).getName());
        
		if(request(send_dto)!=null){
		 File file = new File("c:/ff.png");//�̹������� 
		 		 	
				try {
									  
				    BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
				    System.out.println("���ϸ� : "+file);
				    bw.write(file+"\n"); bw.flush();
				   
				    DataInputStream dis=new DataInputStream(new FileInputStream(file));
				    DataOutputStream dos=new DataOutputStream(client.getOutputStream());
				    				    				  
				    int b=0;
				    while( (b=dis.read()) != -1 ){
				     dos.writeByte(b); dos.flush();
				    }			  
				    dis.close(); dos.close(); client.close();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	     }	
		 return mDTO;
	 }
}