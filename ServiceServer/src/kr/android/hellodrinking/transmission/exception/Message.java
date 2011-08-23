package kr.android.hellodrinking.transmission.exception;

import java.sql.SQLException;

public class Message extends SQLException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -717077963612605769L;
	
	private String message;

	public Message(String message) {
		this.message = message;
	}
	
	public String getMessage(){
		return message;
	}
}
