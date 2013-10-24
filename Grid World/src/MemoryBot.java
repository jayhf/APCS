import info.gridworld.grid.Location;

import java.util.HashSet;

public class MemoryBot extends ActuallyRandomBot {
	HashSet<Location> deadEnds = new HashSet<Location>();

	private int countNearby(Location location) {
		int total = 0;
		for (Location adjacent : getGrid().getValidAdjacentLocations(location))
			if (isValid(adjacent))
				total++;
		System.out.println(total);
		return total;
	}

	@Override
	protected boolean isValid(Location location) {
		return super.isValid(location) && !deadEnds.contains(location);
	}

	@Override
	public void move() {
		System.out.println(deadEnds);
		if (countNearby(getLocation()) == 1)
			deadEnds.add(getLocation());
		super.move();
	}
}