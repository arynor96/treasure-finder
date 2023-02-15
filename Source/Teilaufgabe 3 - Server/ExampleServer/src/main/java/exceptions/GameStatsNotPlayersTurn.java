package exceptions;

import server.exceptions.GenericExampleException;

public class GameStatsNotPlayersTurn extends GenericExampleException {

	public GameStatsNotPlayersTurn(String errorName, String errorMessage) {
		super(errorName, errorMessage);
		// TODO Auto-generated constructor stub
	}

}
