package exceptions;

import server.exceptions.GenericExampleException;

public class PlayerNeededMoreThan5SecondsException extends GenericExampleException {

	public PlayerNeededMoreThan5SecondsException(String errorName, String errorMessage) {
		super(errorName, errorMessage);
		// TODO Auto-generated constructor stub
	}

}
