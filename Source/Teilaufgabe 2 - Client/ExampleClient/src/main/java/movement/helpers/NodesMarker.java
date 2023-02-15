package movement.helpers;

import java.util.List;

import map.ClientFullMap;
import map.Coordinate;
import map.ETerrainClient;

public class NodesMarker {

	public static void markAsVisited(Coordinate myNewPos, ClientFullMap map) {
		// If my current position is on a mountain, I mark the around nodes as visited
		// too because they are no longer unknown/unexplored.
		if (map.getNodes().get(myNewPos).getTerrainType() == ETerrainClient.MOUNTAIN) {
			List<Coordinate> nearNodes = NodesAnalyser.getAllAroundNodes(myNewPos, map);

			// I do also have to mark my current position as visited
			nearNodes.add(myNewPos);

			for (Coordinate c : nearNodes) {
				map.getNodes().get(c).setVisited(true);
			}
		} else {
			map.getNodes().get(myNewPos).setVisited(true);
		}
	}

}
