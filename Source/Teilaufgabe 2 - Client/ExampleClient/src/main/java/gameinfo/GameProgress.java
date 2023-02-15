package gameinfo;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import map.ClientFullMap;

public class GameProgress {

	private PropertyChangeSupport listener = new PropertyChangeSupport(this);

	private ClientFullMap map = new ClientFullMap();
	private ETreasure treasureState = ETreasure.UNKNOWN;
	private EEnemyFort enemyFort = EEnemyFort.UNKNOWN;
	private EGameStatus gameStatus = EGameStatus.MUSTWAIT;

	public GameProgress() {

	}

	public void updateGameProgress(ClientFullMap map) {
		ClientFullMap oldMap = this.map;
		this.map = map;
		this.listener.firePropertyChange("Map", oldMap, this.map);

	}

	public void setTreasureState(ETreasure treasureState) {
		ETreasure oldTreasureState = this.treasureState;
		this.treasureState = treasureState;
		this.listener.firePropertyChange("ETreasure", oldTreasureState, this.treasureState);
	}

	public void setEnemyFortDiscovered() {
		EEnemyFort oldFortState = this.enemyFort;
		this.enemyFort = EEnemyFort.DISCOVERED;
		this.listener.firePropertyChange("EEnemyFort", oldFortState, this.enemyFort);
	}

	public ClientFullMap getMap() {
		return map;
	}

	public void setGameStatus(EGameStatus gameStatus) {
		EGameStatus oldGameStatus = this.gameStatus;
		this.gameStatus = gameStatus;
		this.listener.firePropertyChange("EGameStatus", oldGameStatus, this.gameStatus);
	}

	public void registerView(PropertyChangeListener newView) {
		listener.addPropertyChangeListener(newView);
	}

}
