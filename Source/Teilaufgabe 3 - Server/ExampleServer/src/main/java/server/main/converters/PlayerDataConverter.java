package server.main.converters;

import messagesBase.messagesFromClient.PlayerRegistration;
import player.PlayerData;
import player.PlayerFirstName;
import player.PlayerLastName;
import player.PlayerUAccount;

public class PlayerDataConverter {

	public static PlayerData toLocal(PlayerRegistration playerRegistration) {

		PlayerFirstName fName = new PlayerFirstName(playerRegistration.getStudentFirstName());
		PlayerLastName lName = new PlayerLastName(playerRegistration.getStudentLastName());
		PlayerUAccount uAcc = new PlayerUAccount(playerRegistration.getStudentUAccount());
		return new PlayerData(fName, lName, uAcc);
	}

}
