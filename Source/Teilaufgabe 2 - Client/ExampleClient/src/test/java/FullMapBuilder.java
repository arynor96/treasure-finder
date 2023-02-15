import java.util.Map;
import java.util.Set;

import map.ClientFullMap;
import map.ClientMapNode;
import map.Coordinate;
import map.EFort;
import map.EPlayer;
import map.ETerrainClient;

public class FullMapBuilder {

	// My Position is always on the right part of the map.
	static ClientFullMap mergeMapsTreasureVisible(ClientFullMap halfMap1, ClientFullMap halfMap2) {

		Map<Coordinate, ClientMapNode> fullMapNodes = halfMap1.getNodes();

		Set<Map.Entry<Coordinate, ClientMapNode>> nodes2 = halfMap2.getNodes().entrySet();

		for (Map.Entry<Coordinate, ClientMapNode> entry : nodes2) {
			// this line places my position.
			if (entry.getKey().getX() == 4 && entry.getKey().getY() == 2) {
				fullMapNodes.put(new Coordinate(entry.getKey().getX() + 10, entry.getKey().getY()),
						new ClientMapNode(ETerrainClient.GRASS, EFort.MYFORT, EPlayer.MY, false));
				continue;
			}
			// this line places the treasure.
			if (entry.getKey().getX() == 5 && entry.getKey().getY() == 2) {
				fullMapNodes.put(new Coordinate(entry.getKey().getX() + 10, entry.getKey().getY()),
						new ClientMapNode(ETerrainClient.GRASS, EFort.NOFORT, entry.getValue().getPlayerState(), true));
				continue;
			}
			fullMapNodes.put(new Coordinate(entry.getKey().getX() + 10, entry.getKey().getY()), new ClientMapNode(
					entry.getValue().getTerrainType(), EFort.NOFORT, entry.getValue().getPlayerState(), false));

		}

		ClientFullMap fullMap = new ClientFullMap(fullMapNodes);

		return fullMap;

	}

	static ClientFullMap mergeMapsTreasureNotVisible(ClientFullMap halfMap1, ClientFullMap halfMap2) {

		Map<Coordinate, ClientMapNode> fullMapNodes = halfMap1.getNodes();

		Set<Map.Entry<Coordinate, ClientMapNode>> nodes2 = halfMap2.getNodes().entrySet();

		for (Map.Entry<Coordinate, ClientMapNode> entry : nodes2) {
			// this line places my position.
			if (entry.getKey().getX() == 4 && entry.getKey().getY() == 2) {
				fullMapNodes.put(new Coordinate(entry.getKey().getX() + 10, entry.getKey().getY()),
						new ClientMapNode(ETerrainClient.GRASS, EFort.MYFORT, EPlayer.MY, false));
				continue;
			}

			fullMapNodes.put(new Coordinate(entry.getKey().getX() + 10, entry.getKey().getY()), new ClientMapNode(
					entry.getValue().getTerrainType(), EFort.NOFORT, entry.getValue().getPlayerState(), false));

		}

		ClientFullMap fullMap = new ClientFullMap(fullMapNodes);

		return fullMap;

	}

}
