import static org.mockito.ArgumentMatchers.isA;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import client.main.ClientController;
import gameinfo.EGameStatus;
import halfmap.ClientHalfMapGenerator;
import map.ClientFullMap;
import movement.EMoveClient;
import movement.MoveManager;
import network.ClientNetworkHandler;
import view.CommandLineInterface;

public class ClientControllerTest {

	@Test
	public void ControllerGeneratesHalfMap_InvokeNetworkSendHalfMap_NetworkCallShouldWorkWithRightParameter() {

		// Arrange
		ClientNetworkHandler mockedNetwork = Mockito.mock(ClientNetworkHandler.class);

		CommandLineInterface cli = new CommandLineInterface();
		ClientController controller = new ClientController(mockedNetwork, cli);

		Mockito.when(mockedNetwork.getGameStatus()).thenReturn(EGameStatus.MUSTACT);
		Mockito.when(mockedNetwork.isGameFinished()).thenReturn(false);

		// Act
		controller.sendHalfMap();

		// Assert
		Mockito.verify(mockedNetwork, Mockito.atLeastOnce()).sendHalfMap(isA(ClientFullMap.class));
		;

	}

	@Test
	public void SendMove_InvokeNetworkSendMove_NetworkCallShouldWorkWithRightParameter() {

		// Arrange
		ClientNetworkHandler mockedNetwork = Mockito.mock(ClientNetworkHandler.class);

		CommandLineInterface cli = new CommandLineInterface();
		ClientController controller = new ClientController(mockedNetwork, cli);

		Mockito.when(mockedNetwork.getGameStatus()).thenReturn(EGameStatus.MUSTACT);
		Mockito.when(mockedNetwork.isGameFinished()).thenReturn(false);
		Mockito.when(mockedNetwork.isTreasureCollected()).thenReturn(false);

		// I am generating my own map, which I mock as it comes from the network.
		// I need it to be able to generate a move and send it.
		ClientFullMap halfMap1 = ClientHalfMapGenerator.generateHalfMap();
		ClientFullMap halfMap2 = ClientHalfMapGenerator.generateHalfMap();
		ClientFullMap fullMap = FullMapBuilder.mergeMapsTreasureVisible(halfMap1, halfMap2);
		MoveManager moveManager = new MoveManager(fullMap);
		Mockito.when(mockedNetwork.receiveGameStats()).thenReturn(fullMap);

		// Act
		controller.updateGameStats();
		controller.sendMove(moveManager);

		// Assert
		Mockito.verify(mockedNetwork, Mockito.atLeastOnce()).sendMove(isA(EMoveClient.class));
		;

	}

}
