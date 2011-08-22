package kr.android.hellodrinking.transmission.exception;

public class LoginException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6850451379292179061L;
	public State state;
	
	public LoginException(String reason, State state) throws Exception{
		this.state = state;
		throw new Exception(reason);
	}
	
	 public enum State {NotFoundId, NotMatch, TimeOut, ConnectError};
}
