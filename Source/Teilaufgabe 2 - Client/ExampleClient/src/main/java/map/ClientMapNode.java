package map;

public class ClientMapNode {

	private ETerrainClient terrainType;
	private EFort fortState = EFort.NOFORT;
	private EPlayer playerState = EPlayer.NONE;
	private boolean hasMyTreasure = false;
	private boolean visited = false;

	// ##### START Only for Dijkstra relevant ######
	// I don't really know what would have been better, to use this class or to
	// create a new structure just for Dijkstra.
	private int dijkstraCost = Integer.MAX_VALUE;
	private Coordinate previousNode = null;
	// ##### END Only for Dijkstra relevant ######

	public ClientMapNode(ETerrainClient terrainType) {
		this.terrainType = terrainType;
	}

	public ClientMapNode(ETerrainClient terrainType, EFort fortState, EPlayer playerState, boolean hasMyTreasure) {
		super();
		this.terrainType = terrainType;
		this.fortState = fortState;
		this.playerState = playerState;
		this.hasMyTreasure = hasMyTreasure;
	}

	public EFort getFortState() {
		return fortState;
	}

	public ETerrainClient getTerrainType() {
		return terrainType;
	}

	public EPlayer getPlayerState() {
		return playerState;
	}

	public boolean isHasMyTreasure() {
		return hasMyTreasure;
	}

	public void setFortState(EFort fortState) {
		this.fortState = fortState;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	// useful for flood fill only
	public void setTerrainType(ETerrainClient terrainType) {
		this.terrainType = terrainType;
	}

	// ##### START Only for Dijkstra relevant ######
	public int getDijkstraCost() {
		return dijkstraCost;
	}

	public void setDijkstraCost(int dijkstraCost) {
		this.dijkstraCost = dijkstraCost;
	}

	public Coordinate getPreviousNode() {
		return previousNode;
	}

	public void setPreviousNode(Coordinate previousNode) {
		this.previousNode = previousNode;
	}
	// ##### END Only for Dijkstra relevant ######

}
