package rules;

import java.util.HashMap;
import java.util.Map;

import exceptions.GameIsFullException;
import game.GameID;
import game.GameManager;
import messagesBase.UniqueGameIdentifier;
import messagesBase.UniquePlayerIdentifier;
import messagesBase.messagesFromClient.PlayerHalfMap;
import server.main.converters.GameIDConverter;

public class PlayerMax2Players implements IRule {

	private Map<GameID, GameManager> games = new HashMap<>();

	public PlayerMax2Players(Map<GameID, GameManager> games) {
		this.games = games;
	}

	@Override
	public void checkPlayerRegistration(UniqueGameIdentifier gameID) {
		GameManager currentGame = games.get(GameIDConverter.toLocal(gameID.getUniqueGameID()));
		if (currentGame.isGameFull())
			throw new GameIsFullException("Game is full", "2 Players are already registed to this game!");

	}

	@Override
	public void checkMapRules(UniqueGameIdentifier gameID, PlayerHalfMap halfMap, GameManager game) {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkGameStateRules(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID) {
		// TODO Auto-generated method stub

	}

}
