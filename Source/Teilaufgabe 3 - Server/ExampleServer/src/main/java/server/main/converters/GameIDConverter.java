package server.main.converters;

import game.GameID;
import messagesBase.UniqueGameIdentifier;

public class GameIDConverter {

	public static UniqueGameIdentifier toNetwork(String gameID) {
		return new UniqueGameIdentifier(gameID);
	}

	public static GameID toLocal(String gameID) {
		return new GameID(gameID);
	}

}
