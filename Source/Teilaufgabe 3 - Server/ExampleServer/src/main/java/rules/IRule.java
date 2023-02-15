package rules;

import game.GameManager;
import messagesBase.UniqueGameIdentifier;
import messagesBase.UniquePlayerIdentifier;
import messagesBase.messagesFromClient.PlayerHalfMap;

public interface IRule {

	public void checkPlayerRegistration(UniqueGameIdentifier gameID);

	public void checkMapRules(UniqueGameIdentifier gameID, PlayerHalfMap halfMap, GameManager game);

	public void checkGameStateRules(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID);

}
