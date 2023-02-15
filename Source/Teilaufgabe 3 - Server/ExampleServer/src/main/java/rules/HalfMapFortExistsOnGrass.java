package rules;

import java.util.Collection;

import exceptions.HalfMapFortException;
import game.GameManager;
import messagesBase.UniqueGameIdentifier;
import messagesBase.UniquePlayerIdentifier;
import messagesBase.messagesFromClient.ETerrain;
import messagesBase.messagesFromClient.PlayerHalfMap;
import messagesBase.messagesFromClient.PlayerHalfMapNode;
import player.Player;

public class HalfMapFortExistsOnGrass implements IRule {

	@Override
	public void checkPlayerRegistration(UniqueGameIdentifier gameID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkMapRules(UniqueGameIdentifier gameID, PlayerHalfMap halfMap, GameManager game) {
		Player sender = new Player(halfMap.getUniquePlayerID());

		Collection<PlayerHalfMapNode> nodes = halfMap.getMapNodes();
		PlayerHalfMapNode fortNode = new PlayerHalfMapNode();

		for (PlayerHalfMapNode node : nodes) {
			if (node.isFortPresent()) {
				fortNode = node;
			}
		}

		if (!fortNode.isFortPresent()) {
			game.setWinner(game.getEnemy(sender));
			throw new HalfMapFortException("Fort Error", "Fort is not present!");
		} else {
			if (fortNode.getTerrain() != ETerrain.Grass) {
				game.setWinner(game.getEnemy(sender));
				throw new HalfMapFortException("Fort Error", "Fort is not on grass!");
			}
		}

	}

	@Override
	public void checkGameStateRules(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID) {
		// TODO Auto-generated method stub

	}

}
