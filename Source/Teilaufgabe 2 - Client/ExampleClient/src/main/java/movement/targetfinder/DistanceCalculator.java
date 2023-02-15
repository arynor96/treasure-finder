package movement.targetfinder;

import map.Coordinate;

public class DistanceCalculator {

	public static double calculateDistance(Coordinate current, Coordinate target) {

		return Math.abs(current.getX() - target.getX()) + Math.abs(current.getY() - target.getY());

	}

}
