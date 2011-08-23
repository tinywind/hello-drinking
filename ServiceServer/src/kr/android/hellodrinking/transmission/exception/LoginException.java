package kr.android.hellodrinking.transmission.exception;

import java.sql.SQLException;

public class LoginException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6850451379292179061L;
	public State state;
	
	public LoginException(String reason, State state) throws SQLException{
		this.state = state;
		throw new SQLException(reason);
	}
	
	 public enum State {NotFoundId, NotMatch, TimeOut, ConnectError};
}
