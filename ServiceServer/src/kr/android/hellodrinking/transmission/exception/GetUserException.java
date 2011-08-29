package kr.android.hellodrinking.transmission.exception;

public class GetUserException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6850451379292179061L;
	public State state;

	public GetUserException(String reason, State state) {
		super(reason);
		this.state = state;
	}

	public enum State {
		NotFoundId, TimeOut, ConnectError
	};
}