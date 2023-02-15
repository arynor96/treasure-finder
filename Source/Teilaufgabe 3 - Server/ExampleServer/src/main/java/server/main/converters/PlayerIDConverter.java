package server.main.converters;

import messagesBase.UniquePlayerIdentifier;
import player.Player;

public class PlayerIDConverter {

	public static UniquePlayerIdentifier toNetwork(String playerID) {
		return new UniquePlayerIdentifier(playerID);
	}

	public static Player toLocal(String playerID) {
		return new Player(playerID);
	}

}
