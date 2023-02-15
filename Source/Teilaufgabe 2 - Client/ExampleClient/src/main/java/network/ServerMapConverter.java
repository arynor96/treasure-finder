package network;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import map.ClientFullMap;
import map.ClientMapNode;
import map.Coordinate;
import messagesBase.messagesFromServer.FullMapNode;
import messagesBase.messagesFromServer.GameState;

public class ServerMapConverter {

	public static ClientFullMap convertFullMap(GameState state) {

		Collection<FullMapNode> nodes = state.getMap().get().getMapNodes();
		Map<Coordinate, ClientMapNode> localNodes = new HashMap<Coordinate, ClientMapNode>();

		for (FullMapNode node : nodes) {
			Coordinate currentCoordinate = new Coordinate(node.getX(), node.getY());
			localNodes.put(currentCoordinate, convertToClientMapNode(node));
		}

		return new ClientFullMap(localNodes);
	}

	private static ClientMapNode convertToClientMapNode(FullMapNode node) {

		return ServerNodeConverter.convertToLocalNode(node);

	}

}
