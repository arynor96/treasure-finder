package exceptions;

public class PlayerRegistrationInformationException extends Exception {

	public PlayerRegistrationInformationException(String message, Throwable cause) {
		super(message, cause);
	}

	public PlayerRegistrationInformationException(String message) {
		super(message);
	}

}
