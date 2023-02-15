package map;

import java.util.HashMap;
import java.util.Map;

import rules.HalfMapSize;

// copied from Client.
public class MapFloodFill {

	// ###### TAKEN FROM https://stackoverflow.com/a/28288641 <1> ######
	// a shallow copy is not enough for my FloodFill because i modify the nodes.
	// i was not aware of this behaviour and I saw using a terrain counter that a
	// simply copy is modified during the flood fill

	private Map<Coordinate, ServerMapNode> floodNodes;

	public MapFloodFill(Map<Coordinate, ServerMapNode> nodes) {
		Map<Coordinate, ServerMapNode> floodNodes = deepCopyHashMap(nodes);
		this.floodNodes = floodNodes;
	}

	public void floodFill(Coordinate start) {

		if (!checkFloodValidPosition(start.getX(), start.getY())) {
			return;
		}

		if (floodNodes.get(start).getTerrainType() == ETerrainType.WATER) {
			return;
		}

		floodNodes.get(start).setTerrainType(ETerrainType.WATER);

		floodFill(new Coordinate(start.getX() - 1, start.getY()));
		floodFill(new Coordinate(start.getX() + 1, start.getY()));
		floodFill(new Coordinate(start.getX(), start.getY() - 1));
		floodFill(new Coordinate(start.getX(), start.getY() + 1));

	}

	private boolean checkFloodValidPosition(int x, int y) {

		if (x > HalfMapSize.HALFMAP_LENGTH || x < 0 || y > HalfMapSize.HALFMAP_HEIGHT || y < 0)
			return false;

		return true;
	}

	// ###### TAKEN FROM START <1> ######
	private Map<Coordinate, ServerMapNode> deepCopyHashMap(Map<Coordinate, ServerMapNode> toCopy) {

		Map<Coordinate, ServerMapNode> copy = new HashMap<Coordinate, ServerMapNode>();
		for (Map.Entry<Coordinate, ServerMapNode> entry : toCopy.entrySet()) {
			Coordinate newCoordinate = new Coordinate(entry.getKey().getX(), entry.getKey().getY());
			ServerMapNode newNode = new ServerMapNode(entry.getValue().getTerrainType());
			copy.put(newCoordinate, newNode);
		}
		return copy;
	}
	// ###### TAKEN FROM END <1> ######

	public Map<Coordinate, ServerMapNode> getFloodNodes() {
		return floodNodes;
	}

}
