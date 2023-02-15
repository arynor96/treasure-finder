package client.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gameinfo.EGameStatus;
import gameinfo.MapAnalyser;
import halfmap.ClientHalfMapGenerator;
import map.ClientFullMap;
import movement.MoveManager;
import network.ClientNetworkHandler;
import view.CommandLineInterface;

public class ClientController {

	private final static Logger logger = LoggerFactory.getLogger(ClientController.class);

	private ClientNetworkHandler network;
	private MapAnalyser analyser = new MapAnalyser();

	public ClientController(ClientNetworkHandler network, CommandLineInterface cli) {
		this.network = network;
		this.analyser.getGameProgress().registerView(cli);
	}

	public void registerClient(String studentFirstName, String studentLastName, String studentUAccount) {
		logger.debug("Registering: " + studentUAccount);
		this.network.registerClient(studentFirstName, studentLastName, studentUAccount);
	}

	public void sendHalfMap() {
		waitForMyTurn();
		ClientFullMap halfMap = ClientHalfMapGenerator.generateHalfMap();
		logger.debug("Sending my HalfMap to the server.");
		network.sendHalfMap(halfMap);

	}

	public void updateGameStats() {
		waitForMyTurn();
		ClientFullMap map = network.receiveGameStats();

		if (analyser.getMap().getNodes().size() == 0) {
			logger.debug("Received from the server the FullMap");
			analyser.updateMapAnalyser(map);
			analyser.setMyMapPosition();
		} else {
			analyser.updateMapAnalyser(map);
			updateTreasureState();
		}
	}

	public void sendMove(MoveManager moveManager) {
		moveManager.updateMoveManagerPositions(analyser.getRelevantPositions(), network.isTreasureCollected());
		network.sendMove(moveManager.getNextMove());

	}

	private void updateTreasureState() {
		if (network.isTreasureCollected()) {
			analyser.treasureCollected();
		}
	}

	private void waitForMyTurn() {
		while (network.getGameStatus() != EGameStatus.MUSTACT && !network.isGameFinished()) {
		}
		analyser.getGameProgress().setGameStatus(network.getGameStatus());

	}
}
