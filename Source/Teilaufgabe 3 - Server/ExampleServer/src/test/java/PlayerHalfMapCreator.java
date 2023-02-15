import java.util.Collection;
import java.util.HashSet;

import messagesBase.messagesFromClient.ETerrain;
import messagesBase.messagesFromClient.PlayerHalfMap;
import messagesBase.messagesFromClient.PlayerHalfMapNode;
import rules.HalfMapSize;

public class PlayerHalfMapCreator {

	public static PlayerHalfMap createValidMap() {
		// valid map.
		Collection<PlayerHalfMapNode> nodesValid = new HashSet<>();
		for (int x = 0; x <= HalfMapSize.HALFMAP_LENGTH; x++) {
			for (int y = 0; y <= HalfMapSize.HALFMAP_HEIGHT; y++) {
				// int x, int y, boolean fortPresent, ETerrain terrain

				if (y == 1 && x == 6) {
					nodesValid.add(new PlayerHalfMapNode(x, y, true, ETerrain.Grass));
					continue;
				}

				if (y == 2) {
					nodesValid.add(new PlayerHalfMapNode(x, y, false, ETerrain.Mountain));
					continue;
				}

				if (y == 4 && x > 2) {
					nodesValid.add(new PlayerHalfMapNode(x, y, false, ETerrain.Mountain));
					continue;
				}

				if (y == 3 && x >= 1 && x < 9) {
					nodesValid.add(new PlayerHalfMapNode(x, y, false, ETerrain.Water));
					continue;
				} else {
					nodesValid.add(new PlayerHalfMapNode(x, y, false, ETerrain.Grass));
				}

			}
		}

		return new PlayerHalfMap("testIDforPlayer", nodesValid);
	}

	public static PlayerHalfMap createFortOnMountainMap() {
		// valid map.
		Collection<PlayerHalfMapNode> nodesValid = new HashSet<>();
		for (int x = 0; x <= HalfMapSize.HALFMAP_LENGTH; x++) {
			for (int y = 0; y <= HalfMapSize.HALFMAP_HEIGHT; y++) {
				// int x, int y, boolean fortPresent, ETerrain terrain

				if (y == 1 && x == 6) {
					nodesValid.add(new PlayerHalfMapNode(x, y, true, ETerrain.Mountain));
					continue;
				}

				if (y == 2) {
					nodesValid.add(new PlayerHalfMapNode(x, y, false, ETerrain.Mountain));
					continue;
				}

				if (y == 4 && x > 2) {
					nodesValid.add(new PlayerHalfMapNode(x, y, false, ETerrain.Mountain));
					continue;
				}

				if (y == 3 && x >= 1 && x < 9) {
					nodesValid.add(new PlayerHalfMapNode(x, y, false, ETerrain.Water));
					continue;
				} else {
					nodesValid.add(new PlayerHalfMapNode(x, y, false, ETerrain.Grass));
				}

			}
		}

		return new PlayerHalfMap("testIDforPlayer", nodesValid);
	}

	public static PlayerHalfMap createNotEnoughGrass() {
		// valid map.
		Collection<PlayerHalfMapNode> nodesValid = new HashSet<>();
		for (int x = 0; x <= HalfMapSize.HALFMAP_LENGTH; x++) {
			for (int y = 0; y <= HalfMapSize.HALFMAP_HEIGHT; y++) {
				// int x, int y, boolean fortPresent, ETerrain terrain

				if (y == 1 && x == 6) {
					nodesValid.add(new PlayerHalfMapNode(x, y, true, ETerrain.Grass));
					continue;
				}

				if (y == 2) {
					nodesValid.add(new PlayerHalfMapNode(x, y, false, ETerrain.Mountain));
					continue;
				}

				if (y == 4 && x > 2) {
					nodesValid.add(new PlayerHalfMapNode(x, y, false, ETerrain.Mountain));
					continue;
				}

				if (y == 3 && x >= 1 && x < 9) {
					nodesValid.add(new PlayerHalfMapNode(x, y, false, ETerrain.Water));
					continue;
				} else {
					nodesValid.add(new PlayerHalfMapNode(x, y, false, ETerrain.Mountain));
				}

			}
		}

		return new PlayerHalfMap("testIDforPlayer", nodesValid);
	}

	public static PlayerHalfMap createNotEnoughWater() {
		// valid map.
		Collection<PlayerHalfMapNode> nodesValid = new HashSet<>();
		for (int x = 0; x <= HalfMapSize.HALFMAP_LENGTH; x++) {
			for (int y = 0; y <= HalfMapSize.HALFMAP_HEIGHT; y++) {
				// int x, int y, boolean fortPresent, ETerrain terrain

				if (y == 1 && x == 6) {
					nodesValid.add(new PlayerHalfMapNode(x, y, true, ETerrain.Grass));
					continue;
				}

				if (y == 2) {
					nodesValid.add(new PlayerHalfMapNode(x, y, false, ETerrain.Mountain));
					continue;
				}

				if (y == 4 && x > 2) {
					nodesValid.add(new PlayerHalfMapNode(x, y, false, ETerrain.Mountain));
					continue;
				}

				if (y == 3 && x >= 6) {
					nodesValid.add(new PlayerHalfMapNode(x, y, false, ETerrain.Water));
					continue;
				} else {
					nodesValid.add(new PlayerHalfMapNode(x, y, false, ETerrain.Grass));
				}

			}
		}

		return new PlayerHalfMap("testIDforPlayer", nodesValid);
	}

	public static PlayerHalfMap createNotEnoughMountainMap() {
		// valid map.
		Collection<PlayerHalfMapNode> nodesValid = new HashSet<>();
		for (int x = 0; x <= HalfMapSize.HALFMAP_LENGTH; x++) {
			for (int y = 0; y <= HalfMapSize.HALFMAP_HEIGHT; y++) {
				// int x, int y, boolean fortPresent, ETerrain terrain

				if (x == 6 && y == 1) {
					nodesValid.add(new PlayerHalfMapNode(x, y, true, ETerrain.Grass));
					continue;
				}

				if (y == 4 && x > 5) {
					nodesValid.add(new PlayerHalfMapNode(x, y, false, ETerrain.Mountain));
					continue;
				}

				if (y == 3 && x >= 1 && x < 9) {
					nodesValid.add(new PlayerHalfMapNode(x, y, false, ETerrain.Water));
					continue;
				} else {
					nodesValid.add(new PlayerHalfMapNode(x, y, false, ETerrain.Grass));
				}

			}
		}

		return new PlayerHalfMap("testIDforPlayer", nodesValid);
	}

	public static PlayerHalfMap createTooSmallMap() {
		// valid map.
		Collection<PlayerHalfMapNode> nodesValid = new HashSet<>();
		for (int x = 0; x <= HalfMapSize.HALFMAP_LENGTH - 1; x++) {
			for (int y = 0; y <= HalfMapSize.HALFMAP_HEIGHT; y++) {
				// int x, int y, boolean fortPresent, ETerrain terrain

				if (x == 6 && y == 1) {
					nodesValid.add(new PlayerHalfMapNode(x, y, true, ETerrain.Grass));
					continue;
				}

				if (y == 2) {
					nodesValid.add(new PlayerHalfMapNode(x, y, false, ETerrain.Mountain));
					continue;
				}

				if (y == 4 && x > 2) {
					nodesValid.add(new PlayerHalfMapNode(x, y, false, ETerrain.Mountain));
					continue;
				}

				if (y == 3 && x >= 1 && x < 9) {
					nodesValid.add(new PlayerHalfMapNode(x, y, false, ETerrain.Water));
					continue;
				} else {
					nodesValid.add(new PlayerHalfMapNode(x, y, false, ETerrain.Grass));
				}

			}
		}

		return new PlayerHalfMap("testIDforPlayer", nodesValid);
	}

	public static PlayerHalfMap createTooMuchWaterEdge() {

		Collection<PlayerHalfMapNode> nodesInvalidEdge = new HashSet<>();
		for (int x = 0; x <= HalfMapSize.HALFMAP_LENGTH; x++) {
			for (int y = 0; y <= HalfMapSize.HALFMAP_HEIGHT; y++) {

				if (x == 6 && y == 1) {
					nodesInvalidEdge.add(new PlayerHalfMapNode(x, y, true, ETerrain.Grass));
					continue;
				}

				if (y == 2) {
					nodesInvalidEdge.add(new PlayerHalfMapNode(x, y, false, ETerrain.Mountain));
					continue;
				}

				if (y == 4 && x > 2) {
					nodesInvalidEdge.add(new PlayerHalfMapNode(x, y, false, ETerrain.Mountain));
					continue;
				}

				if (y == 0 && x >= 1 && x < 9) {
					nodesInvalidEdge.add(new PlayerHalfMapNode(x, y, false, ETerrain.Water));
					continue;
				} else {
					nodesInvalidEdge.add(new PlayerHalfMapNode(x, y, false, ETerrain.Grass));
				}

			}
		}
		return new PlayerHalfMap("testIDPlayerInvalidMap", nodesInvalidEdge);

	}

	public static PlayerHalfMap createNoFortMap() {
		// valid map.
		Collection<PlayerHalfMapNode> nodesValid = new HashSet<>();
		for (int x = 0; x <= HalfMapSize.HALFMAP_LENGTH; x++) {
			for (int y = 0; y <= HalfMapSize.HALFMAP_HEIGHT; y++) {
				// int x, int y, boolean fortPresent, ETerrain terrain

				if (y == 2) {
					nodesValid.add(new PlayerHalfMapNode(x, y, false, ETerrain.Mountain));
					continue;
				}

				if (y == 4 && x > 2) {
					nodesValid.add(new PlayerHalfMapNode(x, y, false, ETerrain.Mountain));
					continue;
				}

				if (y == 3 && x >= 1 && x < 9) {
					nodesValid.add(new PlayerHalfMapNode(x, y, false, ETerrain.Water));
					continue;
				} else {
					nodesValid.add(new PlayerHalfMapNode(x, y, false, ETerrain.Grass));
				}

			}
		}

		return new PlayerHalfMap("testIDforPlayer", nodesValid);
	}

	public static PlayerHalfMap createIslandMap() {
		// valid map.
		Collection<PlayerHalfMapNode> nodesValid = new HashSet<>();
		for (int x = 0; x <= HalfMapSize.HALFMAP_LENGTH; x++) {
			for (int y = 0; y <= HalfMapSize.HALFMAP_HEIGHT; y++) {
				// int x, int y, boolean fortPresent, ETerrain terrain

				if (y == 1 && x == 6) {
					nodesValid.add(new PlayerHalfMapNode(x, y, true, ETerrain.Grass));
					continue;
				}

				if (y == 1 && x == 5) {
					nodesValid.add(new PlayerHalfMapNode(x, y, false, ETerrain.Water));
					continue;
				}

				if (y == 2 && x == 4 || y == 2 && x == 6) {
					nodesValid.add(new PlayerHalfMapNode(x, y, false, ETerrain.Water));
					continue;
				}

				if (y == 2) {
					nodesValid.add(new PlayerHalfMapNode(x, y, false, ETerrain.Mountain));
					continue;
				}

				if (y == 4 && x > 2) {
					nodesValid.add(new PlayerHalfMapNode(x, y, false, ETerrain.Mountain));
					continue;
				}

				if (y == 3 && x >= 1 && x < 9) {
					nodesValid.add(new PlayerHalfMapNode(x, y, false, ETerrain.Water));
					continue;
				} else {
					nodesValid.add(new PlayerHalfMapNode(x, y, false, ETerrain.Grass));
				}

			}
		}

		return new PlayerHalfMap("testIDforPlayer", nodesValid);
	}

}
