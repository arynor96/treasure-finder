package ServerAdministration;

import java.util.UUID;

public class PlayerIDGenerator {

	public static String generate() {
		return UUID.randomUUID().toString();
	}

}
