package ServerAdministration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.GameID;
import game.GameManager;
import gamestats.GameStats;
import map.ServerFullMap;
import messagesBase.UniqueGameIdentifier;
import messagesBase.UniquePlayerIdentifier;
import messagesBase.messagesFromClient.PlayerHalfMap;
import player.Player;
import player.PlayerData;
import rules.GameIDExistence;
import rules.HalfMapFortExistsOnGrass;
import rules.HalfMapIsland;
import rules.HalfMapSize;
import rules.HalfMapTerrainType;
import rules.HalfMapWaterOnEdges;
import rules.IRule;
import rules.PlayerActionMoreThan5Seconds;
import rules.PlayerMax2Players;
import rules.PlayerRegistered;
import rules.PlayerSentMapAlready;

public class ServerManager {

	private Map<GameID, GameManager> games = new HashMap<>();

	public GameID createGame() {
		GameID newGame = GameIDGenerator.generate(games.keySet());
		games.put(newGame, new GameManager());

		// will deleted finished games, games that took more than 10 minutes or games
		// with no activity in the last 5 seconds.
		if (games.size() > 999) {
			sanitizeGames();
		}

		return newGame;
	}

	public String addPlayer(GameID gameID, PlayerData playerDetails) {
		GameManager game = games.get(gameID);
		return game.addPlayer(playerDetails);

	}

	public void addMap(ServerFullMap map, GameID gameID) {
		GameManager game = games.get(gameID);
		game.addHalfMap(map);
	}

	public GameStats createGameState(GameID gameID, Player player) {
		return games.get(gameID).createGameStatus(player);
	}

	private void sanitizeGames() {
		this.games = GamesSanitizer.sanitizeGames(games);
	}

	public void checkRules(UniqueGameIdentifier gameID) {

		RuleChecker ruleChecker = new RuleChecker(getRules());
		ruleChecker.checkPlayerRules(gameID);

	}

	public void checkMapRules(UniqueGameIdentifier gameID, PlayerHalfMap halfMap) {
		RuleChecker ruleChecker = new RuleChecker(getRules());
		ruleChecker.checkMapRules(gameID, halfMap, games.get(new GameID(gameID.getUniqueGameID())));

	}

	public void checkGameStateRules(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID) {
		RuleChecker ruleChecker = new RuleChecker(getRules());
		ruleChecker.checkGameStateRules(gameID, playerID);

	}

	private List<IRule> getRules() {

		List<IRule> rulesToCheck = new ArrayList<>();

		rulesToCheck.add(new GameIDExistence(games.keySet()));
		rulesToCheck.add(new PlayerRegistered(games));
		rulesToCheck.add(new PlayerMax2Players(games));
		rulesToCheck.add(new PlayerSentMapAlready());
		rulesToCheck.add(new HalfMapFortExistsOnGrass());
		rulesToCheck.add(new HalfMapIsland());
		rulesToCheck.add(new HalfMapSize());
		rulesToCheck.add(new HalfMapTerrainType());
		rulesToCheck.add(new HalfMapWaterOnEdges());
		rulesToCheck.add(new PlayerActionMoreThan5Seconds());

		return rulesToCheck;
	}

}