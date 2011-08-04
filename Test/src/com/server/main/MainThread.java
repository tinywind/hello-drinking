package com.server.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;

import com.db.connection.DBConnection;

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

	}

	@Override
	public void run() {

		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(socket.getInputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}
		try {

			System.out.println("서버분기1");
			receive_dto = (DTO) ois.readObject();// 여기가문제
			System.out.println("서버분기2");
			option = receive_dto.getOption();
			System.out.println("서버분기3");
			Functions_Server f = new Functions_Server();
			System.out.println("서버분기4");
			conn = DBConnection.Connect();

			switch (option) {
			case 0:
				send_dto.setResult(f.register(receive_dto, conn));// 요청처리 &결과저장
				oos.writeObject(send_dto);// 결과전송
				ois.close();
				oos.close();
				System.out.println("회원가입완료");
				break;// 회원가입

			case 1:
				System.out.println("서버 로그인 분기1");
				send_dto.setResult(f.logIn(receive_dto, conn));
				System.out.println("서버 로그인 분기1");
				oos.writeObject(send_dto);// 결과전송
				ois.close();
				oos.close();
				System.out.println("로그인성공");
				break;// 로그인

			case 2:
				break;// 게시물 검색(여자,남자,거리?)
			case 3:
				break;// 게시물 등록
			case 4:
				break;// 게시물 수정
			case 5:
				break;// 게시물 삭제
			case 6:
				break;// 게시물 상세 정보
			case 7:
				break;// 게시물에 대한 관심자로 등록(하나의 게시물에 대한 복수의 관심자)
			case 8:
				break;// 프로필 확인
			case 9:
				break;// 프로필 수정
			case 10:
				break;// 메세지 발신, 수신
			case 11:
				break;// 로그아웃
			case 12:
				break;// 회원탈퇴
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
}
