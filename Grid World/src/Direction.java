import info.gridworld.grid.Location;

/**
 * A class that adds some useful utility methods not available in Location
 * 
 * @see Location
 * @author Jay Fleischer
 * @version 1.0 (10-27-13)
 */
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
