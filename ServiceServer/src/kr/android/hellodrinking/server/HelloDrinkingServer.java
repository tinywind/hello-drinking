package kr.android.hellodrinking.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class HelloDrinkingServer {
	public static final String DEFAULT_IMAGEFILE_DIRECTORY = "Image";
	public static final String PROPERTIES_FILE_PATH = "properties/properties.txt";
	public static final int DEFAULT_DB_PORT = 1521;
	public static final String DEFAULT_DB_IP = "127.0.0.1";
	public static final String DEFAULT_DB_USER = "Project";
	public static final String DEFAULT_DB_PASSWORD = "kitri";
	public static final String DEFAULT_JDBC_DRIVER = "oracle:thin";
	public static final String DEFAULT_JDBC_ID = "xe";
	public static final String DEFAULT_JDBC_CLASS = "oracle.jdbc.driver.OracleDriver";

	private static final int PORT = 18080;
	private static ServerSocket server;

	public HelloDrinkingServer() throws Exception {
		server = new ServerSocket(PORT);
	}

	public void start() {
		System.out.println("Server Start");
		while (true) {
			try {
				Socket socket = server.accept();
				ClientThread client = new ClientThread(socket);
				client.start();
				System.err.println("socket accept : "
						+ socket.getInetAddress().toString());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
