package movement.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import map.ClientFullMap;
import map.ClientMapNode;
import map.Coordinate;
import map.ETerrainClient;

public class NodesAnalyser {

	public static List<Coordinate> getAllAroundNodes(Coordinate myNewPos, ClientFullMap map) {

		int curX = myNewPos.getX();
		int curY = myNewPos.getY();

		List<Coordinate> visibleNodes = new ArrayList<Coordinate>();

		// they are clockwise.
		if (ValidCoordinateChecker.isValidNode(curX, curY - 1, map)) {
			visibleNodes.add(new Coordinate(curX, curY - 1));
		}

		if (ValidCoordinateChecker.isValidNode(curX + 1, curY - 1, map)) {
			visibleNodes.add(new Coordinate(curX + 1, curY - 1));
		}

		if (ValidCoordinateChecker.isValidNode(curX + 1, curY, map)) {
			visibleNodes.add(new Coordinate(curX + 1, curY));

		}

		if (ValidCoordinateChecker.isValidNode(curX + 1, curY + 1, map)) {
			visibleNodes.add(new Coordinate(curX + 1, curY + 1));
		}

		if (ValidCoordinateChecker.isValidNode(curX, curY + 1, map)) {
			visibleNodes.add(new Coordinate(curX, curY + 1));
		}

		if (ValidCoordinateChecker.isValidNode(curX - 1, curY + 1, map)) {
			visibleNodes.add(new Coordinate(curX - 1, curY + 1));
		}

		if (ValidCoordinateChecker.isValidNode(curX - 1, curY, map)) {
			visibleNodes.add(new Coordinate(curX - 1, curY));
		}

		if (ValidCoordinateChecker.isValidNode(curX - 1, curY - 1, map)) {
			visibleNodes.add(new Coordinate(curX - 1, curY - 1));
		}

		return visibleNodes;

	}

	public static List<Coordinate> getAdjacentNodes(Coordinate myNewPos, ClientFullMap map) {

		int curX = myNewPos.getX();
		int curY = myNewPos.getY();

		List<Coordinate> visibleNodes = new ArrayList<Coordinate>();

		if (ValidCoordinateChecker.isValidNode(curX - 1, curY, map)) {
			visibleNodes.add(new Coordinate(curX - 1, curY));
		}

		if (ValidCoordinateChecker.isValidNode(curX + 1, curY, map)) {
			visibleNodes.add(new Coordinate(curX + 1, curY));

		}

		if (ValidCoordinateChecker.isValidNode(curX, curY - 1, map)) {
			visibleNodes.add(new Coordinate(curX, curY - 1));
		}

		if (ValidCoordinateChecker.isValidNode(curX, curY + 1, map)) {
			visibleNodes.add(new Coordinate(curX, curY + 1));
		}

		return visibleNodes;

	}

	public static int getAroundUnvisitedSize(Coordinate myNewPos, ClientFullMap map) {
		List<Coordinate> allAroundNodes = getAllAroundNodes(myNewPos, map);
		List<Coordinate> unexplored = new ArrayList<Coordinate>();

		for (int i = 0; i < allAroundNodes.size(); i++) {
			ClientMapNode currentNode = map.getNodes().get(allAroundNodes.get(i));
			if (!(currentNode.isVisited()) && currentNode.getTerrainType() == ETerrainClient.GRASS) {
				unexplored.add(allAroundNodes.get(i));
			}
		}

		return unexplored.size();
	}

	public static int getAdjacentUnvisitedSize(Coordinate myNewPos, ClientFullMap map) {
		List<Coordinate> getAdjacentNodes = getAdjacentNodes(myNewPos, map);
		List<Coordinate> unexplored = new ArrayList<Coordinate>();

		for (int i = 0; i < getAdjacentNodes.size(); i++) {
			ClientMapNode currentNode = map.getNodes().get(getAdjacentNodes.get(i));
			if (!(currentNode.isVisited()) && currentNode.getTerrainType() == ETerrainClient.GRASS) {
				unexplored.add(getAdjacentNodes.get(i));
			}
		}

		return unexplored.size();
	}

	public static int calculateMoveCost(Coordinate current, Coordinate target, ClientFullMap map) {
		Map<Coordinate, ClientMapNode> mapNodes = map.getNodes();
		return calculateTerrainCost(mapNodes.get(current)) + calculateTerrainCost(mapNodes.get(target));
	}

	public static int calculateTerrainCost(ClientMapNode node) {
		if (node.getTerrainType() == ETerrainClient.GRASS)
			return 1;
		else {
			return 2;
		}

	}

}
