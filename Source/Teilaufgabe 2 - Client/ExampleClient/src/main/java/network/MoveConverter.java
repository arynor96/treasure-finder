package network;

import messagesBase.messagesFromClient.EMove;
import messagesBase.messagesFromClient.PlayerMove;
import movement.EMoveClient;

public class MoveConverter {

	public static PlayerMove convertMove(String playerID, EMoveClient move) {

		EMove convertedMove;
		switch (move) {
		case UP:
			convertedMove = EMove.Up;
			break;
		case DOWN:
			convertedMove = EMove.Down;
			break;
		case RIGHT:
			convertedMove = EMove.Right;
			break;
		default:
			convertedMove = EMove.Left;
		}

		return PlayerMove.of(playerID, convertedMove);
	}

}
