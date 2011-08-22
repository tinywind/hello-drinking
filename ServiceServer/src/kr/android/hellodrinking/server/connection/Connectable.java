package kr.android.hellodrinking.server.connection;

import java.sql.Connection;
import java.sql.SQLException;

public interface Connectable {
	public Connection getConnection() throws ClassNotFoundException, SQLException;

}
