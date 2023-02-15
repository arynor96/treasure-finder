package server.main.converters;

import map.EFort;
import map.ETerrainType;
import map.ServerMapNode;
import messagesBase.messagesFromClient.ETerrain;
import messagesBase.messagesFromClient.PlayerHalfMapNode;
import player.Player;

public class MapNodeConverter {

	public static ServerMapNode toLocal(PlayerHalfMapNode node, Player creator) {
		ETerrain terrain = node.getTerrain();
		ETerrainType terrainLocal = convertToLocalTerrain(terrain);
		boolean fortPresent = node.isFortPresent();
		if (fortPresent)
			return new ServerMapNode(terrainLocal, EFort.FORT, creator);
		return new ServerMapNode(terrainLocal, creator);
	}

	private static ETerrainType convertToLocalTerrain(ETerrain terrain) {
		switch (terrain) {
		case Grass:
			return ETerrainType.GRASS;
		case Water:
			return ETerrainType.WATER;
		default:
			return ETerrainType.MOUNTAIN;

		}

	}

}
