package player;

import java.util.Objects;

import ServerAdministration.PlayerIDGenerator;

public class Player {

	private String playerID = "";
	private PlayerData playerData;

	public Player() {
		this.playerID = PlayerIDGenerator.generate();
	}

	public Player(PlayerData playerData) {
		this.playerID = PlayerIDGenerator.generate();
		this.playerData = playerData;
	}

	public Player(String playerID) {
		this.playerID = playerID;
	}

	public String getPlayerID() {
		return playerID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(playerID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		return Objects.equals(playerID, other.playerID);
	}

	public PlayerData getPlayerData() {
		return playerData;
	}

}
