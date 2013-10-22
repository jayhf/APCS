import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class PathFindingBot extends Robot {
	@Override
	public void act() {
		if (!hasFoundTreasure()) {
			int[] result = pathFind(new HashSet<Location>(), getLocation(), getGrid());
			int direction = getLocation().getDirectionToward(new Location(result[1], result[2]));
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
		return location.getDirectionToward(getLocation()) % 90 == 0;
	}

	@SuppressWarnings("unchecked")
	private int[] pathFind(HashSet<Location> previousLocations, Location current, Grid<Actor> grid) {
		int shortest = Integer.MAX_VALUE, xnext = -1, ynext = -1;
		for (Location location : getValidOptions(getGrid().getEmptyAdjacentLocations(current)))
			if (!previousLocations.contains(location)) {
				if (grid.get(location) instanceof Treasure)
					return new int[] { 0, location.getCol(), location.getRow() };
				HashSet<Location> copy = (HashSet<Location>) previousLocations.clone();
				int[] result = pathFind(copy, location, grid);
				if (result[0] < shortest) {
					shortest = result[0];
					xnext = result[1];
					ynext = result[2];
				}
			}
		return new int[] { shortest, xnext, ynext };
	}
}
