package kr.android.hellodrinking.transmission.exception;

public class RegisterException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6850451379292179071L;
	public State state;

	public RegisterException(String reason, State state){
		super(reason);
		this.state = state;
	}

	public enum State {
		AlreadyExistId, AlreadyLogedin, TimeOut, ConnectError
	};
}
