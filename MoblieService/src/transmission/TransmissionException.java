package transmission;

public class TransmissionException extends Exception {
	TransmissionException(String reason) throws Exception{
		throw new Exception(reason);
	}
}
