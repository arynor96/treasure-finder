package movement.helpers;

import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import map.ClientFullMap;
import map.Coordinate;
import movement.EMoveClient;

public class CoordinateToEMoveStack {

	private static Logger logger = LoggerFactory.getLogger(CoordinateToEMoveStack.class);

	private Coordinate myPos;
	private Coordinate target;
	private ClientFullMap map;

	public CoordinateToEMoveStack(Coordinate myPos, Coordinate target, ClientFullMap map) {
		this.myPos = myPos;
		this.target = target;
		this.map = map;
	}

	public Stack<EMoveClient> buildStack() {

		Stack<EMoveClient> stack = new Stack<EMoveClient>();

		int cost = NodesAnalyser.calculateMoveCost(myPos, target, map);
		logger.trace("Cost to go there is: " + cost);
		while (cost > 0) {
			stack.push(calculateDirection(myPos, target));
			cost--;
		}

		logger.trace("Stack size is: " + stack.size());
		return stack;

	}

	private EMoveClient calculateDirection(Coordinate start, Coordinate target) {

		if (target.getX() > start.getX())
			return EMoveClient.RIGHT;
		if (target.getX() < start.getX())
			return EMoveClient.LEFT;
		if (target.getY() > start.getY())
			return EMoveClient.DOWN;
		else
			return EMoveClient.UP;

	}

}
