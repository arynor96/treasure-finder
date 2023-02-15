package map;

import java.util.HashMap;
import java.util.Map;

public class TargetHalf {

	private ClientFullMap map;

	public TargetHalf(ClientFullMap map) {
		this.map = map;
	}

	public Map<Coordinate, ClientMapNode> getLeftPart() {
		Map<Coordinate, ClientMapNode> nodesOnLeftPart = new HashMap<Coordinate, ClientMapNode>();
		for (int x = 0; x <= 9; x++) {
			for (int y = 0; y <= 4; y++) {
				Coordinate current = new Coordinate(x, y);
				nodesOnLeftPart.put(current, map.getNodes().get(current));
			}
		}

		return nodesOnLeftPart;
	}

	public Map<Coordinate, ClientMapNode> getUpPart() {
		Map<Coordinate, ClientMapNode> nodesOnUpperPart = new HashMap<Coordinate, ClientMapNode>();
		for (int x = 0; x <= 9; x++) {
			for (int y = 0; y <= 4; y++) {
				Coordinate current = new Coordinate(x, y);
				nodesOnUpperPart.put(current, map.getNodes().get(current));
			}
		}
		return nodesOnUpperPart;
	}

	public Map<Coordinate, ClientMapNode> getRightPart() {
		Map<Coordinate, ClientMapNode> nodesOnRightPart = new HashMap<Coordinate, ClientMapNode>();
		for (int x = 10; x <= 19; x++) {
			for (int y = 0; y <= 4; y++) {
				Coordinate current = new Coordinate(x, y);
				nodesOnRightPart.put(current, map.getNodes().get(current));
			}
		}

		return nodesOnRightPart;
	}

	public Map<Coordinate, ClientMapNode> getDownPart() {
		Map<Coordinate, ClientMapNode> nodesOnBottomPart = new HashMap<Coordinate, ClientMapNode>();
		for (int x = 0; x <= 9; x++) {
			for (int y = 5; y <= 9; y++) {
				Coordinate current = new Coordinate(x, y);
				nodesOnBottomPart.put(current, map.getNodes().get(current));
			}
		}
		return nodesOnBottomPart;
	}

}
