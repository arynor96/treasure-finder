package network;

import gameinfo.EGameStatus;
import messagesBase.messagesFromServer.EPlayerGameState;

public class GameStatusConverter {

	public static EGameStatus convertGameStatus(EPlayerGameState status) {
		switch (status) {
		case MustAct:
			return EGameStatus.MUSTACT;
		case MustWait:
			return EGameStatus.MUSTWAIT;
		case Won:
			return EGameStatus.WON;
		default:
			return EGameStatus.LOST;
		}
	}

}
