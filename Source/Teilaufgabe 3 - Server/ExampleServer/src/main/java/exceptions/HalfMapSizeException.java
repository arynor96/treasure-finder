package exceptions;

import server.exceptions.GenericExampleException;

public class HalfMapSizeException extends GenericExampleException {

	public HalfMapSizeException(String errorName, String errorMessage) {
		super(errorName, errorMessage);
	}

}
