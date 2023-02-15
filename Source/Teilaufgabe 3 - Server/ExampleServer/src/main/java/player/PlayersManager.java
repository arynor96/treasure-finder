package player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayersManager {

	// private Map<Player, PlayerData> players = new HashMap<>();
	private List<Player> players = new ArrayList<Player>();
	private Player nextPlayerTurn = new Player("noturnyet");
	private Player winner = new Player("nowinneryet");

	public PlayersManager() {

	}

	public String addPlayer(PlayerData playerDetails) {
		Player newPlayer = new Player(playerDetails);
		players.add(newPlayer);
		return newPlayer.getPlayerID();
	}

	public boolean isPlayerRegistered(String playerID) {
		Player playerToFind = new Player(playerID);

		for (Player player : players) {
			if (player.equals(playerToFind))
				return true;
		}

		return false;
	}

	public boolean isGameFull() {
		return players.size() == 2;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public Player getnextPlayerTurn() {
		return nextPlayerTurn;
	}

	public void setnextPlayerTurn(Player nextPlayerTurn) {
		this.nextPlayerTurn = nextPlayerTurn;
	}

	public Player getWinner() {
		return winner;
	}

	public Player getEnemyOf(Player player) {
		Player enemy = player;
		for (Player playerEntry : players) {
			if (!playerEntry.equals(player))
				enemy = playerEntry;
		}
		return enemy;

	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}

	public boolean isNextPlayerTurn(Player nextPlayerTurn) {
		return this.nextPlayerTurn.equals(nextPlayerTurn);
	}

	public void switchTurn() {
		nextPlayerTurn = getEnemyOf(nextPlayerTurn);
	}

	public void decideRandomTurn() {
		Random rand = new Random();
		int firstPlayer = rand.nextInt(players.size());
		nextPlayerTurn = players.get(firstPlayer);
	}

}
