package map;

import java.util.Map;
import java.util.Random;
import java.util.Set;

public class ServerMapBuilder {

	public static ServerFullMap mergeMaps(ServerFullMap halfMap1, ServerFullMap halfMap2) {

		Map<Coordinate, ServerMapNode> fullMapNodes;
		Map<Coordinate, ServerMapNode> nodesToAdd;
		Random rand = new Random();

		int shape = rand.nextInt(2);
		int firstHalfMap = rand.nextInt(2);

		if (firstHalfMap == 1) {
			fullMapNodes = halfMap1.getNodes();
			nodesToAdd = halfMap2.getNodes();
		} else {
			fullMapNodes = halfMap2.getNodes();
			nodesToAdd = halfMap1.getNodes();
		}

		if (shape == 1) {
			return buildSquare(fullMapNodes, nodesToAdd);
		} else {
			return buildRectangle(fullMapNodes, nodesToAdd);
		}

	}

	private static ServerFullMap buildRectangle(Map<Coordinate, ServerMapNode> halfMap,
			Map<Coordinate, ServerMapNode> toAdd) {

		Map<Coordinate, ServerMapNode> fullMap = halfMap;
		Set<Map.Entry<Coordinate, ServerMapNode>> nodesToAdd = toAdd.entrySet();

		for (Map.Entry<Coordinate, ServerMapNode> entry : nodesToAdd) {
			Coordinate newCoordinate = new Coordinate(entry.getKey().getX() + 10, entry.getKey().getY());
			fullMap.put(newCoordinate, entry.getValue());
		}

		return new ServerFullMap(fullMap, EMapShape.RECTANGLE);
	}

	private static ServerFullMap buildSquare(Map<Coordinate, ServerMapNode> halfMap,
			Map<Coordinate, ServerMapNode> toAdd) {

		Map<Coordinate, ServerMapNode> fullMap = halfMap;
		Set<Map.Entry<Coordinate, ServerMapNode>> nodesToAdd = toAdd.entrySet();

		for (Map.Entry<Coordinate, ServerMapNode> entry : nodesToAdd) {
			Coordinate newCoordinate = new Coordinate(entry.getKey().getX(), entry.getKey().getY() + 5);
			fullMap.put(newCoordinate, entry.getValue());
		}

		return new ServerFullMap(fullMap, EMapShape.SQUARE);

	}

}
