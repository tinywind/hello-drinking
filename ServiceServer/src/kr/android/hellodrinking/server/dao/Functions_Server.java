package kr.android.hellodrinking.server.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.android.hellodrinking.transmission.DTO;

public class Functions_Server {
	//ȸ����
	public boolean register(DTO receive_dto,Connection conn){
		PreparedStatement pstmt=null;
		PreparedStatement pstmt2=null;
		try {
		pstmt = conn.prepareStatement("insert into userinfo values(?,?,?,?,?,?,?)");
		pstmt.setString(1, receive_dto.getId());
		pstmt.setString(2, receive_dto.getName());
		pstmt.setString(3, receive_dto.getPassword());
		pstmt.setString(4, receive_dto.getAge());
		pstmt.setString(5, receive_dto.getSex());
		pstmt.setString(6, receive_dto.getPhoneNumber());
		pstmt.setString(7, receive_dto.getJob());
		pstmt.executeUpdate();
		conn.commit();
		
		if(receive_dto.isImage){//���� ��ο� "�������̵�_�����̸�"���� �̹��� ����
		pstmt2=conn.prepareStatement("insert into userimage values(UI_seq.nextval,?,?)");
		String imageName="c:/image/"+receive_dto.getId();
		pstmt2.setString(1,imageName);
		pstmt2.setString(2,receive_dto.getId());
		pstmt2.executeUpdate();
		}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}	
		return true;
	}
	//�α���
	public boolean logIn(DTO receive_dto, Connection conn){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			pstmt=conn.prepareStatement("select id,password from userinfo where id=? AND password =?");
			pstmt.setString(1,receive_dto.getId());
			pstmt.setString(2,receive_dto.getPassword());
			
			rs= pstmt.executeQuery();
			while(rs.next()){
				if(rs.getString(1).equals(receive_dto.getId())
						&& rs.getString(2).equals(receive_dto.getPassword())){
					return true;
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return false;	
		
	}
	//�Խù��˻�
	public void search(){
		
		
	}
	
	
	//�Խù����
	public boolean post(DTO receive_dto,Connection conn){
		PreparedStatement pstmt=null;
		PreparedStatement pstmt2=null;
		
		try {
			pstmt = conn.prepareStatement("insert into postinfo values(post_seq.nextval,?,?)");
			pstmt.setString(1, receive_dto.getId());
			pstmt.setString(2, receive_dto.getComment());
			pstmt.executeUpdate();
			
			if(receive_dto.isImage){//���� ��ο� "�������̵�_�����̸�"���� �̹��� ����
				pstmt2=conn.prepareStatement("insert into postimage values(PI_seq.nextval,?,?,post_seq.currval)");
				String imageName="c:/image/"+receive_dto.getId()+"_"+receive_dto.getImageName();
				pstmt2.setString(1,imageName);
				pstmt2.setString(2,receive_dto.getId());
				pstmt2.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
			}	
	
		return true;
	}
	//�Խù� ����
	public boolean modify_Post(DTO receive_dto,Connection conn){
	   PreparedStatement pstmt=null;
	   PreparedStatement pstmt2=null;
		try {
			pstmt = conn.prepareStatement("UPDATE postINFO" +
					"SET commnets=? where id= ?");
			pstmt.setString(1, receive_dto.getComment());
			pstmt.setString(2, receive_dto.getId());
			pstmt.executeUpdate();
			
			if(receive_dto.isImage){ //���� ��ο� "�������̵�_�����̸�"���� �̹��� ����
				pstmt2=conn.prepareStatement("update postimage set imagename=? where postnum=?");
				String imageName="c:/image/"+receive_dto.getId()+"_"+receive_dto.getImageName();
				pstmt2.setString(1,imageName);
				pstmt2.setString(2,receive_dto.getPostNum());
				pstmt2.executeUpdate();
			}
			} catch (SQLException e) {
				e.printStackTrace();
			return false;
			}	
		return true;
	}
	//�Խù� ����
	public boolean delete_Post(DTO receive_dto,Connection conn){
		PreparedStatement pstmt=null;
		try {
			pstmt = conn.prepareStatement("delete * from postinfo where id =?" +
					"and postnum=?");
			pstmt.setString(1, receive_dto.getId());
			pstmt.setString(1, receive_dto.getPostNum());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	//�Խù� �� ����
	public void detail_Post(){
		
	}
	//�Խù��� ���� ����ڷ� ���
	public void add_following(){
		
	}
	//������Ȯ��
	public DTO detail_Profile(DTO receive_dto,Connection conn){
	   DTO userDTO = new DTO();
	   PreparedStatement pstmt=null;
	   PreparedStatement pstmt2=null;
	   ResultSet rs=null;
	   ResultSet rs2=null;
	   
		try {//ȸ������
			pstmt=conn.prepareStatement("select id,name,age,sex,phonenumber,job from userinfo where id=? ");
			pstmt.setString(1,receive_dto.getId());
						
			rs= pstmt.executeQuery();
			while(rs.next()){
				userDTO.setId(rs.getString(1));
				userDTO.setName((rs.getString(2)));
				userDTO.setAge(rs.getString(3));
				userDTO.setSex(rs.getString(4));
				userDTO.setPhoneNumber(rs.getString(5));
				userDTO.setJob(rs.getString(6));
				}
			
		if(receive_dto.isImage){ 
			pstmt2=conn.prepareStatement("select Imagename from userimage where id=?");
			pstmt2.setString(1,receive_dto.getId());
			rs2=pstmt2.executeQuery();
		
			while(rs2.next()){//ȸ�� �̹����н��� �̸� ����			
				userDTO.setImageName(rs2.getString(1));
			}
		}
		}
		 catch (SQLException e) {
				e.printStackTrace();
				}
		
		return userDTO;
    }
	//������ ����
	public boolean modify_Profile(DTO receive_dto,Connection conn){
		PreparedStatement pstmt=null;
		PreparedStatement pstmt2=null;
		try {
			pstmt = conn.prepareStatement("UPDATE USERINFO" +
					"SET NAME=?," +
					"SET PASSWORD=?" +
					"SET AGE=?," +
					"SET SEX=?," +
					"set phoneNumber=?," +
					"set job=? where id= ?"	);
			pstmt.setString(1, receive_dto.getName());
			pstmt.setString(2, receive_dto.getPassword());
			pstmt.setString(3, receive_dto.getAge());
			pstmt.setString(4, receive_dto.getSex());
			pstmt.setString(5, receive_dto.getPhoneNumber());
			pstmt.setString(6, receive_dto.getJob());
			pstmt.setString(7, receive_dto.getId());
			pstmt.executeUpdate();
			
			if(receive_dto.isImage){ //���� ��ο� "�������̵�_�����̸�"���� �̹��� ����
				pstmt2=conn.prepareStatement("update postimage set imagename=? where postnum=?");
				String imageName="c:/image/"+receive_dto.getId()+"_"+receive_dto.getImageName();
				pstmt2.setString(1,imageName);
				pstmt2.setString(2,receive_dto.getPostNum());
				pstmt2.executeUpdate();
			}
		} catch (SQLException e) {
				e.printStackTrace();
			return false;
			}	
		return true;	
	}
	
	//�޼��� �߽�
	public void send_Message(){
		
		
	}
	
	//�α׾ƿ�
	public boolean logOut(DTO receive_dto, Connection conn){
		PreparedStatement pstmt=null;
		try {
			pstmt = conn.prepareStatement("insert into userinfo(log) values(?)");
			pstmt.setString(1, receive_dto.getLog());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//ȸ��Ż��
	public boolean out_Member(DTO receive_dto,Connection conn){
		PreparedStatement pstmt=null;
		try {
			conn.commit();
			pstmt = conn.prepareStatement("delete from userinfo where id =? and password=?");
			pstmt.setString(1, receive_dto.getId());
			pstmt.setString(2, receive_dto.getPassword());
			pstmt.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
