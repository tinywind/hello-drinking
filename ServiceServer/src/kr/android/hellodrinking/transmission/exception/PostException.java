package kr.android.hellodrinking.transmission.exception;

public class PostException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 995045137929179071L;
	public State state;

	public PostException(String reason, State state) {
		super(reason);
		this.state = state;
	}

	public enum State {
		NotKnownError
	};
}
