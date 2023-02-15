package movement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exceptions.NewTargetIsWaterException;
import map.ClientFullMap;
import map.ClientMapNode;
import map.Coordinate;
import map.ETerrainClient;
import movement.helpers.NodesAnalyser;

public class PathFinder {

	// TAKEN FROM https://www.programiz.com/dsa/dijkstra-algorithm <1>
	// TAKEN FROM https://isaaccomputerscience.org/concepts/dsa_search_dijkstra <1>
	// On those links I have looked to remember how Dijkstra works.
	// Since there is a high chance that my implementation is influenced by them,
	// I have mentioned both of them.
	// I have spent most of the time on IssacComputerScience website mentioned above
	// more exactly on the second tab: Dijkstra’s algorithm - step by step

	private final static Logger logger = LoggerFactory.getLogger(PathFinder.class);

	private ClientFullMap map;

	public PathFinder(ClientFullMap map) {
		this.map = map;
	}

	public Stack<Coordinate> calculatePath(Coordinate start, Coordinate target) {

		if (map.getNodes().get(target).getTerrainType() == ETerrainClient.WATER)
			throw new NewTargetIsWaterException(
					"Path Finder has received as Target a water field! Check the TargetFinder class!");

		logger.debug("Building path from: " + start + " to " + target);

		// ###### TAKEN FROM START <1> ######

		Set<Coordinate> unvisited = new HashSet<Coordinate>();
		Set<Coordinate> visited = new HashSet<Coordinate>();

		for (Map.Entry<Coordinate, ClientMapNode> entry : map.getNodes().entrySet()) {

			if (!start.equals(entry.getKey())) {
				entry.getValue().setDijkstraCost(Integer.MAX_VALUE);
				entry.getValue().setPreviousNode(null);
				unvisited.add(entry.getKey());
			} else {
				entry.getValue().setDijkstraCost(0);
				entry.getValue().setPreviousNode(null);
				unvisited.add(entry.getKey());
			}
		}

		while (!(visited.contains(target))) {
			Coordinate current = lowestDijkstraCost(unvisited);

			logger.trace("My current lowestCost is : " + current);

			List<Coordinate> neighbors = NodesAnalyser.getAdjacentNodes(current, this.map);
			for (Coordinate neighbor : neighbors) {
				if (!visited.contains(neighbor)) {
					int distanceToNeighbor = map.getNodes().get(current).getDijkstraCost()
							+ calculateMoveCost(current, neighbor);
					if (distanceToNeighbor < map.getNodes().get(neighbor).getDijkstraCost()) {
						map.getNodes().get(neighbor).setDijkstraCost(distanceToNeighbor);
						map.getNodes().get(neighbor).setPreviousNode(current);
					}
				}
			}
			visited.add(current);
			unvisited.remove(current);
		}
		// ###### TAKEN FROM END <1> ######

		List<Coordinate> path = buildPath(start, target);
		logger.trace("Dijkstra not yet reversed: " + path + path.size());

		Stack<Coordinate> pathStack = new Stack<Coordinate>();
		for (Coordinate c : path) {
			pathStack.push(c);
		}

		// I don't have to go to my current position, I remove my current position
		// coordinate from the stack. That is always the first one.
		pathStack.pop();

		logger.trace("Dijkstra AFTER last element removed: " + pathStack);
		return pathStack;

	}

	private int calculateMoveCost(Coordinate current, Coordinate target) {
		Map<Coordinate, ClientMapNode> mapNodes = map.getNodes();
		return calculateTerrainCost(mapNodes.get(current)) + calculateTerrainCost(mapNodes.get(target));
	}

	private int calculateTerrainCost(ClientMapNode node) {
		if (node.getTerrainType() == ETerrainClient.GRASS)
			return 1;
		else {
			return 2;
		}

	}

	private List<Coordinate> buildPath(Coordinate start, Coordinate target) {

		Map<Coordinate, ClientMapNode> mapNodes = map.getNodes();
		List<Coordinate> path = new ArrayList<Coordinate>();
		Coordinate current = target;
		while (!(current.equals(start))) {
			path.add(current);

			if (mapNodes.get(current).getPreviousNode() == null)
				break;
			else
				current = mapNodes.get(current).getPreviousNode();
		}
		path.add(start);

		logger.trace("Build path has a path of length: " + path.size());
		return path;
	}

	private Coordinate lowestDijkstraCost(Set<Coordinate> list) {
		int minValue = Integer.MAX_VALUE;
		Coordinate minCoord = null;
		for (Coordinate coord : list) {
			if (map.getNodes().get(coord).getDijkstraCost() < minValue) {
				minValue = map.getNodes().get(coord).getDijkstraCost();
				minCoord = coord;
			}
		}
		return minCoord;
	}

}
