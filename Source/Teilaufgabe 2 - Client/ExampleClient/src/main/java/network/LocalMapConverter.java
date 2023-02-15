package network;

import java.util.HashSet;
import java.util.Set;

import map.ClientFullMap;
import map.ClientMapNode;
import map.Coordinate;
import map.EFort;
import map.ETerrainClient;
import messagesBase.messagesFromClient.ETerrain;
import messagesBase.messagesFromClient.PlayerHalfMap;
import messagesBase.messagesFromClient.PlayerHalfMapNode;

public class LocalMapConverter {

	public static PlayerHalfMap convertHalfMap(String playerID, ClientFullMap halfMap) {

		Set<PlayerHalfMapNode> convertedNodes = new HashSet<PlayerHalfMapNode>();
		for (Coordinate c : halfMap.getNodes().keySet()) {
			convertedNodes.add(convertToHalfMapNode(c, halfMap.getNodes().get(c)));

		}
		return new PlayerHalfMap(playerID, convertedNodes);

	}

	private static PlayerHalfMapNode convertToHalfMapNode(Coordinate c, ClientMapNode node) {
		boolean fortPresent = node.getFortState() == EFort.MYFORT;
		return new PlayerHalfMapNode(c.getX(), c.getY(), fortPresent, convertTerrain(node.getTerrainType()));
	}

	private static ETerrain convertTerrain(ETerrainClient terrain) {
		switch (terrain) {
		case GRASS:
			return ETerrain.Grass;
		case WATER:
			return ETerrain.Water;
		default:
			return ETerrain.Mountain;

		}

	}

}
