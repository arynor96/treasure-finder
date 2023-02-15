package ServerAdministration;

import java.time.Duration;
import java.time.Instant;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import game.GameID;
import game.GameManager;
import gamestats.EGameStatus;

public class GamesSanitizer {

	public static Map<GameID, GameManager> sanitizeGames(Map<GameID, GameManager> games) {

		int initialSize = games.size();
		Instant currentTime = Instant.now();

		Map<GameID, GameManager> gamesToReturn = games;
		Set<Map.Entry<GameID, GameManager>> gamesSet = games.entrySet();

		for (Iterator<Map.Entry<GameID, GameManager>> iterator = gamesSet.iterator(); iterator.hasNext();) {
			var entry = iterator.next();
			if (entry.getValue().isGameFinished()) {
				iterator.remove();
				continue;
			}

			Instant currentGameCreationTime = entry.getValue().getCreationTime();

			long elapsedTime = Duration.between(currentTime, currentGameCreationTime).toMinutes();
			if (elapsedTime > 10) {
				iterator.remove();
				continue;
			}

			if (entry.getValue().getGameStatus() == EGameStatus.RUNNING) {
				Instant lastActivity = entry.getValue().getLastActivity();
				long lastAction = Duration.between(currentTime, lastActivity).toSeconds();
				if (lastAction > 5) {
					iterator.remove();
					continue;
				}

			}
		}

		while (gamesToReturn.size() == initialSize) {
			gamesToReturn = removeOldestGame(games);
		}

		return gamesToReturn;
	}

	private static Map<GameID, GameManager> removeOldestGame(Map<GameID, GameManager> games) {

		Map<GameID, GameManager> gamesToReturn = games;

		Set<Map.Entry<GameID, GameManager>> gamesSet = gamesToReturn.entrySet();
		Instant oldestTime = null;
		GameID oldestGame = null;
		boolean firstTimeInLoop = true;
		for (Map.Entry<GameID, GameManager> entry : gamesSet) {

			if (firstTimeInLoop) {
				firstTimeInLoop = false;
				oldestTime = entry.getValue().getCreationTime();
				oldestGame = entry.getKey();
				continue;
			}

			Instant currentGameCreationTime = entry.getValue().getCreationTime();
			if (oldestTime.isAfter(currentGameCreationTime)) {
				oldestTime = currentGameCreationTime;
				oldestGame = entry.getKey();
			}
		}
		gamesToReturn.remove(oldestGame);
		return gamesToReturn;

	}
}