import info.gridworld.grid.Location;

import java.util.HashSet;

/**
 * MemoryBot is an ActuallyRandomBot that avoids dead ends, by keeping track of all of the dead ends it has been to.
 * 
 * @see ActuallyRandomBot
 * @see Robot
 * @author Jay Fleischer
 * @version 1.0 (10-27-13)
 */
public class MemoryBot extends ActuallyRandomBot {
	HashSet<Location> deadEnds = new HashSet<Location>();

	private int countNearby(Location location) {
		int total = 0;
		for (Location adjacent : getGrid().getValidAdjacentLocations(location))
			if (isValid(adjacent))
				total++;
		return total;
	}

	@Override
	protected boolean isValid(Location location) {
		return super.isValid(location) && !deadEnds.contains(location);
	}

	@Override
	public void move() {
		if (countNearby(getLocation()) == 1)
			deadEnds.add(getLocation());
		super.move();
	}
}