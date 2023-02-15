package game;

import java.time.Instant;

import gamestats.EGameStatus;
import gamestats.GameStats;
import gamestats.GameStatsManager;
import map.MapManager;
import map.ServerFullMap;
import player.Player;
import player.PlayerData;
import player.PlayersManager;

public class GameManager {

	private PlayersManager players = new PlayersManager();
	private MapManager gameMap = new MapManager();
	private GameStatsManager gameStatsManager = new GameStatsManager();

	public GameManager() {

	}

	public String addPlayer(PlayerData playerDetails) {
		String newPlayerID = players.addPlayer(playerDetails);
		this.gameStatsManager.newGameStatsID();
		if (isGameFull() && gameStatsManager.getGameStatus() != EGameStatus.WAITINGMAPS) {
			gameStatsManager.setGameStatus(EGameStatus.WAITINGMAPS);
			players.decideRandomTurn();
		}
		return newPlayerID;
	}

	public void addHalfMap(ServerFullMap halfMap) {
		setLastActivity();
		gameMap.addHalfMap(halfMap);
		players.switchTurn();
		this.gameStatsManager.newGameStatsID();

		if (gameMap.isFullMapCreated()) {
			gameStatsManager.setGameStatus(EGameStatus.RUNNING);
		}

	}

	public Instant getCreationTime() {
		return gameStatsManager.getCreationTime();
	}

	public Instant getLastActivity() {
		return gameStatsManager.getLastAction();
	}

	public ServerFullMap getMap() {
		return gameMap.getFullMap();
	}

	public void setWinner(Player player) {
		gameStatsManager.setGameStatus(EGameStatus.FINISHED);
		players.setWinner(player);
	}

	public Player getEnemy(Player player) {
		return players.getEnemyOf(player);
	}

	public Player getNextPlayerTurn() {
		return players.getnextPlayerTurn();
	}

	public boolean isGameFull() {
		return players.isGameFull();
	}

	public boolean isPlayerRegistered(String playerID) {
		return players.isPlayerRegistered(playerID);
	}

	public EGameStatus getGameStatus() {
		return gameStatsManager.getGameStatus();
	}

	public void setLastActivity() {
		gameStatsManager.setLastAction(Instant.now());
	}

	public boolean isGameFinished() {
		return gameStatsManager.getGameStatus() == EGameStatus.FINISHED;
	}

	public GameStats createGameStatus(Player player) {
		return new GameStats(this.gameStatsManager, this.players, this.gameMap.getFullMap());

	}

}
