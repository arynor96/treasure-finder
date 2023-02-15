package map;

import java.util.HashMap;
import java.util.Map;

public class ServerFullMap {

	private Map<Coordinate, ServerMapNode> nodes = new HashMap<Coordinate, ServerMapNode>();
	private EMapShape mapShape;

	public ServerFullMap() {

	}

	public ServerFullMap(Map<Coordinate, ServerMapNode> nodes) {
		this.nodes = nodes;
	}

	public ServerFullMap(Map<Coordinate, ServerMapNode> nodes, EMapShape mapShape) {
		this.nodes = nodes;
		this.mapShape = mapShape;
	}

	public Map<Coordinate, ServerMapNode> getNodes() {
		return nodes;
	}

	public void setNodes(Map<Coordinate, ServerMapNode> nodes) {
		this.nodes = nodes;
	}

	public EMapShape getMapShape() {
		return mapShape;
	}

	public void setMapShape(EMapShape mapShape) {
		this.mapShape = mapShape;
	}

}
