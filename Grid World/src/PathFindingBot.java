import info.gridworld.grid.Location;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * A robot that recursively finds the best path to the treasure and stores it. It then follows the path. It has a
 * strange bug where on the first turn it moves diagonally.
 * 
 * @see Robot
 * @author Jay Fleischer
 * @version 1.0 (10-27-13)
 */
public class PathFindingBot extends Robot {
	private boolean impossible = false;
	private LinkedList<Location> path = null;

	@Override
	public void act() {
		if (!hasFoundTreasure() && !impossible) {
			if (path == null) {
				path = pathFind(new HashMap<Location, Integer>(),
						new LinkedList<Location>(Arrays.asList(getLocation())), 0);
				if (path == null) {
					impossible = true;
					return;
				} else
					path.pop();
			}
			int direction = getLocation().getDirectionToward(path.peek());
			if (getDirection() == direction) {
				if (canMove()) {
					move();
					path.pop();
				}
			} else
				setDirection(direction);
		}
	}

	protected List<Location> getValidAdjacentOptions(Location location) {
		List<Location> locations = getGrid().getValidAdjacentLocations(location);
		Iterator<Location> itr = locations.iterator();
		while (itr.hasNext()) {
			Location next = itr.next();
			if (!canEverMove(next) || location.getDirectionToward(next) % 90 != 0)
				itr.remove();
		}
		return locations;
	}

	protected LinkedList<Location> pathFind(HashMap<Location, Integer> minCosts, LinkedList<Location> checkNext,
			int currentCost) {
		LinkedList<Location> nextList = new LinkedList<Location>();
		for (Location location : checkNext)
			for (Location adjacent : getValidAdjacentOptions(location)) {
				Integer cost = minCosts.get(adjacent);
				if (cost == null || cost > currentCost) {
					minCosts.put(adjacent, currentCost);
					nextList.push(adjacent);
					if (getGrid().get(adjacent) instanceof Treasure) {
						LinkedList<Location> path = new LinkedList<Location>();
						path.add(adjacent);
						return path;
					}
				}
			}
		if (nextList.isEmpty())
			return null;
		LinkedList<Location> path = pathFind(minCosts, nextList, currentCost + 1);
		if (path == null)
			return null;
		for (Location l : getValidAdjacentOptions(path.peek()))
			if (minCosts.get(l) == currentCost) {
				path.push(l);
				break;
			}
		return path;
	}
}
