package kr.android.hellodrinking.transmission.dto;

import java.io.Serializable;

public class ResponceBeanPackege implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7903187436477770064L;
	private boolean isSuccessed;
	private Exception exception;
	private Serializable object;
	private int state;

	public ResponceBeanPackege(boolean isSuccessed) {
		this.setSuccess(isSuccessed);
	}

	public ResponceBeanPackege(Exception exception) {
		if (exception == null)
			setSuccess(true);
		else
			setSuccess(false);
		setException(exception);
	}

	public ResponceBeanPackege(boolean isSuccessed, Exception exception) {
		this.setSuccess(isSuccessed);
		if (exception != null)
			setException(exception);
	}

	public ResponceBeanPackege(String message) {
		if (message == null)
			setSuccess(true);
		else
			setSuccess(false);
		setException(new Exception(message));
	}

	public void setSuccess(boolean isSuccessed) {
		this.isSuccessed = isSuccessed;
	}

	public boolean isSuccessed() {
		return isSuccessed;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public Exception getException() {
		return exception;
	}

	public void setObject(Serializable object) {
		this.object = object;
	}

	public Serializable getObject() {
		return object;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getState() {
		return state;
	}
}
