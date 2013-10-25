import info.gridworld.grid.Location;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PathFindingBot extends Robot {
	private boolean impossible = false;
	private LinkedList<Location> path = null;

	@Override
	public void act() {
		if (!hasFoundTreasure() && !impossible) {
			if (path == null) {
				path = pathFind(new HashMap<Location, Integer>(), getLocation(), 0);
				if (path == null) {
					impossible = true;
					return;
				} else
					path.pop();
			}
			int direction = getLocation().getDirectionToward(path.peek());
			if (getDirection() == direction) {
				move();
				path.pop();
			} else
				turn();
		}
	}

	protected List<Location> getValidAdjacentOptions(Location location) {
		List<Location> locations = getGrid().getValidAdjacentLocations(location);
		Iterator<Location> itr = locations.iterator();
		while (itr.hasNext()) {
			Location next = itr.next();
			if (!canMove(next) || location.getDirectionToward(next) % 90 != 0)
				itr.remove();
		}
		return locations;
	}

	protected LinkedList<Location> pathFind(HashMap<Location, Integer> minCosts, Location current, int currentCost) {
		LinkedList<Location> best = null;
		for (Location location : getValidAdjacentOptions(current)) {
			if (getGrid().get(location) instanceof Treasure) {
				LinkedList<Location> result = new LinkedList<Location>();
				result.push(location);
				result.push(current);
				return result;
			}
			if (!minCosts.containsKey(location))
				minCosts.put(location, currentCost + 1);
			if (currentCost < minCosts.get(location)) {
				LinkedList<Location> result = pathFind(minCosts, location, currentCost + 1);
				if (result != null && result.size() < (best == null ? Integer.MAX_VALUE : best.size()))
					best = result;
			}
		}
		if (best != null)
			best.push(current);
		return best;
	}
}
