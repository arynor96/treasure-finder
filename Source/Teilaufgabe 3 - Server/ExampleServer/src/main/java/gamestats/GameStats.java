package gamestats;

import java.util.List;

import map.ServerFullMap;
import player.Player;
import player.PlayersManager;

public class GameStats {

	private GameStatsManager gameStats;
	private PlayersManager players;
	private ServerFullMap fullMap;

	public GameStats(GameStatsManager gameStats, PlayersManager players, ServerFullMap fullMap) {
		this.gameStats = gameStats;
		this.players = players;
		this.fullMap = fullMap;

	}

	public String getGameStatsID() {
		return this.gameStats.getGameStatsID();
	}

	public ServerFullMap getFullMap() {
		return this.fullMap;
	}

	public PlayersManager getPlayersManager() {
		return this.players;
	}

	public List<Player> getPlayers() {
		return this.players.getPlayers();
	}

	public Player getWinner() {
		return this.players.getWinner();
	}

	public Player getNextTurnPlayer() {
		return this.players.getnextPlayerTurn();
	}

	public boolean gameFinished() {
		return gameStats.getGameStatus() == EGameStatus.FINISHED;
	}

}
