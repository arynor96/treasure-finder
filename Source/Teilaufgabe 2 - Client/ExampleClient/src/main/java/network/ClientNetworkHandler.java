package network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import client.main.datatypes.GameID;
import client.main.datatypes.ServerURL;
import exceptions.PlayerRegistrationInformationException;
import gameinfo.EGameStatus;
import map.ClientFullMap;
import messagesBase.ResponseEnvelope;
import messagesBase.UniquePlayerIdentifier;
import messagesBase.messagesFromClient.ERequestState;
import messagesBase.messagesFromServer.GameState;
import movement.EMoveClient;
import reactor.core.publisher.Mono;

public class ClientNetworkHandler {

	private static Logger logger = LoggerFactory.getLogger(ClientNetworkHandler.class);

	private String gameID;
	private String playerID;
	private WebClient baseWebClient;

	public ClientNetworkHandler(ServerURL serverBaseUrlClass, GameID gameIDclass) {
		this.gameID = gameIDclass.getGameID();
		this.baseWebClient = WebClient.builder().baseUrl(serverBaseUrlClass.getServerURL() + "/games")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE)
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE).build();
	}

	public void registerClient(String studentFirstName, String studentLastName, String studentUAccount) {

		Mono<ResponseEnvelope> webAccess = null;

		try {
			webAccess = baseWebClient.method(HttpMethod.POST).uri("/" + gameID + "/players")
					.body(BodyInserters.fromValue(PlayerRegistrationCreator.createPlayerRegistration(studentFirstName,
							studentLastName, studentUAccount)))

					.retrieve().bodyToMono(ResponseEnvelope.class);
		} catch (PlayerRegistrationInformationException e) {
			logger.error("Player information are missing: " + e.getMessage());
		}

		ResponseEnvelope<UniquePlayerIdentifier> resultReg = webAccess.block();

		if (resultReg.getState() == ERequestState.Error) {
			logger.error("Registration error, errormessage: " + resultReg.getExceptionMessage());
		} else {
			UniquePlayerIdentifier uniqueID = resultReg.getData().get();
			logger.info("Registration succesful. Client Player's ID is: " + uniqueID.getUniquePlayerID());

		}

		this.playerID = resultReg.getData().get().getUniquePlayerID();

	}

	public void sendHalfMap(ClientFullMap halfMap) {
		Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.POST).uri("/" + gameID + "/halfmaps")
				.body(BodyInserters.fromValue(LocalMapConverter.convertHalfMap(playerID, halfMap))).retrieve()
				.bodyToMono(ResponseEnvelope.class);
		;

		ResponseEnvelope<GameState> requestResult = webAccess.block();

		if (requestResult.getState() == ERequestState.Error) {
			System.err.println("Send half map error, errormessage: " + requestResult.getExceptionMessage());
		}

		logger.debug("Server accepted client's HalfMap");

	}

	public boolean isMyTurn() {
		Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.GET)
				.uri("/" + gameID + "/states/" + playerID).retrieve().bodyToMono(ResponseEnvelope.class);
		ResponseEnvelope<GameState> requestResult = webAccess.block();

		if (requestResult.getState() == ERequestState.Error) {
			System.err.println("Client error, errormessage: " + requestResult.getExceptionMessage());
		}

		EGameStatus gameStatus = getGameStatus();

		if (gameStatus.equals(EGameStatus.MUSTACT))
			return true;
		else
			return false;

	}

	public EGameStatus getGameStatus() {

		Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.GET)
				.uri("/" + gameID + "/states/" + playerID).retrieve().bodyToMono(ResponseEnvelope.class);
		ResponseEnvelope<GameState> requestResult = webAccess.block();

		if (requestResult.getState() == ERequestState.Error) {
			System.err.println("Client error, errormessage: " + requestResult.getExceptionMessage());
		}

		EGameStatus gameStatus = GameStatusConverter.convertGameStatus(requestResult.getData().get().getPlayers()
				.stream().filter(player -> player.getUniquePlayerID().equals(playerID)).findAny().get().getState());

		return gameStatus;

	}

	public ClientFullMap receiveGameStats() {

		Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.GET)
				.uri("/" + gameID + "/states/" + playerID).retrieve().bodyToMono(ResponseEnvelope.class);
		ResponseEnvelope<GameState> requestResult = webAccess.block();

		if (requestResult.getState() == ERequestState.Error) {
			logger.error("Client error, errormessage: " + requestResult.getExceptionMessage());
		}

		if (!isMyTurn()) {
			if (getGameStatus() == EGameStatus.MUSTWAIT) {
				waitBetweenRequests();
				receiveGameStats();
			}
		}

		return ServerMapConverter.convertFullMap(requestResult.getData().get());
	}

	public void sendMove(EMoveClient move) {

		Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.POST).uri("/" + gameID + "/moves")
				.body(BodyInserters.fromValue(MoveConverter.convertMove(playerID, move))).retrieve()
				.bodyToMono(ResponseEnvelope.class);

		ResponseEnvelope<GameState> requestResult = webAccess.block();

		if (requestResult.getState() == ERequestState.Error) {
			logger.error("Send Move error, errormessage: " + requestResult.getExceptionMessage());
		}

	}

	public boolean isTreasureCollected() {

		Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.GET)
				.uri("/" + gameID + "/states/" + playerID).retrieve().bodyToMono(ResponseEnvelope.class);
		ResponseEnvelope<GameState> requestResult = webAccess.block();

		return requestResult.getData().get().getPlayers().stream()
				.filter(player -> player.getUniquePlayerID().equals(playerID)).findAny().get().hasCollectedTreasure();

	}

	public boolean isGameFinished() {
		EGameStatus gameStatus = getGameStatus();
		if (gameStatus == EGameStatus.WON || gameStatus == EGameStatus.LOST)
			return true;
		else
			return false;
	}

	private void waitBetweenRequests() {
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
