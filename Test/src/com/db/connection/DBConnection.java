package com.db.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {//오라클접속만 할것이므로 interface를 이용하지 않음.
	
	public static Connection Connect() throws ClassNotFoundException, SQLException{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		return DriverManager.getConnection(
				"jdbc:oracle:thin:@127.0.0.1:1521:xe", 
				"Project",
				"kitri");
	}
}
