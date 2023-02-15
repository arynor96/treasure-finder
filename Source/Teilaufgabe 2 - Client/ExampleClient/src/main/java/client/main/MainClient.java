package client.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.main.datatypes.GameID;
import client.main.datatypes.ServerURL;
import exceptions.ArgsNumberException;
import movement.MoveManager;
import network.ClientNetworkHandler;
import view.CommandLineInterface;

public class MainClient {

	public static void main(String[] args) {

		final Logger logger = LoggerFactory.getLogger(MainClient.class);

		try {
			InputVerifier.checkArgs(args);
		} catch (ArgsNumberException e) {
			logger.error("Not enough arguments are provided! " + e.getMessage());
		}

		ServerURL serverBaseUrl = new ServerURL(args[1]);
		GameID gameId = new GameID(args[2]);
		ClientNetworkHandler network = new ClientNetworkHandler(serverBaseUrl, gameId);

		CommandLineInterface cli = new CommandLineInterface();
		ClientController controller = new ClientController(network, cli);

		controller.registerClient("Adrian", "Boghean", "adrianb96");
		controller.sendHalfMap();

		// receive full map
		controller.updateGameStats();

		MoveManager moveManager = new MoveManager(network.receiveGameStats());

		while (!network.isGameFinished()) {
			controller.sendMove(moveManager);
			controller.updateGameStats();
		}

	}

}