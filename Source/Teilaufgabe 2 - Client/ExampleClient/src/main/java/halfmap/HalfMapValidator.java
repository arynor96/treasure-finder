package halfmap;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import map.ClientMapNode;
import map.Coordinate;
import map.EFort;
import map.ETerrainClient;

public class HalfMapValidator {

	private static Logger logger = LoggerFactory.getLogger(HalfMapValidator.class);

	private final int MINIMUM_GRASS_FIELDS = 24;
	private final int MINIMUM_WATER_FIELDS = 7;
	// private final int MINIMUM_MOUNTAIN_FIELDS = 5; //original rule.
	private final int MINIMUM_MOUNTAIN_FIELDS = 15;

	private final int MAXIMUM_WATER_SHORTEDGE = 2;
	private final int MAXIMUM_WATER_LONGEDGE = 4;

	private Map<Coordinate, ClientMapNode> nodes;

	public HalfMapValidator(Map<Coordinate, ClientMapNode> nodes) {
		this.nodes = nodes;
	}

	public boolean validateMap() {
		if (checkNumberTerrainTypes() && checkWaterOnBorder() && checkFortExists() && checkForIsland() && checkSize())
			return true;
		else
			return false;
	}

	private boolean checkSize() {
		if (nodes.size() != (ClientHalfMapGenerator.HALF_MAP_LENGTH + 1) * (ClientHalfMapGenerator.HALF_MAP_HEIGHT + 1))
			return false;
		else
			return true;
	}

	private boolean checkNumberTerrainTypes() {
		int grass = MINIMUM_GRASS_FIELDS;
		int water = MINIMUM_WATER_FIELDS;
		int mountain = MINIMUM_MOUNTAIN_FIELDS;

		for (ClientMapNode node : nodes.values()) {
			switch (node.getTerrainType()) {
			case GRASS:
				grass--;
				break;
			case WATER:
				water--;
				break;
			case MOUNTAIN:
				mountain--;
				break;
			}
		}

		if (grass < 0 && water < 0 && mountain < 0) {
			return true;
		}

		else {
			TerrainTypeCounter.countTerrains(nodes);
			return false;
		}

	}

	private boolean checkWaterOnBorder() {

		int waterShortEdge = MAXIMUM_WATER_SHORTEDGE;
		int waterShortLeftEdge = MAXIMUM_WATER_SHORTEDGE;
		int waterLongEdge = MAXIMUM_WATER_LONGEDGE;
		int waterLongTopEdge = MAXIMUM_WATER_LONGEDGE;

		// short right edge
		for (int y = 0; y <= ClientHalfMapGenerator.HALF_MAP_HEIGHT; y++) {
			if (nodes.get(new Coordinate(9, y)).getTerrainType() == ETerrainClient.WATER)
				waterShortEdge--;
		}

		if (waterShortEdge < 0)
			return false;

		// short left edge
		for (int y = 0; y <= ClientHalfMapGenerator.HALF_MAP_HEIGHT; y++) {
			if (nodes.get(new Coordinate(0, y)).getTerrainType() == ETerrainClient.WATER)
				waterShortLeftEdge--;
		}

		if (waterShortLeftEdge < 0)
			return false;

		// long bottom edge
		for (int x = 0; x <= ClientHalfMapGenerator.HALF_MAP_LENGTH; x++) {
			if (nodes.get(new Coordinate(x, 4)).getTerrainType() == ETerrainClient.WATER)
				waterLongEdge--;
		}

		if (waterLongEdge < 0)
			return false;

		// long top edge
		for (int x = 0; x <= ClientHalfMapGenerator.HALF_MAP_LENGTH; x++) {
			if (nodes.get(new Coordinate(x, 0)).getTerrainType() == ETerrainClient.WATER)
				waterLongTopEdge--;
		}

		if (waterLongTopEdge < 0)
			return false;

		return true;

	}

	private boolean checkFortExists() {
		for (ClientMapNode node : nodes.values()) {
			if (node.getFortState() == EFort.MYFORT && node.getTerrainType() == ETerrainClient.GRASS) {
				return true;
			}

		}
		logger.debug("Fort was not placed on a valid position.");
		return false;
	}

	private boolean checkForIsland() {

		Coordinate firstGrassNode = findFirstGrassNode();
		if (firstGrassNode == null) {
			logger.debug("There is no grass node on this map.");
			return false;
		}

		HalfMapFloodFill floodFiller = new HalfMapFloodFill(nodes);
		floodFiller.floodFill(firstGrassNode);
		Map<Coordinate, ClientMapNode> floodNodes = floodFiller.getFloodNodes();

		// if in the returning nodes any grass or mountain is found, we have an island
		for (ClientMapNode node : floodNodes.values()) {
			if (node.getTerrainType() == ETerrainClient.GRASS) {
				logger.debug("Found island, rebuilding.");
				return false;
			}

			if (node.getTerrainType() == ETerrainClient.MOUNTAIN) {
				logger.debug("Found island, rebuilding.");
				return false;
			}

		}
		return true;

	}

	// required for flood fill as a start point
	private Coordinate findFirstGrassNode() {

		Coordinate first = nodes.entrySet().stream()
				.filter(node -> node.getValue().getTerrainType() == ETerrainClient.GRASS).map(Map.Entry::getKey)
				.findFirst().orElse(null);

		return first;
	}

}
