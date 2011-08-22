package kr.android.hellodrinking.transmission.exception;

public class RegisterException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6850451379292179071L;
	public State state;

	public RegisterException(String reason, State state) throws Exception {
		this.state = state;
		throw new Exception(reason);
	}

	public enum State {
		AlreadyExistId, AlreadyLogedin, TimeOut, ConnectError
	};
}
