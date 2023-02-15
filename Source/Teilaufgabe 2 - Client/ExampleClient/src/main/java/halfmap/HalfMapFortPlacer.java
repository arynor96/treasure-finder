package halfmap;

import java.util.Map;

import map.ClientFullMap;
import map.ClientMapNode;
import map.Coordinate;
import map.ETerrainClient;
import movement.helpers.NodesAnalyser;

public class HalfMapFortPlacer {

	public static Coordinate placeFort(Map<Coordinate, ClientMapNode> nodes) {

		int mostInaccessible = Integer.MAX_VALUE;
		Coordinate mostInaccessibleCoordinate = new Coordinate(0, 0);

		for (Map.Entry<Coordinate, ClientMapNode> entry : nodes.entrySet()) {
			ClientMapNode currentNode = entry.getValue();
			int currentIsolation = NodesAnalyser.getAdjacentUnvisitedSize(entry.getKey(), new ClientFullMap(nodes));
			if (currentIsolation < mostInaccessible) {
				if (currentNode.getTerrainType() == ETerrainClient.GRASS) {
					mostInaccessible = currentIsolation;
					mostInaccessibleCoordinate = entry.getKey();
				}
			}
		}
		return mostInaccessibleCoordinate;
	}

}
