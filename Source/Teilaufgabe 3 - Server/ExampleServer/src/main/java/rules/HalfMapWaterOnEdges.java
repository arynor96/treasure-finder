package rules;

import exceptions.HalfMapWaterOnEdgeException;
import game.GameManager;
import messagesBase.UniqueGameIdentifier;
import messagesBase.UniquePlayerIdentifier;
import messagesBase.messagesFromClient.ETerrain;
import messagesBase.messagesFromClient.PlayerHalfMap;
import messagesBase.messagesFromClient.PlayerHalfMapNode;
import player.Player;

public class HalfMapWaterOnEdges implements IRule {

	public final static int MAXIMUM_WATER_SHORTEDGE = 2;
	public final static int MAXIMUM_WATER_LONGEDGE = 4;

	@Override
	public void checkPlayerRegistration(UniqueGameIdentifier gameID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkMapRules(UniqueGameIdentifier gameID, PlayerHalfMap halfMap, GameManager game) {

		Player sender = new Player(halfMap.getUniquePlayerID());

		int waterShortEdge = MAXIMUM_WATER_SHORTEDGE;
		int waterShortLeftEdge = MAXIMUM_WATER_SHORTEDGE;
		int waterLongEdge = MAXIMUM_WATER_LONGEDGE;
		int waterLongTopEdge = MAXIMUM_WATER_LONGEDGE;

		for (PlayerHalfMapNode node : halfMap.getMapNodes()) {

			// short right edge
			if (node.getY() == 0 && node.getTerrain() == ETerrain.Water)
				waterLongTopEdge--;
			if (waterLongTopEdge < 0) {
				game.setWinner(game.getEnemy(sender));
				throw new HalfMapWaterOnEdgeException("Water on Edge", "Too much water on top long edge!");
			}

			if (node.getY() == 4 && node.getTerrain() == ETerrain.Water)
				waterLongEdge--;
			if (waterLongEdge < 0) {
				game.setWinner(game.getEnemy(sender));
				throw new HalfMapWaterOnEdgeException("Water on Edge", "Too much water on bottom long edge!");
			}

			if (node.getX() == 0 && node.getTerrain() == ETerrain.Water)
				waterShortLeftEdge--;
			if (waterShortLeftEdge < 0) {
				game.setWinner(game.getEnemy(sender));
				throw new HalfMapWaterOnEdgeException("Water on Edge", "Too much water on left short edge!");
			}

			if (node.getX() == 9 && node.getTerrain() == ETerrain.Water)
				waterShortEdge--;
			if (waterShortEdge < 0) {
				game.setWinner(game.getEnemy(sender));
				throw new HalfMapWaterOnEdgeException("Water on Edge", "Too much water on right short edge!");
			}

		}

	}

	@Override
	public void checkGameStateRules(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID) {
		// TODO Auto-generated method stub

	}

}
