import info.gridworld.grid.Location;

public class Direction extends Location {
	public static int left(int direction) {
		return (direction + 270) % 360;
	}

	public static int right(int direction) {
		return (direction + 90) % 360;
	}

	private Direction() {
		super(0, 0);
	}
}
