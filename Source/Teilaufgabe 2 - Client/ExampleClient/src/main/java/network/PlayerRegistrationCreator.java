package network;

import exceptions.PlayerRegistrationInformationException;
import messagesBase.messagesFromClient.PlayerRegistration;

public class PlayerRegistrationCreator {

	public static PlayerRegistration createPlayerRegistration(String studentFirstName, String studentLastName,
			String studentUAccount) throws PlayerRegistrationInformationException {

		if (studentFirstName.isEmpty()) {
			throw new PlayerRegistrationInformationException("First name is empty.");
		}
		if (studentLastName.isEmpty()) {
			throw new PlayerRegistrationInformationException("Last name is empty.");
		}
		if (studentUAccount.isEmpty()) {
			throw new PlayerRegistrationInformationException("UAccount is empty.");
		}

		return new PlayerRegistration(studentFirstName, studentLastName, studentUAccount);
	}

}
