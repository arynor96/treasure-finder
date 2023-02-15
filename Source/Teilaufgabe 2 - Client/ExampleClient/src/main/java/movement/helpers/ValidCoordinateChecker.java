package movement.helpers;

import map.ClientFullMap;
import map.Coordinate;
import map.ETerrainClient;

public class ValidCoordinateChecker {

	public static boolean isValidNode(int x, int y, ClientFullMap map) {

		if (x < 0 || x > map.getLength())
			return false;
		if (y < 0 || y > map.getHeigth())
			return false;

		Coordinate c = new Coordinate(x, y);
		if (map.getNodes().get(c).getTerrainType() != ETerrainClient.WATER)
			return true;
		else
			return false;

	}

}
