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
import java.util.StringTokenizer;

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
		return DriverManager.getConnection("jdbc:"+jdbc_driver+":@"+db_ip+":"+db_port+":"+jdbc_id, db_user, db_password);
	}

	private void readSettingFile(File file) throws FileNotFoundException, IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String properties = "";
		String token;
		while ((token = reader.readLine()) != null) {
			properties += token;
		}

		StringTokenizer tokenizer = new StringTokenizer(properties);
		while (tokenizer.hasMoreTokens()) {
			token = tokenizer.nextToken();
			if (token.startsWith("[") && token.endsWith("]")) {
				if (token.equals("[Default DB Setting]")) {
					readDBSetting(tokenizer);
				}
			} else if (token.startsWith("[") && token.endsWith("]")) {
				if (token.equals("[Default JDBC Setting]")) {
					readJDBCSetting(tokenizer);
				}
			}
		}
	}

	private void readJDBCSetting(StringTokenizer tokenizer) throws IOException {
		String token;
		StreamTokenizer elementPaser;
		while (tokenizer.hasMoreTokens() && (token = tokenizer.nextToken()).startsWith("[")) {
			elementPaser = new StreamTokenizer(new StringReader(token));
			elementPaser.commentChar(';');
			elementPaser.ordinaryChars('1', '9');
			elementPaser.ordinaryChar('0');

			while (elementPaser.nextToken() != StreamTokenizer.TT_EOF) {
				if (elementPaser.sval.equals("driver")) {
					if (elementPaser.nextToken() == StreamTokenizer.TT_EOF)
						break;
					if (elementPaser.sval.equals("=")) {
						if (elementPaser.nextToken() == StreamTokenizer.TT_EOF)
							break;
						jdbc_driver = elementPaser.sval;
					}
					continue;
				} else if (elementPaser.sval.equals("id")) {
					if (elementPaser.nextToken() == StreamTokenizer.TT_EOF)
						break;
					if (elementPaser.sval.equals("=")) {
						if (elementPaser.nextToken() == StreamTokenizer.TT_EOF)
							break;
						jdbc_id = elementPaser.sval;
					}
					continue;
				} else if (elementPaser.sval.equals("class")) {
					if (elementPaser.nextToken() == StreamTokenizer.TT_EOF)
						break;
					if (elementPaser.sval.equals("=")) {
						if (elementPaser.nextToken() == StreamTokenizer.TT_EOF)
							break;
						jdbc_class = elementPaser.sval;
					}
					continue;
				}
			}
		}
	}

	private void readDBSetting(StringTokenizer tokenizer) throws IOException {
		String token;
		StreamTokenizer elementPaser;
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
						db_ip = elementPaser.sval;
					}
					continue;
				} else if (elementPaser.sval.equals("port")) {
					if (elementPaser.nextToken() == StreamTokenizer.TT_EOF)
						break;
					if (elementPaser.sval.equals("=")) {
						if (elementPaser.nextToken() == StreamTokenizer.TT_EOF)
							break;
						db_port = Integer.parseInt(elementPaser.sval);
					}
					continue;
				} else if (elementPaser.sval.equals("user")) {
					if (elementPaser.nextToken() == StreamTokenizer.TT_EOF)
						break;
					if (elementPaser.sval.equals("=")) {
						if (elementPaser.nextToken() == StreamTokenizer.TT_EOF)
							break;
						db_user = elementPaser.sval;
					}
					continue;
				} else if (elementPaser.sval.equals("password")) {
					if (elementPaser.nextToken() == StreamTokenizer.TT_EOF)
						break;
					if (elementPaser.sval.equals("=")) {
						if (elementPaser.nextToken() == StreamTokenizer.TT_EOF)
							break;
						db_password = elementPaser.sval;
					}
					continue;
				}
			}
		}
	}

	private void createDefaultFile(File file) throws IOException {
		file.createNewFile();
		FileWriter fos = new FileWriter(file);
		fos.write("[Default DB Setting]\n");
		fos.write("ip=" + HelloDrinkingServer.DEFAULT_DB_IP);
		fos.write("port=" + HelloDrinkingServer.DEFAULT_DB_PORT);
		fos.write("user=" + HelloDrinkingServer.DEFAULT_DB_USER);
		fos.write("password=" + HelloDrinkingServer.DEFAULT_DB_PASSWORD);
		fos.flush();

		fos.write("[Default JDBC Setting]\n");
		fos.write("driver=" + HelloDrinkingServer.DEFAULT_JDBC_DRIVER);
		fos.write("id=" + HelloDrinkingServer.DEFAULT_JDBC_ID);
		fos.write("class=" + HelloDrinkingServer.DEFAULT_JDBC_CLASS);
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
