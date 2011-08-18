package kr.android.transmission;

public class TransmissionException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7442355608626778601L;

	TransmissionException(String reason) throws Exception{
		throw new Exception(reason);
	}
}
