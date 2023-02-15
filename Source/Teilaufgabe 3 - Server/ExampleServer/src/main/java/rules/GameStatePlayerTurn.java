package rules;

import exceptions.GameStatsNotPlayersTurn;
import game.GameManager;
import messagesBase.UniqueGameIdentifier;
import messagesBase.UniquePlayerIdentifier;
import messagesBase.messagesFromClient.PlayerHalfMap;
import player.Player;

public class GameStatePlayerTurn implements IRule {

	@Override
	public void checkPlayerRegistration(UniqueGameIdentifier gameID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkMapRules(UniqueGameIdentifier gameID, PlayerHalfMap halfMap, GameManager game) {
		// GameManager gameManager = games.get(new GameID(gameID.getUniqueGameID()));
		Player playerFromNetwork = new Player(halfMap.getUniquePlayerID());

		if (game.getNextPlayerTurn() != playerFromNetwork) {
			game.setWinner(game.getNextPlayerTurn());
			throw new GameStatsNotPlayersTurn("Not players turn!", "It is not your turn!");
		}

	}

	@Override
	public void checkGameStateRules(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID) {

	}

}
