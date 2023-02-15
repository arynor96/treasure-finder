package map;

public class MapManager {

	private ServerFullMap fullMap = new ServerFullMap();

	public MapManager() {

	}

	public void addHalfMap(ServerFullMap halfMap) {
		if (fullMap.getNodes().size() == 0) {
			fullMap.setNodes(halfMap.getNodes());
			return;
		}

		if (fullMap.getNodes().size() == 100) {
			// After implementing get state, this should no longer happen.
			System.out.println("nonono...");
		} else {
			fullMap = ServerMapBuilder.mergeMaps(fullMap, halfMap);
			// ---- TreasurePlacer here! ----
			// this part is not implemented, returns the same map.
			fullMap = TreasurePlacer.placeTreasure(fullMap);
		}
	}

	public boolean isFullMapCreated() {
		return fullMap.getNodes().size() == 100;
	}

	public ServerFullMap getFullMap() {
		return fullMap;
	}

}
