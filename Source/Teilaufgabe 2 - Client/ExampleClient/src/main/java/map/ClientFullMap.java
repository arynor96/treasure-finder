package map;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ClientFullMap {

	private Map<Coordinate, ClientMapNode> nodes = new HashMap<Coordinate, ClientMapNode>();
	private int length = 0;
	private int heigth = 0;

	public ClientFullMap() {
	}

	public ClientFullMap(Map<Coordinate, ClientMapNode> nodes) {
		this.nodes = nodes;

		if (this.length == 0)
			calculateDimension();
	}

	public Map<Coordinate, ClientMapNode> getNodes() {
		return nodes;
	}

	public int getLength() {
		return length;
	}

	public int getHeigth() {
		return heigth;
	}

	private int calculateDimension() {
		int maxX = 0;
		int maxY = 0;

		for (Coordinate c : nodes.keySet()) {
			if (c.getX() > maxX) {
				maxX = c.getX();
			}

			if (c.getY() > maxY) {
				maxY = c.getY();
			}
		}

		this.length = maxX;
		this.heigth = maxY;

		return length;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nodes);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClientFullMap other = (ClientFullMap) obj;
		return Objects.equals(nodes, other.nodes);
	}

}
