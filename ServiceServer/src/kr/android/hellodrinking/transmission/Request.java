package kr.android.hellodrinking.transmission;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import kr.android.hellodrinking.HelloDrinkingApplication;
import kr.android.hellodrinking.transmission.dto.BeanController;
import kr.android.hellodrinking.transmission.dto.UserBean;

public class Request implements Requestable {
	public static final String PROPERTIES_FILE_PATH = "../../properties/properties.txt";

	private Socket mSocket = null;
	private InputStream mReader = null;
	private OutputStream mWriter = null;

	private String server = "";
	private int port = 0;

	private boolean isUsable = false;

	public Request() {
		try {
			File file = new File(PROPERTIES_FILE_PATH);
			if (!file.exists()) {
				file.createNewFile();
				FileWriter fos = new FileWriter(file);
				fos.write("[Default Server Setting]\n");
				fos.write("ip=" + HelloDrinkingApplication.DEFAULT_SERVER);
				fos.write("port=" + HelloDrinkingApplication.DEFAULT_PORT);
				fos.flush();
				fos.close();
				server = HelloDrinkingApplication.DEFAULT_SERVER;
				port = HelloDrinkingApplication.DEFAULT_PORT;
			} else {
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				String properties = "";
				String token;
				while ((token = reader.readLine()) != null) {
					properties += token;
				}

				StringTokenizer tokenizer = new StringTokenizer(properties);
				StreamTokenizer elementPaser;
				while (tokenizer.hasMoreTokens()) {
					token = tokenizer.nextToken();
					if (token.startsWith("[") && token.endsWith("]")) {
						if (token.equals("[Default Server Setting]")) {
							while (tokenizer.hasMoreTokens() && (token = tokenizer.nextToken()).startsWith("[")) {
								elementPaser = new StreamTokenizer(new StringReader(token));
								elementPaser.commentChar(';');
								elementPaser.ordinaryChars('1', '9');
								elementPaser.ordinaryChar('0');

								while (elementPaser.nextToken() != StreamTokenizer.TT_EOF) {
									if (elementPaser.sval.equals("ip")) {
										if (elementPaser.nextToken() == StreamTokenizer.TT_EOF)
											break;
										if (elementPaser.sval.equals("=")) {
											if (elementPaser.nextToken() == StreamTokenizer.TT_EOF)
												break;
											server = elementPaser.sval;
										}
										continue;
									} else if (elementPaser.sval.equals("port")) {
										if (elementPaser.nextToken() == StreamTokenizer.TT_EOF)
											break;
										if (elementPaser.sval.equals("=")) {
											if (elementPaser.nextToken() == StreamTokenizer.TT_EOF)
												break;
											port = Integer.parseInt(elementPaser.sval);
										}
										continue;
									}
								}
							}
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			server = HelloDrinkingApplication.DEFAULT_SERVER;
			port = HelloDrinkingApplication.DEFAULT_PORT;
		}

		try {
			createSocekt();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Request(String server, int port) {
		this.server = server;
		this.port = port;

		try {
			createSocekt();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createSocekt() throws UnknownHostException, IOException {
		mSocket = new Socket(server, port);
		mReader = mSocket.getInputStream();
		mWriter = mSocket.getOutputStream();
	}

	public void close() {
		try {
			mReader.close();
			mWriter.close();
			mSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void register(String id, String name, String password, String age, String sex, String phone, String job, String imageFilePath) {
		UserBean user = new UserBean(id, password, name, age, sex, phone, job, imageFilePath);
		register(user);
	}

	@Override
	public void register(UserBean user) {
		BeanController dto = new BeanController(BeanController.Request.Register, user);
		try {
			ObjectOutputStream oos = new ObjectOutputStream(mWriter);
			oos.flush();
			oos.writeObject(dto);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 다시 받는 과정이 필요
	}

	@Override
	public void login(String id, String password) {

	}

	@Override
	public void login(UserBean bean) {

	}

	public boolean isUsable() {
		return isUsable;
	}
}
