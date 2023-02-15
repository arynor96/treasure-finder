package rules;

import exceptions.HalfMapNotEnoughTerrainTypeException;
import game.GameManager;
import messagesBase.UniqueGameIdentifier;
import messagesBase.UniquePlayerIdentifier;
import messagesBase.messagesFromClient.PlayerHalfMap;
import messagesBase.messagesFromClient.PlayerHalfMapNode;
import player.Player;

public class HalfMapTerrainType implements IRule {

	public final static int MINIMUM_GRASS_FIELDS = 24;
	public final static int MINIMUM_WATER_FIELDS = 7;
	public final static int MINIMUM_MOUNTAIN_FIELDS = 5;

	@Override
	public void checkPlayerRegistration(UniqueGameIdentifier gameID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkMapRules(UniqueGameIdentifier gameID, PlayerHalfMap halfMap, GameManager game) {

		Player sender = new Player(halfMap.getUniquePlayerID());

		int grass = MINIMUM_GRASS_FIELDS;
		int water = MINIMUM_WATER_FIELDS;
		int mountain = MINIMUM_MOUNTAIN_FIELDS;

		for (PlayerHalfMapNode node : halfMap.getMapNodes()) {
			switch (node.getTerrain()) {
			case Grass:
				grass--;
				break;
			case Water:
				water--;
				break;
			case Mountain:
				mountain--;
				break;
			}
		}

		if (grass > 0) {
			game.setWinner(game.getEnemy(sender));
			throw new HalfMapNotEnoughTerrainTypeException("Terrain Type problem",
					"Half Map does not contain enough grass!");
		}

		if (water > 0) {
			game.setWinner(game.getEnemy(sender));
			throw new HalfMapNotEnoughTerrainTypeException("Terrain Type problem",
					"Half Map does not contain enough water!");
		}

		if (mountain > 0) {
			game.setWinner(game.getEnemy(sender));
			throw new HalfMapNotEnoughTerrainTypeException("Terrain Type problem",
					"Half Map does not contain enough mountain!");
		}

	}

	@Override
	public void checkGameStateRules(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID) {
		// TODO Auto-generated method stub

	}

}
