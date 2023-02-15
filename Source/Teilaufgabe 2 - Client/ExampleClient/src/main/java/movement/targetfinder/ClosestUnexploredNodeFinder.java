package movement.targetfinder;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import map.ClientFullMap;
import map.ClientMapNode;
import map.Coordinate;
import map.ETerrainClient;
import movement.helpers.NodesAnalyser;

public class ClosestUnexploredNodeFinder {

	private static Logger logger = LoggerFactory.getLogger(ClosestUnexploredNodeFinder.class);

	// if everything around me is visited, this will calculate the closest unvisited
	// grass zone of the current half map.
	public static Coordinate calculateClosestUnexploredNode(Map<Coordinate, ClientMapNode> myHalfMapNodes,
			Coordinate myPosition, ClientFullMap map) {

		Coordinate closestCoordinate = new Coordinate(0, 0);
		int mostUnexploredNodes = 0;
		double minimumDistance = Integer.MAX_VALUE;

		for (Map.Entry<Coordinate, ClientMapNode> entry : myHalfMapNodes.entrySet()) {
			Coordinate possibleTargetCoord = entry.getKey();
			ClientMapNode possibleTargetNode = entry.getValue();

			double distance = DistanceCalculator.calculateDistance(myPosition, possibleTargetCoord);

			if (!(possibleTargetNode.isVisited()) && possibleTargetNode.getTerrainType() == ETerrainClient.GRASS) {
				int currentCoordinateUnexplored = NodesAnalyser.getAdjacentUnvisitedSize(possibleTargetCoord, map);
				if (currentCoordinateUnexplored >= mostUnexploredNodes && distance < minimumDistance) {
					mostUnexploredNodes = currentCoordinateUnexplored;
					minimumDistance = distance;
					closestCoordinate = possibleTargetCoord;
				}
			}

			if (!(possibleTargetNode.isVisited()) && possibleTargetNode.getTerrainType() == ETerrainClient.MOUNTAIN) {
				int currentCoordinateUnexplored = NodesAnalyser.getAroundUnvisitedSize(possibleTargetCoord, map);
				if (currentCoordinateUnexplored >= 4 && distance < minimumDistance) {
					mostUnexploredNodes = currentCoordinateUnexplored;
					minimumDistance = distance;
					closestCoordinate = possibleTargetCoord;
				}
			}

		}

		logger.trace("Closest unexplored profitable node is: " + closestCoordinate);

		return closestCoordinate;

	}

}
