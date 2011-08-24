package kr.android.hellodrinking.transmission;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.net.Socket;
import java.net.UnknownHostException;

import kr.android.hellodrinking.HelloDrinkingApplication;
import kr.android.hellodrinking.transmission.dto.RequestBeanPackege;
import kr.android.hellodrinking.transmission.dto.ResponceBeanPackege;
import kr.android.hellodrinking.transmission.dto.UserBean;

public class Request implements Requestable {
	public static final String PROPERTIES_FILE_PATH = "properties/properties.txt";

	private Socket mSocket = null;
	private InputStream mReader = null;
	private OutputStream mWriter = null;
	private ObjectInputStream mObjectReader = null;
	private ObjectOutputStream mObjectWriter = null;

	private String serverip = "";
	private int port = 0;

	private boolean isUsable = false;

	public Request() {
		try {
			File file = new File(PROPERTIES_FILE_PATH);
			if (!file.exists()) {
				createPropertiesFile(file);
			} else {
				readSettingFile(file);
			}
		} catch (IOException e) {
			e.printStackTrace();
			serverip = HelloDrinkingApplication.DEFAULT_SERVER;
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

	private void readSettingFile(File file) throws FileNotFoundException, IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String token = reader.readLine();
		while (token != null) {
			if (token.startsWith("[") && token.endsWith("]")) {
				if (token.equals("[Default Server Setting]")) {
					token = readServerSetting(reader);
				}
			}
		}
	}

	private String readServerSetting(BufferedReader reader) throws IOException {
		String token;
		StreamTokenizer elementPaser;
		while ((token = reader.readLine()) != null && !token.startsWith("[")) {
			elementPaser = createTokenizer(token);

			while (elementPaser.nextToken() != StreamTokenizer.TT_EOF) {
				if (elementPaser.sval != null && elementPaser.sval.equals("ip")) {
					serverip = getValue(elementPaser);
					continue;
				} else if (elementPaser.sval != null && elementPaser.sval.equals("port")) {
					String value = getValue(elementPaser);
					if (value != null)
						port = Integer.parseInt(value);
					continue;
				}
			}
		}
		return token;
	}

	private StreamTokenizer createTokenizer(String token) {
		StreamTokenizer elementPaser;
		elementPaser = new StreamTokenizer(new StringReader(token));
		elementPaser.commentChar(';');
		elementPaser.ordinaryChar('=');

		elementPaser.ordinaryChars('1', '9');
		elementPaser.ordinaryChar('0');

		elementPaser.wordChars('1', '9');
		elementPaser.wordChars('0', '0');
		elementPaser.wordChars(':', ':');
		return elementPaser;
	}

	private String getValue(StreamTokenizer elementPaser) throws IOException {
		if (elementPaser.nextToken() == StreamTokenizer.TT_EOF)
			return null;
		if (elementPaser.ttype == '=') {
			if (elementPaser.nextToken() == StreamTokenizer.TT_EOF)
				return null;
			if (elementPaser.ttype == StreamTokenizer.TT_WORD)
				return elementPaser.sval;
		}
		return null;
	}

	private void createPropertiesFile(File file) throws IOException {
		if (!file.getParentFile().exists())
			file.getParentFile().mkdir();
		file.createNewFile();
		FileWriter fos = new FileWriter(file);
		fos.write("[Default Server Setting]\n");
		fos.write("ip=" + HelloDrinkingApplication.DEFAULT_SERVER + "\n");
		fos.write("port=" + HelloDrinkingApplication.DEFAULT_PORT + "\n");
		fos.flush();
		fos.close();
		serverip = HelloDrinkingApplication.DEFAULT_SERVER;
		port = HelloDrinkingApplication.DEFAULT_PORT;
	}

	public Request(String server, int port) {
		this.serverip = server;
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
		mSocket = new Socket(serverip, port);
		mReader = mSocket.getInputStream();
		mWriter = mSocket.getOutputStream();
		mObjectReader = new ObjectInputStream(mReader);
		mObjectWriter = new ObjectOutputStream(mWriter);
		mWriter.flush();
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
	public ResponceBeanPackege register(String id, String name, String password, String age, String sex, String phone, String job,
			String imageFilePath) {
		UserBean user = new UserBean(id, password, name, age, sex, phone, job, imageFilePath);
		return register(user);
	}

	@Override
	public ResponceBeanPackege register(UserBean user) {
		RequestBeanPackege controller = new RequestBeanPackege(RequestBeanPackege.Request.Register, user);
		ResponceBeanPackege responce = sendRequestAndGetResponce(controller);

		TEST(responce);
		return responce;
	}

	private ResponceBeanPackege sendRequestAndGetResponce(RequestBeanPackege controller) {
		try {
			mObjectWriter.writeObject(controller);
			mObjectWriter.flush();

			return (ResponceBeanPackege) mObjectReader.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ResponceBeanPackege login(String id, String password) {
		return login(new UserBean(id, password));
	}

	@Override
	public ResponceBeanPackege login(UserBean user) {
		RequestBeanPackege controller = new RequestBeanPackege(RequestBeanPackege.Request.Login, user);
		ResponceBeanPackege responce = sendRequestAndGetResponce(controller);

		TEST(responce);
		return responce;
	}

	@Override
	public ResponceBeanPackege userModify(String id, String name, String password, String age, String sex, String phone, String job,
			String imageFilePath) {
		return userModify(new UserBean(id, password, name, age, sex, phone, job, imageFilePath));
	}

	@Override
	public ResponceBeanPackege userModify(UserBean user) {
		RequestBeanPackege controller = new RequestBeanPackege(RequestBeanPackege.Request.UserModify, user);
		ResponceBeanPackege responce = sendRequestAndGetResponce(controller);

		TEST(responce);
		return responce;
	}

	private void TEST(ResponceBeanPackege responce) {
		System.out.println("is Successed? : " + responce.isSuccessed());
		if (!responce.isSuccessed())
			System.out.println("Reason : " + responce.getException().getMessage());
	}

	public boolean isUsable() {
		return isUsable;
	}
}
