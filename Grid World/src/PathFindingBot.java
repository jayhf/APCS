import info.gridworld.grid.Location;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * A robot that recursively finds the best path to the treasure and stores it. It then follows the path.
 * 
 * @see Robot
 * @author Jay Fleischer
 * @version 2.0 (11-5-13)
 */
public class PathFindingBot extends Robot {
	private boolean impossible = false;
	private LinkedList<Location> path = null;
	private Location treasure = null;

	/**
	 * Moves one step or turns, going along the shortest path to the nearest treasure.
	 */
	@Override
	public void act() {
		if (!hasFoundTreasure() && !impossible) {
			if (path == null) {
				path = generatePath();
				if (path == null) {
					impossible = true;
					return;
				}
			}
			int direction = getLocation().getDirectionToward(path.peek());
			if (getDirection() == direction) {
				if (canMove()) {
					move();
					path.pop();
				} else if (treasure != null && !(getGrid().get(treasure) instanceof Treasure)) {
					treasure = null;
					path = pathFind(new HashMap<Location, Integer>(),
							new LinkedList<Location>(Arrays.asList(getLocation())), 0);
				}
			} else
				setDirection(direction);
		}
	}

	/**
	 * Generates a path to the nearest treasure.
	 * 
	 * @return the path, stored in a LinkedList of locations.
	 */
	protected LinkedList<Location> generatePath() {
		return pathFind(new HashMap<Location, Integer>(), new LinkedList<Location>(Arrays.asList(getLocation())), 0);
	}

	private LinkedList<Location> pathFind(HashMap<Location, Integer> minCosts, LinkedList<Location> checkNext,
			int currentCost) {
		LinkedList<Location> nextList = new LinkedList<Location>();
		for (Location location : checkNext)
			for (Location adjacent : getValidAdjacentOptions(location, true))
				if (!minCosts.containsKey(adjacent)) {
					minCosts.put(adjacent, currentCost);
					nextList.push(adjacent);
					if (getGrid().get(adjacent) instanceof Treasure) {
						LinkedList<Location> path = new LinkedList<Location>();
						path.add(treasure = adjacent);
						return path;
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
