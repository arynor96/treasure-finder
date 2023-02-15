package rules;

import java.util.Map;

import exceptions.HalfMapIslandException;
import game.GameManager;
import map.Coordinate;
import map.ETerrainType;
import map.MapFloodFill;
import map.ServerFullMap;
import map.ServerMapNode;
import messagesBase.UniqueGameIdentifier;
import messagesBase.UniquePlayerIdentifier;
import messagesBase.messagesFromClient.PlayerHalfMap;
import player.Player;
import server.main.converters.PlayerHalfMapConverter;

public class HalfMapIsland implements IRule {

	@Override
	public void checkPlayerRegistration(UniqueGameIdentifier gameID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkMapRules(UniqueGameIdentifier gameID, PlayerHalfMap halfMap, GameManager game) {
		Player sender = new Player(halfMap.getUniquePlayerID());
		ServerFullMap convertedMap = PlayerHalfMapConverter.toLocal(halfMap);

		MapFloodFill floodFiller = new MapFloodFill(convertedMap.getNodes());
		Coordinate startPoint = findFirstGrassNode(convertedMap.getNodes());

		floodFiller.floodFill(startPoint);
		Map<Coordinate, ServerMapNode> floodNodes = floodFiller.getFloodNodes();

		for (ServerMapNode node : floodNodes.values()) {
			if (node.getTerrainType() == ETerrainType.GRASS) {
				game.setWinner(game.getEnemy(sender));
				throw new HalfMapIslandException("Half Map Error", "Island found.");
			}

			if (node.getTerrainType() == ETerrainType.MOUNTAIN) {
				game.setWinner(game.getEnemy(sender));
				throw new HalfMapIslandException("Half Map Error", "Island found.");
			}

		}

	}

	private Coordinate findFirstGrassNode(Map<Coordinate, ServerMapNode> nodes) {

		Coordinate first = nodes.entrySet().stream()
				.filter(node -> node.getValue().getTerrainType() == ETerrainType.GRASS).map(Map.Entry::getKey)
				.findFirst().orElse(null);

		return first;

	}

	@Override
	public void checkGameStateRules(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID) {
		// TODO Auto-generated method stub

	}

}
