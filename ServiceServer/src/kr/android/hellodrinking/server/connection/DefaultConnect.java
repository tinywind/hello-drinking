package kr.android.hellodrinking.server.connection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import kr.android.hellodrinking.server.HelloDrinkingServer;

public class DefaultConnect implements Connectable {
	private int db_port = 0;
	private String db_ip = "";
	private String db_user = "";
	private String db_password = "";
	private String jdbc_driver = "";
	private String jdbc_id = "";
	private String jdbc_class = "";

	private static DefaultConnect instance = new DefaultConnect();

	private DefaultConnect() {
	}

	public static DefaultConnect getInstance() {
		return instance;
	}

	@Override
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		try {
			File file = new File(HelloDrinkingServer.PROPERTIES_FILE_PATH);
			if (!file.exists()) {
				createDefaultFile(file);
				setDefaultSetting();
			} else {
				readSettingFile(file);
			}
		} catch (IOException e) {
			e.printStackTrace();
			setDefaultSetting();
		}

		Class.forName(jdbc_class);

		//DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","Project_Test","1122");
		
		return DriverManager.getConnection("jdbc:" + jdbc_driver + ":@" + db_ip + ":" + db_port + ":" + jdbc_id, db_user, db_password);
		
	}

	private void readSettingFile(File file) throws FileNotFoundException, IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String token = reader.readLine();
		while (token != null) {
			if (token.startsWith("[") && token.endsWith("]")) {
				if (token.equals("[Default DB Setting]")) {
					token = readDBSetting(reader);
				} else if (token.equals("[Default JDBC Setting]")) {
					token = readJDBCSetting(reader);
				}
			}
		}
	}

	private String readJDBCSetting(BufferedReader reader) throws IOException {
		String token;
		StreamTokenizer elementPaser;
		while ((token = reader.readLine()) != null && !token.startsWith("[")) {
			elementPaser = createTokenizer(token);

			while (elementPaser.nextToken() != StreamTokenizer.TT_EOF) {
				if (elementPaser.sval != null && elementPaser.sval.equals("driver")) {
					jdbc_driver = getValue(elementPaser);
					continue;
				} else if (elementPaser.sval != null && elementPaser.sval.equals("id")) {
					jdbc_id = getValue(elementPaser);
					continue;
				} else if (elementPaser.sval != null && elementPaser.sval.equals("class")) {
					jdbc_class = getValue(elementPaser);
					continue;
				}
			}
		}
		return token;
	}

	private String readDBSetting(BufferedReader reader) throws IOException {
		String token;
		StreamTokenizer elementPaser;
		while ((token = reader.readLine()) != null && !token.startsWith("[")) {
			elementPaser = createTokenizer(token);

			while (elementPaser.nextToken() != StreamTokenizer.TT_EOF) {
				if (elementPaser.sval != null && elementPaser.sval.equals("ip")) {
					db_ip = getValue(elementPaser);
					continue;
				} else if (elementPaser.sval != null && elementPaser.sval.equals("port")) {
					String value = getValue(elementPaser);
					if (value != null)
						db_port = Integer.parseInt(value);
					continue;
				} else if (elementPaser.sval != null && elementPaser.sval.equals("user")) {
					db_user = getValue(elementPaser);
					continue;
				} else if (elementPaser.sval != null && elementPaser.sval.equals("password")) {
					db_password = getValue(elementPaser);
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

	private void createDefaultFile(File file) throws IOException {
		if (!file.getParentFile().exists())
			file.getParentFile().mkdir();
		file.createNewFile();
		FileWriter fos = new FileWriter(file);
		fos.write("[Default DB Setting]\n");
		fos.write("ip=" + HelloDrinkingServer.DEFAULT_DB_IP + "\n");
		fos.write("port=" + HelloDrinkingServer.DEFAULT_DB_PORT + "\n");
		fos.write("user=" + HelloDrinkingServer.DEFAULT_DB_USER + "\n");
		fos.write("password=" + HelloDrinkingServer.DEFAULT_DB_PASSWORD + "\n");
		fos.flush();

		fos.write("[Default JDBC Setting]\n");
		fos.write("driver=" + HelloDrinkingServer.DEFAULT_JDBC_DRIVER + "\n");
		fos.write("id=" + HelloDrinkingServer.DEFAULT_JDBC_ID + "\n");
		fos.write("class=" + HelloDrinkingServer.DEFAULT_JDBC_CLASS + "\n");
		fos.flush();

		fos.close();
	}

	private void setDefaultSetting() {
		db_ip = HelloDrinkingServer.DEFAULT_DB_IP;
		db_port = HelloDrinkingServer.DEFAULT_DB_PORT;
		db_user = HelloDrinkingServer.DEFAULT_DB_USER;
		db_password = HelloDrinkingServer.DEFAULT_DB_PASSWORD;

		jdbc_driver = HelloDrinkingServer.DEFAULT_JDBC_DRIVER;
		jdbc_id = HelloDrinkingServer.DEFAULT_JDBC_ID;
		jdbc_class = HelloDrinkingServer.DEFAULT_JDBC_CLASS;
	}
}
