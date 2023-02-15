package movement;

import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gameinfo.Positions;
import map.ClientFullMap;
import map.Coordinate;
import movement.helpers.CoordinateToEMoveStack;
import movement.targetfinder.FortFinder;
import movement.targetfinder.ITargetFinder;
import movement.targetfinder.TreasureFinder;

public class MoveManager {

	private final static Logger logger = LoggerFactory.getLogger(MoveManager.class);

	private Positions positions;
	private PathFinder pathFinder;
	private ITargetFinder targetFinder;

	private boolean treasureCollected = false;

	private Stack<Coordinate> path = new Stack<Coordinate>();
	private Stack<EMoveClient> nextMoves = new Stack<EMoveClient>();

	public MoveManager(ClientFullMap map) {
		this.pathFinder = new PathFinder(map);
		this.targetFinder = new TreasureFinder(map);
	}

	public void updateMoveManagerPositions(Positions positions, boolean treasureCollected) {
		this.positions = positions;
		this.treasureCollected = treasureCollected;
		this.targetFinder.updateFinder(positions.getMyPosition(), positions.getMyMapPosition(),
				positions.getMyPosition());

	}

	public EMoveClient getNextMove() {

		analyseNewInformation();

		if (nextMoves.empty()) {
			if (path.empty()) {
				calculateNextPath();
			}

			Coordinate myPos = positions.getMyPosition();
			logger.debug("Next step is " + myPos + " to " + path.peek());
			Coordinate target = path.pop();
			CoordinateToEMoveStack nextMovesGenerator = new CoordinateToEMoveStack(myPos, target,
					targetFinder.getVisitedMap());
			nextMoves = nextMovesGenerator.buildStack();
		}

		logger.debug("Move sent is: " + nextMoves.peek());
		return nextMoves.pop();
	}

	private void calculateNextPath() {
		Coordinate target;
		logger.debug("Calculating new path.");

		if (this.treasureCollected && !(targetFinder instanceof FortFinder)) {
			changeStrategy();
		}

		target = targetFinder.findTarget();
		path = pathFinder.calculatePath(this.positions.getMyPosition(), target);

	}

	private void changeStrategy() {

		ClientFullMap currentMapState = this.targetFinder.getVisitedMap();
		this.targetFinder = new FortFinder(currentMapState);

		targetFinder.updateFinder(this.positions.getMyPosition(), this.positions.getMyMapPosition(),
				this.positions.getMyPosition());

		path.clear();
		logger.debug("Current path deleted. Strategy changed to fort finding.");
	}

	private void analyseNewInformation() {

		// don't want to change the direction if already sent some moves required to go
		// the next coordinate
		if (nextMoves.empty()) {

			if (this.positions.isTreasureVisible() && this.treasureCollected == false) {
				path = pathFinder.calculatePath(positions.getMyPosition(), positions.getTreasurePosition());
				logger.debug("Treasure is visible, new path is to: " + positions.getTreasurePosition());
			}

			if (this.positions.isFortVisible() && this.treasureCollected == true) {
				path = pathFinder.calculatePath(positions.getMyPosition(), positions.getEnemyFortPosition());
				logger.debug(
						"Fort visible and treasure collected, new path is to: " + positions.getEnemyFortPosition());
			}
		}

	}

	public ITargetFinder getTargetFinder() {
		return targetFinder;
	}

}
