package server.main.converters;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import map.Coordinate;
import map.ServerFullMap;
import map.ServerMapNode;
import messagesBase.messagesFromClient.PlayerHalfMap;
import messagesBase.messagesFromClient.PlayerHalfMapNode;
import player.Player;

public class PlayerHalfMapConverter {

	public static ServerFullMap toLocal(PlayerHalfMap halfMap) {

		String creator = halfMap.getUniquePlayerID();
		Collection<PlayerHalfMapNode> nodes = halfMap.getMapNodes();
		Map<Coordinate, ServerMapNode> convertedNodes = new HashMap<Coordinate, ServerMapNode>();

		for (PlayerHalfMapNode node : nodes) {
			Coordinate currentCoord = new Coordinate(node.getX(), node.getY());
			ServerMapNode convertedNode = MapNodeConverter.toLocal(node, new Player(creator));
			convertedNodes.put(currentCoord, convertedNode);
		}

		return new ServerFullMap(convertedNodes);

	}

}
