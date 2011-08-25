package kr.android.hellodrinking.transmission.exception;

public class LoginException extends Exception {
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
