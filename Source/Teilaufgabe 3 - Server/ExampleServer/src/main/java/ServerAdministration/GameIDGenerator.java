package ServerAdministration;

import java.util.Random;
import java.util.Set;

import game.GameID;

public class GameIDGenerator {

	private static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String alphabetLower = alphabet.toLowerCase();
	private static final String digits = "1234567890";

	private static final int GAME_ID_LENGTH = 5;

	public static GameID generate(Set<GameID> games) {

		String gameID = "";
		for (int i = 0; i < GAME_ID_LENGTH; i++) {
			Random rand = new Random();
			int characterType = rand.nextInt(3);

			switch (characterType) {
			case 0:
				gameID = gameID + alphabet.charAt(rand.nextInt(alphabet.length() - 1));
				break;
			case 1:
				gameID = gameID + alphabetLower.charAt(rand.nextInt(alphabetLower.length() - 1));
				break;
			case 2:
				gameID = gameID + digits.charAt(rand.nextInt(digits.length() - 1));
				break;
			}

		}

		GameID newGame = new GameID(gameID);

		while (!uniqueID(games, newGame)) {
			newGame = GameIDGenerator.generate(games);
		}

		return newGame;
	}

	private static boolean uniqueID(Set<GameID> games, GameID gameID) {
		if (games.contains(gameID))
			return false;
		else
			return true;

	}

}
