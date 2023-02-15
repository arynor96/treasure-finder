package gamestats;

import java.time.Instant;
import java.util.UUID;

public class GameStatsManager {

	private int turnNumber = 0;
	private EGameStatus gameStatus = EGameStatus.WAITINGPLAYERS;
	private String gameStatsID = UUID.randomUUID().toString();
	private Instant creationTime = Instant.now();
	private Instant lastAction = Instant.now();

	public GameStatsManager() {

	}

	public void newGameStatsID() {
		this.gameStatsID = UUID.randomUUID().toString();
	}

	public EGameStatus getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(EGameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}

	public int getTurnNumber() {
		return turnNumber;
	}

	public void setTurnNumber(int turnNumber) {
		this.turnNumber = turnNumber;
	}

	public String getGameStatsID() {
		return gameStatsID;
	}

	public Instant getCreationTime() {
		return creationTime;
	}

	public Instant getLastAction() {
		return lastAction;
	}

	public void setLastAction(Instant lastAction) {
		this.lastAction = lastAction;
	}

}
