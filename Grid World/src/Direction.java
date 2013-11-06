import info.gridworld.grid.Location;

/**
 * A class that adds some useful utility methods.
 * 
 * @see Location
 * @author Jay Fleischer
 * @version 1.0 (11-5-13)
 */
public class Direction {
	public static int back(int direction) {
		return (direction + 180) % 360;
	}

	public static int left(int direction) {
		return (direction + 270) % 360;
	}

	public static int right(int direction) {
		return (direction + 90) % 360;
	}
}
