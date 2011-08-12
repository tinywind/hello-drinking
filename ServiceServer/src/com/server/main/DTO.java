package com.server.main;
import java.io.Serializable;

public class DTO implements Serializable {
	
	/**
	 * 
	 */
	
	int option;
	/*
	case 0    회원가입
	case 1    로그인
	case 2    게시물 검색
	case 3    게시물 등록
	case 4    게시물 수정
	case 5    게시물 삭제
	case 6    게시물 상세 정보
	case 7    게시물에 대한 관심자로 등록(하나의 게시물에 대한 복수의 관심자)
	case 8    프로필 확인
	case 9    프로필 수정
	case 10   메세지 발신, 수신
	case 11   로그아웃
	case 12   회원탈퇴
	*/
	boolean result;
	//서버가 클라이언트에게 보내는 결과 
	
	public String id;
	public String name;
	public String password;
	public String Image;
	public String age;
	public String sex;
	public String phoneNumber;
	public String job;
	public String log;


	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public int getOption() {
		return option;
	}
	public void setOption(int option) {
		this.option = option;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getImage() {
		return Image;
	}
	public void setImage(String image) {
		Image = image;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	
	
}
