package gameinfo;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import halfmap.ClientHalfMapGenerator;
import map.ClientFullMap;
import map.ClientMapNode;
import map.Coordinate;
import map.EFort;
import map.EPlayer;

public class MapAnalyser {

	private static Logger logger = LoggerFactory.getLogger(MapAnalyser.class);

	private GameProgress gameProgress = new GameProgress();
	private Positions positions = new Positions();

	public void updateMapAnalyser(ClientFullMap map) {
		analyseMap(map);
	}

	private void analyseMap(ClientFullMap map) {

		Map<Coordinate, ClientMapNode> nodes = map.getNodes();

		for (int y = 0; y <= map.getHeigth(); y++) {
			for (int x = 0; x <= map.getLength(); x++) {
				Coordinate currentCoordinate = new Coordinate(x, y);
				ClientMapNode currentNode = nodes.get(currentCoordinate);

				updateTreasurePosition(currentNode, currentCoordinate);
				updateEnemyFortPosition(currentNode, currentCoordinate);
				updateMyPosition(currentNode, currentCoordinate);

			}
		}
		gameProgress.updateGameProgress(map);
	}

	public void setMyMapPosition() {
		positions.setMyMapPosition(findMyMapPlacementPosition());
		logger.debug("Client's map was placed on the " + positions.getMyMapPosition() + " part.");
	}

	public void treasureCollected() {
		this.gameProgress.setTreasureState(ETreasure.PICKED);
		logger.debug("Client's treasure was picked up.");
	}

	public ClientFullMap getMap() {
		return gameProgress.getMap();
	}

	public Coordinate getMyPosition() {
		return positions.getMyPosition();
	}

	public GameProgress getGameProgress() {
		return gameProgress;
	}

	public Positions getRelevantPositions() {
		return this.positions;
	}

	private void updateTreasurePosition(ClientMapNode node, Coordinate c) {

		if (node.isHasMyTreasure()) {
			positions.setTreasurePosition(c);
			gameProgress.setTreasureState(ETreasure.DISCOVERED);
			logger.debug("MapAnalyser can see the treasure.");
		}
	}

	private void updateMyPosition(ClientMapNode node, Coordinate c) {

		if (node.getPlayerState() == EPlayer.MY || node.getPlayerState() == EPlayer.BOTH) {
			positions.setMyPosition(c);
		}
	}

	private void updateEnemyFortPosition(ClientMapNode node, Coordinate c) {

		if (node.getFortState() == EFort.ENEMYFORT) {
			gameProgress.setEnemyFortDiscovered();
			positions.setEnemyFortPosition(c);
			logger.debug("MapAnalyser can see the enemy fort.");
		}

	}

	private EMyMapPosition findMyMapPlacementPosition() {
		Coordinate myPos = positions.getMyPosition();
		int mapLength = gameProgress.getMap().getLength();

		if (mapLength > ClientHalfMapGenerator.HALF_MAP_LENGTH) {
			if (myPos.getX() > ClientHalfMapGenerator.HALF_MAP_LENGTH)
				return EMyMapPosition.RIGHT;
			else
				return EMyMapPosition.LEFT;
		} else if (myPos.getY() > ClientHalfMapGenerator.HALF_MAP_HEIGHT)
			return EMyMapPosition.DOWN;
		else
			return EMyMapPosition.UP;

	}

}
