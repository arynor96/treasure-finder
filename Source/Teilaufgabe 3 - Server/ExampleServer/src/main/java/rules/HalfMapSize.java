package rules;

import java.util.Collection;

import exceptions.HalfMapSizeException;
import game.GameManager;
import messagesBase.UniqueGameIdentifier;
import messagesBase.UniquePlayerIdentifier;
import messagesBase.messagesFromClient.PlayerHalfMap;
import messagesBase.messagesFromClient.PlayerHalfMapNode;
import player.Player;

public class HalfMapSize implements IRule {

	public final static int HALFMAP_LENGTH = 9;
	public final static int HALFMAP_HEIGHT = 4;

	@Override
	public void checkPlayerRegistration(UniqueGameIdentifier gameID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkMapRules(UniqueGameIdentifier gameID, PlayerHalfMap halfMap, GameManager game) {
		Player sender = new Player(halfMap.getUniquePlayerID());
		Collection<PlayerHalfMapNode> nodes = halfMap.getMapNodes();
		int max_X = 0;
		int max_Y = 0;
		int terrains = 0;

		for (PlayerHalfMapNode node : nodes) {
			terrains++;
			if (node.getX() > max_X)
				max_X = node.getX();
			if (node.getY() > max_Y)
				max_Y = node.getY();
		}

		if (max_X != 9) {
			game.setWinner(game.getEnemy(sender));
			throw new HalfMapSizeException("Map Size Problem", "The width of the map is wrong!");
		}

		if (max_Y != 4) {
			game.setWinner(game.getEnemy(sender));
			throw new HalfMapSizeException("Map Size Problem", "The height of the map is wrong!");
		}

		if (terrains != 50) {
			game.setWinner(game.getEnemy(sender));
			throw new HalfMapSizeException("Map Size Problem", "Not enough terrain in the half map!");
		}

	}

	@Override
	public void checkGameStateRules(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID) {
		// TODO Auto-generated method stub

	}

}
