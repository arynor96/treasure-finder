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

public class TreasureFinder implements ITargetFinder {

	private static Logger logger = LoggerFactory.getLogger(TreasureFinder.class);

	private ClientFullMap map;
	private EMyMapPosition mapPosition = EMyMapPosition.UNKNOWN;
	private Coordinate myPosition;
	private List<Coordinate> gardenNodes;

	public TreasureFinder(ClientFullMap map) {
		this.map = map;
	}

	@Override
	public void updateFinder(Coordinate myNewPos, EMyMapPosition mapPosition, Coordinate myPosition) {
		NodesMarker.markAsVisited(myNewPos, this.map);

		this.myPosition = myPosition;

		if (this.mapPosition == EMyMapPosition.UNKNOWN) {
			this.gardenNodes = fortGarden();
		}

		this.mapPosition = mapPosition;
	}

	@Override
	public Coordinate findTarget() {

		if (!(gardenNodes.isEmpty())) {
			Coordinate nextTarget = gardenNodes.get(gardenNodes.size() - 1);
			gardenNodes.remove(gardenNodes.size() - 1);
			logger.debug("Next direction is in my gardern");
			return nextTarget;
		}

		return closestUnvisitedProfitableNode();
	}

	private Coordinate closestUnvisitedProfitableNode() {

		Map<Coordinate, ClientMapNode> myHalfMapNodes = getMyHalf();
		Coordinate closestCoordinate = new Coordinate(0, 0);
		int mostUnexploredNodes = 0;

		List<Coordinate> nearbyNodesUnfiltered = NodesAnalyser.getAdjacentNodes(myPosition, this.map);
		List<Coordinate> nearbyNodes = new ArrayList<Coordinate>();

		// this removes the nearby nodes that are not on my half map.
		for (int i = 0; i < nearbyNodesUnfiltered.size(); i++) {
			if (myHalfMapNodes.get(nearbyNodesUnfiltered.get(i)) != null) {
				nearbyNodes.add(nearbyNodesUnfiltered.get(i));
			}
		}

		if (nearbyNodes.size() > 0) {
			for (Coordinate c : nearbyNodes) {
				ClientMapNode nearbyNode = myHalfMapNodes.get(c);

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
						if (mostUnexploredNodes < currentNodesUnexplored && currentNodesUnexplored > 2) {
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
			closestCoordinate = ClosestUnexploredNodeFinder.calculateClosestUnexploredNode(myHalfMapNodes, myPosition,
					map);
		}

		return closestCoordinate;

	}

	// gets the "good" nodes that are around the fort, if there are any, those are
	// my first targets.
	private List<Coordinate> fortGarden() {

		List<Coordinate> allNodes = new ArrayList<Coordinate>();
		allNodes = NodesAnalyser.getAllAroundNodes(myPosition, this.map);

		List<Coordinate> gardenNodes = new ArrayList<Coordinate>();

		for (int i = 0; i < allNodes.size(); i++) {
			ClientMapNode currentNode = map.getNodes().get(allNodes.get(i));

			if (currentNode.getTerrainType() == ETerrainClient.MOUNTAIN) {
				if (NodesAnalyser.getAroundUnvisitedSize(allNodes.get(i), this.map) > 5) {
					gardenNodes.add(allNodes.get(i));
				}
			} else {
				// this will never happen if > 4.
				if (NodesAnalyser.getAdjacentUnvisitedSize(allNodes.get(i), this.map) > 3)
					gardenNodes.add(allNodes.get(i));
			}

		}

		logger.debug("MyGarden has + " + gardenNodes.size() + " profitable nodes");

		return gardenNodes;

	}

	private Map<Coordinate, ClientMapNode> getMyHalf() {

		TargetHalf mapSeparator = new TargetHalf(this.map);

		switch (mapPosition) {
		case UP:
			return mapSeparator.getUpPart();
		case DOWN:
			return mapSeparator.getDownPart();
		case LEFT:
			return mapSeparator.getLeftPart();
		default:
			return mapSeparator.getRightPart();
		}

	}

	@Override
	public ClientFullMap getVisitedMap() {
		return map;

	}

	@Override
	public boolean isNodeVisited(Coordinate c) {
		return this.map.getNodes().get(c).isVisited();
	}

}
