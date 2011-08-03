package transmission;

public interface Sendable {
	public boolean login(String id, String pw);
	public boolean logout(String id);
	public boolean join(UserInfo info) throws TransmissionException;
}
