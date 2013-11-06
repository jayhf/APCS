import info.gridworld.grid.Location;

import java.util.HashSet;

/**
 * MemoryBot is an ActuallyRandomBot that avoids dead ends, by keeping track of all of the dead ends it has been to.
 * 
 * @see ActuallyRandomBot
 * @see Robot
 * @author Jay Fleischer
 * @version 2.0 (11-5-13)
 */
public class MemoryBot extends ActuallyRandomBot {
	HashSet<Location> deadEnds = new HashSet<Location>();

	/**
	 * Overrides canMove so that the robot will not be able to go to a location that it has already visited and leads to
	 * a dead end.
	 */
	@Override
	public boolean canMove(Location location, Location previous) {
		return super.canMove(location, previous) && !deadEnds.contains(location);
	}

	private int countNearby(Location location) {
		return getValidAdjacentOptions(getLocation(), true).size();
	}

	/**
	 * Overrides move such that the robot remembers if it is currently at a dead end or a path that only leads to a dead
	 * end.
	 */
	@Override
	public void move() {
		if (countNearby(getLocation()) == 1)
			deadEnds.add(getLocation());
		super.move();
	}
}