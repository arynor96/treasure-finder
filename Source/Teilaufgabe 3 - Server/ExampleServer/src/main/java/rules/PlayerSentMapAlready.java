package rules;

import java.util.Map;
import java.util.Set;

import exceptions.PlayerAlreadySentMapException;
import game.GameManager;
import map.Coordinate;
import map.ServerFullMap;
import map.ServerMapNode;
import messagesBase.UniqueGameIdentifier;
import messagesBase.UniquePlayerIdentifier;
import messagesBase.messagesFromClient.PlayerHalfMap;
import player.Player;

public class PlayerSentMapAlready implements IRule {

	@Override
	public void checkPlayerRegistration(UniqueGameIdentifier gameID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkMapRules(UniqueGameIdentifier gameID, PlayerHalfMap halfMap, GameManager game) {
		Player sender = new Player(halfMap.getUniquePlayerID());

		ServerFullMap map = game.getMap();

		Set<Map.Entry<Coordinate, ServerMapNode>> nodes = map.getNodes().entrySet();
		for (var entry : nodes) {
			if (entry.getValue().getCreator().equals(sender)) {
				game.setWinner(game.getEnemy(sender));
				throw new PlayerAlreadySentMapException("Player already sent map", "You have already sent a half map!");
			}
		}

	}

	@Override
	public void checkGameStateRules(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID) {
		// TODO Auto-generated method stub

	}

}
