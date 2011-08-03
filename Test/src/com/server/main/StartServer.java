package com.server.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class StartServer {

		private static final int PORT = 5555;
		private static ServerSocket server;

		public StartServer() throws Exception{
			server = new ServerSocket(PORT);
		}
		public void start() {
			Socket client = null;
			MainThread thread;
			while (true) {
				try {
					System.out.println("서버시작");
					client = server.accept();
					thread = new MainThread(client);
					thread.start();
					System.err.println("socket accept : "+client.getInetAddress().toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
}
