package exceptions;

import server.exceptions.GenericExampleException;

public class PlayerNotRegisteredException extends GenericExampleException {

	public PlayerNotRegisteredException(String errorName, String errorMessage) {
		super(errorName, errorMessage);
	}

}
