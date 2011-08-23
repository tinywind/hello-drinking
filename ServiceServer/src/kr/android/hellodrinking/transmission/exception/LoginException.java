package kr.android.hellodrinking.transmission.exception;

import java.sql.SQLException;

public class LoginException extends SQLException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6850451379292179061L;
	public State state;
	
	public LoginException(String reason, State state) {
		super(reason);
		this.state = state;
	}
	
	 public enum State {NotFoundId, NotMatch, TimeOut, ConnectError};
}
