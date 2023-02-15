package halfmap;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import map.ClientMapNode;
import map.Coordinate;

public class TerrainTypeCounter {

	private static Logger logger = LoggerFactory.getLogger(TerrainTypeCounter.class);

	public static void countTerrains(Map<Coordinate, ClientMapNode> nodes) {

		int grass = 0;
		int water = 0;
		int mountain = 0;

		for (ClientMapNode node : nodes.values()) {
			switch (node.getTerrainType()) {
			case GRASS:
				grass++;
				break;
			case WATER:
				water++;
				break;
			case MOUNTAIN:
				mountain++;
				break;
			}
		}
		logger.debug("Not enough terrain types: " + "GRASS: " + grass + " WATER: " + water + " MOUNTAIN: " + mountain);

	}

}
