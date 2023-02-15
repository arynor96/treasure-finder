package view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

import gameinfo.EEnemyFort;
import gameinfo.EGameStatus;
import gameinfo.ETreasure;
import map.ClientFullMap;
import map.ClientMapNode;
import map.Coordinate;
import map.EFort;
import map.EPlayer;

// some of the colour changes where working only in the eclipse terminal ON WINDOWS
// tested on WSL Ubuntu and native Linux with Gnome terminal later and found out that some emoji can't change colour
// I have changed some emojis later so that it could work on both OSs.
// example: instead of using only one emoji of a square and then color it manually, I have used 3 square emojis with different colours.

public class CommandLineInterface implements PropertyChangeListener {

	private int turnNumber = 0;

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		// Object newValue = evt.getSource();
		Object newValue = evt.getNewValue();
		if (newValue instanceof ClientFullMap castedValue) {
			displayGame(castedValue);
		}

		if (newValue instanceof ETreasure castedValue) {
			treasureState(castedValue);
		}

		if (newValue instanceof EEnemyFort castedValue) {
			enemyFort(castedValue);
		}

		if (newValue instanceof EGameStatus castedValue) {
			displayIfEndGame(castedValue);
		}

	}

	private void treasureState(ETreasure state) {
		switch (state) {
		case DISCOVERED:
			System.out.println(
					ECLIColors.MAGENTA.getColor() + "#### Treasure discovered. ####" + ECLIColors.RESET.getColor());
			break;
		case PICKED:
			System.out.println(
					ECLIColors.MAGENTA.getColor() + "#### Treasure collected. ####" + ECLIColors.RESET.getColor());
			break;
		default:
			System.out.println(ECLIColors.MAGENTA.getColor() + "#### Treasure not discovered yet. ####"
					+ ECLIColors.RESET.getColor());
		}

	}

	private void enemyFort(EEnemyFort fortState) {
		switch (fortState) {
		case DISCOVERED:
			System.out.println(
					ECLIColors.MAGENTA.getColor() + "#### Fort discovered. ####" + ECLIColors.RESET.getColor());
			break;
		case INVADED:
			System.out.println(ECLIColors.MAGENTA.getColor() + "#### Fort invaded. ####" + ECLIColors.RESET.getColor());
			break;
		default:
			System.out.println(
					ECLIColors.MAGENTA.getColor() + "#### Not discovered yet. ####" + ECLIColors.RESET.getColor());
		}

	}

	private void displayGame(ClientFullMap map) {
		System.out.println("#### --- Turn Number : " + turnNumber++ + " --- ####");
		displayMap(map);
	}

	private void displayMap(ClientFullMap map) {

		Map<Coordinate, ClientMapNode> nodes = map.getNodes();

		for (int y = 0; y <= map.getHeigth(); y++) {
			for (int x = 0; x <= map.getLength(); x++) {
				ClientMapNode currentNode = nodes.get(new Coordinate(x, y));
				displayNode(currentNode);

			}
			System.out.println();
		}

	}

	private void displayNode(ClientMapNode node) {

		if (node.getFortState() != EFort.NOFORT) {
			displayFort(node);
			return;
		}

		if (node.getPlayerState() != EPlayer.NONE) {
			displayPlayer(node);
			return;
		}

		if (node.isHasMyTreasure()) {
			System.out.print(ECLIColors.MAGENTA.getColor() + "💰" + ECLIColors.RESET.getColor());
			return;
		}

		displayTerrain(node);

	}

	private void displayPlayer(ClientMapNode node) {

		switch (node.getPlayerState()) {
		case MY:
			System.out.print(ECLIColors.BLUE.getColor() + "🧝" + ECLIColors.RESET.getColor());
			break;
		case ENEMY:
			System.out.print(ECLIColors.RED.getColor() + "😈" + ECLIColors.RESET.getColor());
			break;
		default:
			System.out.print(ECLIColors.RED.getColor() + "⚔" + ECLIColors.RESET.getColor());

		}

	}

	private void displayFort(ClientMapNode node) {
		if (node.getFortState() == EFort.MYFORT) {
			System.out.print(ECLIColors.BLUE.getColor() + "🏰" + ECLIColors.RESET.getColor());
		} else {
			System.out.print(ECLIColors.RED.getColor() + "🏰" + ECLIColors.RESET.getColor());
		}

	}

	private void displayTerrain(ClientMapNode node) {
		switch (node.getTerrainType()) {
		case GRASS:

			System.out.print(ECLIColors.GREEN.getColor() + "🟩" + ECLIColors.RESET.getColor());
			break;
		case MOUNTAIN:

			System.out.print(ECLIColors.BROWN.getColor() + "🟫" + ECLIColors.RESET.getColor());
			break;
		default:

			System.out.print(ECLIColors.BLUE.getColor() + "🟦" + ECLIColors.RESET.getColor());
			break;
		}

	}

	private void displayIfEndGame(EGameStatus status) {
		if (status.equals(EGameStatus.WON))
			System.out.println(ECLIColors.GREEN.getColor() + "#### WON! Final Map: ####" + ECLIColors.RESET.getColor());
		if (status.equals(EGameStatus.LOST))
			System.out
					.println(ECLIColors.RED.getColor() + "#### DEFEAT! Final Map: #### " + ECLIColors.RESET.getColor());

	}

}