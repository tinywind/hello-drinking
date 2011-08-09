package com.server.main;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Functions_Server {
	
	
	public boolean register(DTO receive_dto,Connection conn){
		PreparedStatement pstmt=null;
	
		try {
		pstmt = conn.prepareStatement("insert into userinfo(id,name,password,age,sex,phonenumber,job) values(?,?,?,?,?,?,?)");
		pstmt.setString(1, receive_dto.getId());
		pstmt.setString(2, receive_dto.getName());
		pstmt.setString(3, receive_dto.getPassword());
		pstmt.setString(4, receive_dto.getAge());
		pstmt.setString(5, receive_dto.getSex());
		pstmt.setString(6, receive_dto.getPhoneNumber());
		pstmt.setString(7, receive_dto.getJob());
					
		pstmt.executeUpdate();
		conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}	
		return true;
	}//회원가입
	
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
		
	}//로그인
	public void search(){
		
	}//게시물검색
	public void post(){
		
	}//게시물등록
	public void modify_Post(){
		
	}//게시물 수정
	public void delete_Post(){
		
	}//게시물 삭제
	public void detail_Post(){
		
	}//게시물 상세 정보
	public void add_following(){
		
	}//게시물에 대한 관심자로 등록 
	public void detail_Profile(){
		
	}//프로필확인
	public void modify_Profile(){
		
	}//프로필 수정
	public void send_Message(){
		
	}//메세지 발신
	public boolean logOut(DTO receive_dto, Connection conn){
		PreparedStatement pstmt=null;
		
		try {
			pstmt = conn.prepareStatement("insert into userinfo(log) values(?)");
			pstmt.setString(1, receive_dto.getLog());

			pstmt.executeUpdate();
			conn.commit();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
		
	}//로그아웃
	public void out_Member(){
		
	}//회원탈퇴
}
