package movement.targetfinder;

import gameinfo.EMyMapPosition;
import map.ClientFullMap;
import map.Coordinate;

public interface ITargetFinder {

	public void updateFinder(Coordinate myNewPos, EMyMapPosition mapPosition, Coordinate myPosition);

	public Coordinate findTarget();

	public ClientFullMap getVisitedMap();

	public boolean isNodeVisited(Coordinate c);

}
