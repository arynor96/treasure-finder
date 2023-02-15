package exceptions;

public class ArgsNumberException extends Exception {

	public ArgsNumberException(String message, Throwable cause) {
		super(message, cause);
	}

	public ArgsNumberException(String message) {
		super(message);
	}

}
