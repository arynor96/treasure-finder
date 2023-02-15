package rules;

import java.util.HashSet;
import java.util.Set;

import exceptions.GameNotFoundException;
import game.GameID;
import game.GameManager;
import messagesBase.UniqueGameIdentifier;
import messagesBase.UniquePlayerIdentifier;
import messagesBase.messagesFromClient.PlayerHalfMap;

public class GameIDExistence implements IRule {

	private Set<GameID> games = new HashSet<>();

	public GameIDExistence(Set<GameID> games) {
		this.games = games;
	}

	@Override
	public void checkPlayerRegistration(UniqueGameIdentifier gameID) {
		if (!games.contains(new GameID(gameID.getUniqueGameID()))) {
			throw new GameNotFoundException("Game Not Found", "GameID does not exists or it is not correct.");
		}
	}

	@Override
	public void checkMapRules(UniqueGameIdentifier gameID, PlayerHalfMap halfMap, GameManager game) {
		if (!games.contains(new GameID(gameID.getUniqueGameID()))) {
			throw new GameNotFoundException("Game Not Found", "GameID does not exists or it is not correct.");
		}

	}

	@Override
	public void checkGameStateRules(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID) {
		if (!games.contains(new GameID(gameID.getUniqueGameID()))) {
			throw new GameNotFoundException("Game Not Found", "GameID does not exists or it is not correct.");
		}

	}

}
