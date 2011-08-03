package com.server.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Functions {
	
	
	public int register(DTO receive_dto,Connection conn){
		int result=1;
		PreparedStatement pstmt=null;
		
		try {
		pstmt = conn.prepareStatement("insert into userinfo values(?,?,?,?,?,?,?,?,?)");
		pstmt.setString(1, receive_dto.getId());
		pstmt.setString(2, receive_dto.getName());
		pstmt.setString(3, receive_dto.getPassword());
		pstmt.setString(4, receive_dto.getImage());
		pstmt.setString(5, receive_dto.getAge());
		pstmt.setString(6, receive_dto.getSex());
		pstmt.setString(7, receive_dto.getPhoneNumber());
		pstmt.setString(8, receive_dto.getJob());
		pstmt.setString(9, receive_dto.getLog());
				
		pstmt.executeUpdate();
		conn.commit();
		} catch (SQLException e) {
			result =0;
			e.printStackTrace();
		}	
		return result;
	}//회원가입
	
	public void logIn(){
		
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
	public void logOut(){
		
	}//로그아웃
	public void out_Member(){
		
	}//회원탈퇴
}
