import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class PathFindingBot extends Robot {
	private boolean impossible = false;

	@Override
	public void act() {
		if (!hasFoundTreasure() && !impossible) {
			int shortest = Integer.MAX_VALUE;
			Location best = null;
			for (Location location : getValidOptions(getGrid().getValidAdjacentLocations(getLocation()))) {
				int result = pathFind(new HashSet<Location>(Arrays.asList(getLocation())), getLocation(), getGrid());
				if (shortest > result) {
					shortest = result;
					best = location;
				}
			}
			if (best == null) {
				impossible = true;
				return;
			}
			int direction = getLocation().getDirectionToward(best);
			if (getDirection() == direction)
				move();
			else
				setDirection(direction);
		}
	}

	private List<Location> getValidOptions(List<Location> locations) {
		Iterator<Location> itr = locations.iterator();
		while (itr.hasNext())
			if (!isValid(itr.next()))
				itr.remove();
		return locations;
	}

	protected boolean isValid(Location location) {
		return location.getDirectionToward(getLocation()) % 90 == 0 && getGrid().get(location) != null;
	}

	@SuppressWarnings("unchecked")
	private int pathFind(HashSet<Location> previousLocations, Location current, Grid<Actor> grid) {
		int shortest = Integer.MAX_VALUE;
		for (Location location : getValidOptions(getGrid().getValidAdjacentLocations(current)))
			if (!previousLocations.contains(location)) {
				if (grid.get(location) instanceof Treasure) {
					System.out.println("HI");
					return 0;
				}
				HashSet<Location> copy = (HashSet<Location>) previousLocations.clone();
				copy.add(location);
				int result = pathFind(copy, location, grid);
				if (result < shortest)
					shortest = result;
			}
		return shortest == Integer.MAX_VALUE ? shortest : shortest + 1;
	}
}
