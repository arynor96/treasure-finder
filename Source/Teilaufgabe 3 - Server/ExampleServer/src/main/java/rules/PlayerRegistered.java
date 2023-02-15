package rules;

import java.util.HashMap;
import java.util.Map;

import exceptions.PlayerNotRegisteredException;
import game.GameID;
import game.GameManager;
import messagesBase.UniqueGameIdentifier;
import messagesBase.UniquePlayerIdentifier;
import messagesBase.messagesFromClient.PlayerHalfMap;

public class PlayerRegistered implements IRule {

	private Map<GameID, GameManager> games = new HashMap<>();

	public PlayerRegistered(Map<GameID, GameManager> games) {
		this.games = games;
	}

	@Override
	public void checkPlayerRegistration(UniqueGameIdentifier gameID) {
		// TODO Auto-generated method stub
	}

	@Override
	public void checkMapRules(UniqueGameIdentifier gameID, PlayerHalfMap halfMap, GameManager game) {
		String networkPlayerID = halfMap.getUniquePlayerID();
		if (!games.get(new GameID(gameID.getUniqueGameID())).isPlayerRegistered(networkPlayerID)) {
			throw new PlayerNotRegisteredException("Player not found! ",
					"Player " + networkPlayerID + " is not registered.");
		}

	}

	@Override
	public void checkGameStateRules(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID) {
		String networkPlayerID = playerID.getUniquePlayerID();
		if (!games.get(new GameID(gameID.getUniqueGameID())).isPlayerRegistered(networkPlayerID)) {
			throw new PlayerNotRegisteredException("Player not found! ",
					"Player " + networkPlayerID + " is not registered.");
		}

	}

}
