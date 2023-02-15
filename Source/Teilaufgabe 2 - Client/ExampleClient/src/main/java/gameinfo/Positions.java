package gameinfo;

import map.Coordinate;

public class Positions {

	private Coordinate myPosition;
	private Coordinate treasure;
	private Coordinate enemyFort;
	private EMyMapPosition myMapPosition = EMyMapPosition.UNKNOWN;

	private boolean treasureVisible = false;
	private boolean fortVisible = false;

	public Coordinate getMyPosition() {
		return myPosition;
	}

	public void setMyPosition(Coordinate myPosition) {
		this.myPosition = myPosition;
	}

	public Coordinate getTreasurePosition() {
		return treasure;
	}

	public void setTreasurePosition(Coordinate treasure) {
		this.treasure = treasure;
		this.treasureVisible = true;
	}

	public Coordinate getEnemyFortPosition() {
		return enemyFort;
	}

	public void setEnemyFortPosition(Coordinate enemyFort) {
		this.enemyFort = enemyFort;
		this.fortVisible = true;
	}

	public EMyMapPosition getMyMapPosition() {
		return myMapPosition;
	}

	public void setMyMapPosition(EMyMapPosition myMapPosition) {
		this.myMapPosition = myMapPosition;
	}

	public boolean isTreasureVisible() {
		return treasureVisible;
	}

	public boolean isFortVisible() {
		return fortVisible;
	}

}
