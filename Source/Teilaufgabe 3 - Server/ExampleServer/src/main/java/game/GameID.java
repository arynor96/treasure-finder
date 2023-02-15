package game;

import java.util.Objects;

public class GameID {
	
	private String gameID;

	public GameID(String gameID) {
		this.gameID = gameID;
	}
	
	public String getGameID() {
		return gameID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(gameID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameID other = (GameID) obj;
		return Objects.equals(gameID, other.gameID);
	}
	
}
