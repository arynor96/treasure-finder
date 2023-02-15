package exceptions;

import server.exceptions.GenericExampleException;

public class GameNotFoundException extends GenericExampleException {

	public GameNotFoundException(String errorName, String errorMessage) {
		super(errorName, errorMessage);
		// TODO Auto-generated constructor stub
	}

}
