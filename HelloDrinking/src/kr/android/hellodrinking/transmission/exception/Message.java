package kr.android.hellodrinking.transmission.exception;

import java.io.Serializable;
import java.sql.SQLException;

public class Message extends SQLException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -717077963612605769L;
	
	private Serializable object;

	public Message(String message) {
		super(message);
	}

	public Message(Serializable object) {
		super();
		setObject(object);
	}

	public void setObject(Serializable object) {
		this.object = object;
	}

	public Serializable getObject() {
		return object;
	}
}
