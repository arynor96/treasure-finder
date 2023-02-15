package map;

import java.util.Objects;

// Taken from Client.
public class Coordinate {

	private int x;
	private int y;

	public Coordinate(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		return x == other.x && y == other.y;
	}

	@Override
	public String toString() {
		return "[x=" + x + ", y=" + y + "]";
	}

}
