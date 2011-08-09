package com.server.main;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class StartServer {

		private static final int PORT = 5557;
		private static ServerSocket server;

		public StartServer() throws Exception{
			server = new ServerSocket(PORT);
		}
		public void start() {
			Socket client = null;
			MainThread thread;
			System.out.println("서버시작");
			while (true) {
				try {
					client = server.accept();
					System.out.println("클라이언트 접속");
					thread = new MainThread(client);
					thread.start();
					System.err.println("socket accept : "+client.getInetAddress().toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
}
