package halfmap;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import map.ClientFullMap;
import map.ClientMapNode;
import map.Coordinate;
import map.EFort;
import map.ETerrainClient;

public class ClientHalfMapGenerator {

	public final static int HALF_MAP_LENGTH = 9;
	public final static int HALF_MAP_HEIGHT = 4;
	private final static int TYPES_OF_TERRAIN = 3;

	private static Logger logger = LoggerFactory.getLogger(ClientHalfMapGenerator.class);

	public static ClientFullMap generateHalfMap() {

		Map<Coordinate, ClientMapNode> nodes = new HashMap<Coordinate, ClientMapNode>();
		boolean valid = false;

		while (!valid) {
			nodes.clear();
			for (int x = 0; x <= HALF_MAP_LENGTH; x++) {
				for (int y = 0; y <= HALF_MAP_HEIGHT; y++) {
					Coordinate currentCoordinate = new Coordinate(x, y);
					ClientMapNode currentNode = new ClientMapNode(ClientHalfMapGenerator.decideTerrain());
					nodes.put(currentCoordinate, currentNode);
				}
			}

			nodes.get(HalfMapFortPlacer.placeFort(nodes)).setFortState(EFort.MYFORT);

			HalfMapValidator validator = new HalfMapValidator(nodes);
			valid = validator.validateMap();

		}

		logger.debug("A valid HalfMap was generated.");

		return new ClientFullMap(nodes);

	}

	private static ETerrainClient decideTerrain() {

		// so that i can get more grass, the chances of grass are double.
		// done that so i can reduce the number of map regens during validation
		int terrainTypes = TYPES_OF_TERRAIN + 1;
		Random rand = new Random();
		int decision = rand.nextInt(terrainTypes);
		ETerrainClient terrainToReturn = null;

		switch (decision) {
		case 0:
			terrainToReturn = ETerrainClient.GRASS;
			break;
		case 1:
			terrainToReturn = ETerrainClient.MOUNTAIN;
			break;
		case 2:
			terrainToReturn = ETerrainClient.WATER;
			break;
		case 3:
			terrainToReturn = ETerrainClient.GRASS;
			break;

		}

		return terrainToReturn;
	}

}
