package ServerAdministration;

import java.util.ArrayList;
import java.util.List;

import game.GameManager;
import messagesBase.UniqueGameIdentifier;
import messagesBase.UniquePlayerIdentifier;
import messagesBase.messagesFromClient.PlayerHalfMap;
import rules.IRule;

public class RuleChecker {

	private List<IRule> rules = new ArrayList<>();

	public RuleChecker(List<IRule> rules) {
		this.rules = rules;
	}

	public void checkPlayerRules(UniqueGameIdentifier gameID) {
		if (!rules.isEmpty()) {
			for (IRule rule : rules) {
				rule.checkPlayerRegistration(gameID);
			}
		}
	}

	public void checkMapRules(UniqueGameIdentifier gameID, PlayerHalfMap halfMap, GameManager game) {
		if (!rules.isEmpty()) {
			for (IRule rule : rules) {
				rule.checkMapRules(gameID, halfMap, game);
			}
		}
	}

	public void checkGameStateRules(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID) {
		if (!rules.isEmpty()) {
			for (IRule rule : rules) {
				rule.checkGameStateRules(gameID, playerID);
			}
		}
	}

}
