package map;

import player.Player;

public class ServerMapNode {

	private Player creator;
	private ETerrainType terrainType;
	private EFort fortState = EFort.NOFORT;
	private EPlayerState playerState = EPlayerState.NONE;
	private ETreasure treasureState = ETreasure.NO;

	public ServerMapNode(ETerrainType terrain, Player creator) {
		this.creator = creator;
		this.terrainType = terrain;
	}

	public ServerMapNode(ETerrainType terrain, EFort fortState, Player creator) {
		this.creator = creator;
		this.terrainType = terrain;
		this.fortState = fortState;
	}

	public ServerMapNode(ETerrainType terrainType) {
		this.terrainType = terrainType;
	}

	public ETerrainType getTerrainType() {
		return this.terrainType;
	}

	// for FloodFill
	public void setTerrainType(ETerrainType terrainType) {
		this.terrainType = terrainType;

	}

	public EPlayerState getPlayerState() {
		return playerState;
	}

	public void setPlayerState(EPlayerState playerState) {
		this.playerState = playerState;
	}

	public ETreasure getTreasureState() {
		return treasureState;
	}

	public void setTreasureState(ETreasure treasureState) {
		this.treasureState = treasureState;
	}

	public Player getCreator() {
		return creator;
	}

	public EFort getFortState() {
		return fortState;
	}

}
