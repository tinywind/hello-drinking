package kr.android.hellodrinking.transmission.exception;

public class UserModifyException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6850451379492179071L;
	public State state;

	public UserModifyException(String reason, State state) {
		super(reason);
		this.state = state;
	}

	public enum State {
		NotFoundId, NotMatchIdWithPassword, TimeOut, ConnectError
	};
}
