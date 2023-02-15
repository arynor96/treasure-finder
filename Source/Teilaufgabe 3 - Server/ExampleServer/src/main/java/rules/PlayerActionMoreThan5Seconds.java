package rules;

import java.time.Duration;
import java.time.Instant;

import exceptions.PlayerNeededMoreThan5SecondsException;
import game.GameManager;
import messagesBase.UniqueGameIdentifier;
import messagesBase.UniquePlayerIdentifier;
import messagesBase.messagesFromClient.PlayerHalfMap;
import player.Player;

public class PlayerActionMoreThan5Seconds implements IRule {

	@Override
	public void checkPlayerRegistration(UniqueGameIdentifier gameID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkMapRules(UniqueGameIdentifier gameID, PlayerHalfMap halfMap, GameManager game) {
		Instant currentTime = Instant.now();
		Instant lastActivity = game.getLastActivity();
		long lastAction = Duration.between(currentTime, lastActivity).toSeconds();
		if (lastAction > 5) {
			game.setWinner(game.getEnemy(new Player(halfMap.getUniquePlayerID())));
			throw new PlayerNeededMoreThan5SecondsException("Needed to much time",
					"You need to be faster. You sent something after more than 5 seconds.");
		}

	}

	@Override
	public void checkGameStateRules(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID) {
		// TODO Auto-generated method stub

	}

}
