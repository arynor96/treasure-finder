package player;

public class PlayerData {

	private PlayerFirstName firstName;
	private PlayerLastName lastName;
	private PlayerUAccount uAccount;

	public PlayerData(PlayerFirstName firstName, PlayerLastName lastName, PlayerUAccount uAccount) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.uAccount = uAccount;
	}

	public PlayerFirstName getFirstName() {
		return firstName;
	}

	public PlayerLastName getLastName() {
		return lastName;
	}

	public PlayerUAccount getuAccount() {
		return uAccount;
	}

}
