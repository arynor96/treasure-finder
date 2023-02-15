package server.main.converters;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import gamestats.GameStats;
import map.Coordinate;
import map.EFort;
import map.ETerrainType;
import map.ServerFullMap;
import map.ServerMapNode;
import messagesBase.messagesFromClient.ETerrain;
import messagesBase.messagesFromServer.EFortState;
import messagesBase.messagesFromServer.EPlayerGameState;
import messagesBase.messagesFromServer.EPlayerPositionState;
import messagesBase.messagesFromServer.ETreasureState;
import messagesBase.messagesFromServer.FullMap;
import messagesBase.messagesFromServer.FullMapNode;
import messagesBase.messagesFromServer.GameState;
import messagesBase.messagesFromServer.PlayerState;
import player.Player;
import player.PlayerData;

public class GameStateConverter {

	public static GameState toNetwork(GameStats gameStats, Player player) {
		if (gameStats.getFullMap().getNodes().size() == 0) {
			return new GameState(convertPlayers(gameStats, player), gameStats.getGameStatsID());
		} else {
			return new GameState(convertMap(gameStats.getFullMap(), player), convertPlayers(gameStats, player),
					gameStats.getGameStatsID());
		}

	}

	private static Set<PlayerState> convertPlayers(GameStats gameStats, Player player) {
		Set<PlayerState> convertedPlayers = new HashSet<PlayerState>();

		List<Player> players = gameStats.getPlayers();
		for (Player entry : players) {
			String currentID = entry.getPlayerID();
			if (!currentID.equals(player.getPlayerID())) {
				currentID = "fakeID";
			}
			PlayerData currentData = entry.getPlayerData();
			EPlayerGameState currentPlayerState;

			if (!gameStats.gameFinished()) {
				if (currentID == gameStats.getNextTurnPlayer().getPlayerID()) {
					currentPlayerState = EPlayerGameState.MustAct;
				} else {
					currentPlayerState = EPlayerGameState.MustWait;
				}
			} else {
				if (currentID == gameStats.getWinner().getPlayerID()) {
					currentPlayerState = EPlayerGameState.Won;
				} else {
					currentPlayerState = EPlayerGameState.Lost;
				}

			}

			PlayerState convertedPlayer = new PlayerState(currentData.getFirstName().getFirstName(),
					currentData.getLastName().getLastName(), currentData.getuAccount().getuAccount(),
					currentPlayerState, PlayerIDConverter.toNetwork(currentID), false);

			convertedPlayers.add(convertedPlayer);
		}

		return convertedPlayers;
	}

	private static Optional<FullMap> convertMap(ServerFullMap localMap, Player player) {
		Set<FullMapNode> convertedNodes = new HashSet<FullMapNode>();

		Map<Coordinate, ServerMapNode> nodes = localMap.getNodes();
		Set<Map.Entry<Coordinate, ServerMapNode>> mapNodes = nodes.entrySet();
		for (Map.Entry<Coordinate, ServerMapNode> entry : mapNodes) {
			Player creator = entry.getValue().getCreator();
			int x = entry.getKey().getX();
			int y = entry.getKey().getY();
			ETerrain terrainType = convertTerrain(entry.getValue().getTerrainType());

			EPlayerPositionState positionState = EPlayerPositionState.NoPlayerPresent;
			if (x == 1 && y == 1) {
				positionState = EPlayerPositionState.EnemyPlayerPosition;
			}

			ETreasureState treasureState = ETreasureState.NoOrUnknownTreasureState;

			EFortState fortState = EFortState.NoOrUnknownFortState;
			if (entry.getValue().getFortState() == EFort.FORT && player.equals(creator)) {
				fortState = EFortState.MyFortPresent;
				positionState = EPlayerPositionState.MyPlayerPosition;
			}
			convertedNodes.add(new FullMapNode(terrainType, positionState, treasureState, fortState, x, y));

		}

		return Optional.of(new FullMap(convertedNodes));
	}

	private static ETerrain convertTerrain(ETerrainType terrain) {
		switch (terrain) {
		case GRASS:
			return ETerrain.Grass;
		case WATER:
			return ETerrain.Water;
		default:
			return ETerrain.Mountain;

		}

	}

	// for future usage.
	private static EFortState convertFort(EFort fort) {
		switch (fort) {
		case NOFORT:
			return EFortState.NoOrUnknownFortState;

		}
		return null;

	}

}
