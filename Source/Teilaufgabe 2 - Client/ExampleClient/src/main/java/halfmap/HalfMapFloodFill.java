package halfmap;

import java.util.HashMap;
import java.util.Map;

import map.ClientMapNode;
import map.Coordinate;
import map.ETerrainClient;

public class HalfMapFloodFill {

	// ###### TAKEN FROM https://stackoverflow.com/a/28288641 <1> ######
	// a shallow copy is not enough for my FloodFill because i modify the nodes.
	// i was not aware of this behaviour and I saw using a terrain counter that a
	// simply copy is modified during the flood fill

	private Map<Coordinate, ClientMapNode> floodNodes;

	public HalfMapFloodFill(Map<Coordinate, ClientMapNode> nodes) {
		Map<Coordinate, ClientMapNode> floodNodes = deepCopyHashMap(nodes);
		this.floodNodes = floodNodes;
	}

	public void floodFill(Coordinate start) {

		if (!checkFloodValidPosition(start.getX(), start.getY())) {
			return;
		}

		if (floodNodes.get(start).getTerrainType() == ETerrainClient.WATER) {
			return;
		}

		floodNodes.get(start).setTerrainType(ETerrainClient.WATER);

		floodFill(new Coordinate(start.getX() - 1, start.getY()));
		floodFill(new Coordinate(start.getX() + 1, start.getY()));
		floodFill(new Coordinate(start.getX(), start.getY() - 1));
		floodFill(new Coordinate(start.getX(), start.getY() + 1));

	}

	private boolean checkFloodValidPosition(int x, int y) {

		if (x > ClientHalfMapGenerator.HALF_MAP_LENGTH || x < 0 || y > ClientHalfMapGenerator.HALF_MAP_HEIGHT || y < 0)
			return false;

		return true;
	}

	// ###### TAKEN FROM START <1> ######
	private Map<Coordinate, ClientMapNode> deepCopyHashMap(Map<Coordinate, ClientMapNode> toCopy) {

		Map<Coordinate, ClientMapNode> copy = new HashMap<Coordinate, ClientMapNode>();
		for (Map.Entry<Coordinate, ClientMapNode> entry : toCopy.entrySet()) {
			Coordinate newCoordinate = new Coordinate(entry.getKey().getX(), entry.getKey().getY());
			ClientMapNode newNode = new ClientMapNode(entry.getValue().getTerrainType());
			copy.put(newCoordinate, newNode);
		}
		return copy;
	}
	// ###### TAKEN FROM END <1> ######

	public Map<Coordinate, ClientMapNode> getFloodNodes() {
		return floodNodes;
	}

}
