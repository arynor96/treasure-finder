package movement.targetfinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gameinfo.EMyMapPosition;
import map.ClientFullMap;
import map.ClientMapNode;
import map.Coordinate;
import map.ETerrainClient;
import map.TargetHalf;
import movement.helpers.NodesAnalyser;
import movement.helpers.NodesMarker;

public class FortFinder implements ITargetFinder {

	private static Logger logger = LoggerFactory.getLogger(FortFinder.class);

	private ClientFullMap map;
	private Coordinate myPosition;
	private EMyMapPosition mapPosition;
	private boolean firstTimeCalled = true;

	public FortFinder(ClientFullMap map) {
		this.map = map;
	}

	@Override
	public void updateFinder(Coordinate myNewPos, EMyMapPosition mapPosition, Coordinate myPosition) {
		NodesMarker.markAsVisited(myNewPos, this.map);
		this.mapPosition = mapPosition;
		this.myPosition = myPosition;

	}

	@Override
	public Coordinate findTarget() {

		if (firstTimeCalled) {
			firstTimeCalled = false;
			logger.debug("FortFinder was called for the first time to find a target.");
			return calculateMostUnexploredNode();
		}

		return closestUnvisitedProfitableNode();
	}

	private Coordinate closestUnvisitedProfitableNode() {

		Map<Coordinate, ClientMapNode> enemyMapNodes = getEnemyHalf();
		Coordinate closestCoordinate = new Coordinate(0, 0);
		int mostUnexploredNodes = 0;

		List<Coordinate> nearbyNodesUnfiltered = NodesAnalyser.getAdjacentNodes(myPosition, this.map);
		List<Coordinate> nearbyNodes = new ArrayList<Coordinate>();

		// this removes the nearby nodes that are not on my half map.
		for (int i = 0; i < nearbyNodesUnfiltered.size(); i++) {
			if (enemyMapNodes.get(nearbyNodesUnfiltered.get(i)) != null) {
				nearbyNodes.add(nearbyNodesUnfiltered.get(i));
			}
		}

		if (nearbyNodes.size() > 0) {
			for (Coordinate c : nearbyNodes) {
				ClientMapNode nearbyNode = enemyMapNodes.get(c);

				if (!(nearbyNode.isVisited())) {
					if (nearbyNode.getTerrainType() == ETerrainClient.GRASS) {
						int currentNodesUnexplored = NodesAnalyser.getAdjacentUnvisitedSize(c, map);
						if (mostUnexploredNodes <= currentNodesUnexplored) {
							mostUnexploredNodes = currentNodesUnexplored;
							closestCoordinate = c;
						}
					}
					if (nearbyNode.getTerrainType() == ETerrainClient.MOUNTAIN) {
						int currentNodesUnexplored = NodesAnalyser.getAroundUnvisitedSize(c, map);
						if (mostUnexploredNodes < currentNodesUnexplored && currentNodesUnexplored > 3) {
							mostUnexploredNodes = currentNodesUnexplored;
							closestCoordinate = c;
						}
					}
				}
			}
		}

		// if everything around me is visited, this will calculate the closest unvisited
		// zone of the current half map.
		if (mostUnexploredNodes == 0) {
			closestCoordinate = ClosestUnexploredNodeFinder.calculateClosestUnexploredNode(enemyMapNodes, myPosition,
					map);
		}

		return closestCoordinate;
	}

	private Coordinate calculateMostUnexploredNode() {

		Map<Coordinate, ClientMapNode> enemyMapNodes = getEnemyHalf();
		Coordinate unexploredCoordinate = new Coordinate(0, 0);
		int mostUnexploredNodes = 0;

		for (Map.Entry<Coordinate, ClientMapNode> entry : enemyMapNodes.entrySet()) {
			Coordinate possibleTargetCoord = entry.getKey();
			ClientMapNode possibleTargetNode = entry.getValue();
			if (!(possibleTargetNode.isVisited()) && possibleTargetNode.getTerrainType() != ETerrainClient.WATER) {
				int currentNodesUnexplored = NodesAnalyser.getAroundUnvisitedSize(possibleTargetCoord, this.map);
				if (currentNodesUnexplored > mostUnexploredNodes) {
					mostUnexploredNodes = currentNodesUnexplored;
					unexploredCoordinate = possibleTargetCoord;
				}
			}

		}

		return unexploredCoordinate;

	}

	// this will get the opposite part of the map.
	private Map<Coordinate, ClientMapNode> getEnemyHalf() {

		TargetHalf mapSeparator = new TargetHalf(this.map);
		switch (mapPosition) {
		case UP:
			return mapSeparator.getDownPart();
		case DOWN:
			return mapSeparator.getUpPart();
		case LEFT:
			return mapSeparator.getRightPart();
		default:
			return mapSeparator.getLeftPart();
		}

	}

	@Override

	public ClientFullMap getVisitedMap() {
		return this.map;

	}

	@Override
	public boolean isNodeVisited(Coordinate c) {
		return this.map.getNodes().get(c).isVisited();
	}

}
