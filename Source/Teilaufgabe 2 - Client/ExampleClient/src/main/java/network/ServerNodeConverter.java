package network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import map.ClientMapNode;
import map.EFort;
import map.EPlayer;
import map.ETerrainClient;
import messagesBase.messagesFromClient.ETerrain;
import messagesBase.messagesFromServer.EFortState;
import messagesBase.messagesFromServer.EPlayerPositionState;
import messagesBase.messagesFromServer.ETreasureState;
import messagesBase.messagesFromServer.FullMapNode;

public class ServerNodeConverter {

	private static Logger logger = LoggerFactory.getLogger(ServerNodeConverter.class);

	public static ClientMapNode convertToLocalNode(FullMapNode node) {
		return new ClientMapNode(convertToLocalTerrain(node.getTerrain()), convertToLocalFort(node.getFortState()),
				convertToLocalPlayer(node.getPlayerPositionState()), hasMyTreasure(node.getTreasureState()));
	}

	private static ETerrainClient convertToLocalTerrain(ETerrain terrain) {
		switch (terrain) {
		case Grass:
			return ETerrainClient.GRASS;
		case Water:
			return ETerrainClient.WATER;
		default:
			return ETerrainClient.MOUNTAIN;

		}

	}

	private static EFort convertToLocalFort(EFortState fort) {
		switch (fort) {
		case EnemyFortPresent:
			return EFort.ENEMYFORT;
		case MyFortPresent:
			return EFort.MYFORT;
		default:
			return EFort.NOFORT;
		}
	}

	private static EPlayer convertToLocalPlayer(EPlayerPositionState state) {
		switch (state) {
		case BothPlayerPosition:
			return EPlayer.BOTH;
		case EnemyPlayerPosition:
			return EPlayer.ENEMY;
		case MyPlayerPosition:
			return EPlayer.MY;
		default:
			return EPlayer.NONE;

		}
	}

	private static boolean hasMyTreasure(ETreasureState state) {
		switch (state) {
		case MyTreasureIsPresent:
			logger.debug("Treasure is visible in network");
			return true;
		default:
			return false;
		}

	}

}
