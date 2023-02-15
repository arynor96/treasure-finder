package server.main;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ServerAdministration.ServerManager;
import game.GameID;
import gamestats.GameStats;
import messagesBase.ResponseEnvelope;
import messagesBase.UniqueGameIdentifier;
import messagesBase.UniquePlayerIdentifier;
import messagesBase.messagesFromClient.PlayerHalfMap;
import messagesBase.messagesFromClient.PlayerRegistration;
import messagesBase.messagesFromServer.GameState;
import player.Player;
import player.PlayerData;
import server.exceptions.GenericExampleException;
import server.main.converters.GameIDConverter;
import server.main.converters.GameStateConverter;
import server.main.converters.PlayerDataConverter;
import server.main.converters.PlayerHalfMapConverter;
import server.main.converters.PlayerIDConverter;

@RestController
@RequestMapping(value = "/games")
public class ServerEndpoints {

	private ServerManager serverManager = new ServerManager();

	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody UniqueGameIdentifier newGame(
			@RequestParam(required = false, defaultValue = "false", value = "enableDebugMode") boolean enableDebugMode,
			@RequestParam(required = false, defaultValue = "false", value = "enableDummyCompetition") boolean enableDummyCompetition) {

		// set showExceptionHandling to true to test/play around with the automatic
		// exception handling (see the handleException method at the bottom)
		// this is just some testing code that you can see how exceptions can be used to
		// signal errors to the client, you can REMOVE
		// these lines in your actual server implementation
		boolean showExceptionHandling = false;
		if (showExceptionHandling) {
			// if any error occurs, simply throw an exception with inherits from
			// GenericExampleException
			// the given code than takes care of responding with an error message to the
			// client based on the @ExceptionHandler below
			// make yourself familiar with this concept by setting
			// showExceptionHandling=true
			// and creating a new game through the browser
			// your implementation should use more useful error messages and specialized
			// exception classes
			throw new GenericExampleException("Name: Something", "Message: went totally wrong");
		}

		return GameIDConverter.toNetwork(serverManager.createGame().getGameID());

	}

	// example for a POST endpoint based on /games/{gameID}/players
	@RequestMapping(value = "/{gameID}/players", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody ResponseEnvelope<UniquePlayerIdentifier> registerPlayer(
			@Validated @PathVariable UniqueGameIdentifier gameID,
			@Validated @RequestBody PlayerRegistration playerRegistration) {

		serverManager.checkRules(gameID);

		GameID currentGame = GameIDConverter.toLocal(gameID.getUniqueGameID());
		PlayerData playerDetails = PlayerDataConverter.toLocal(playerRegistration);
		UniquePlayerIdentifier newPlayerID = PlayerIDConverter
				.toNetwork(serverManager.addPlayer(currentGame, playerDetails));

		return new ResponseEnvelope<>(newPlayerID);

	}

	@RequestMapping(value = "/{gameID}/halfmaps", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody ResponseEnvelope<?> receiveHalfMap(@Validated @PathVariable UniqueGameIdentifier gameID,
			@Validated @RequestBody PlayerHalfMap halfMap) {

		serverManager.checkMapRules(gameID, halfMap);

		GameID currentGame = GameIDConverter.toLocal(gameID.getUniqueGameID());
		serverManager.addMap(PlayerHalfMapConverter.toLocal(halfMap), currentGame);

		return new ResponseEnvelope<>();

	}

	@RequestMapping(value = "/{gameID}/states/{playerID}", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody ResponseEnvelope<?> sendGameStatus(@Validated @PathVariable UniqueGameIdentifier gameID,
			@Validated @PathVariable UniquePlayerIdentifier playerID) {

		serverManager.checkGameStateRules(gameID, playerID);

		GameID currentGame = GameIDConverter.toLocal(gameID.getUniqueGameID());
		Player convertedPlayer = new Player(playerID.getUniquePlayerID());

		GameStats currentGameStats = serverManager.createGameState(currentGame, convertedPlayer);

		return new ResponseEnvelope<GameState>(GameStateConverter.toNetwork(currentGameStats, convertedPlayer));

	}

	@ExceptionHandler({ GenericExampleException.class })
	public @ResponseBody ResponseEnvelope<?> handleException(GenericExampleException ex, HttpServletResponse response) {
		ResponseEnvelope<?> result = new ResponseEnvelope<>(ex.getErrorName(), ex.getMessage());

		// reply with 200 OK as defined in the network documentation
		// Side note: We only do this here for simplicity reasons. For future projects,
		// you should check out HTTP status codes and
		// what they can be used for. Note, the WebClient used during the Client
		// implementation can react
		// to them using the .onStatus(...) method.
		response.setStatus(HttpServletResponse.SC_OK);
		return result;
	}
}
