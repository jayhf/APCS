import info.gridworld.grid.Location;

import java.util.HashMap;
import java.util.LinkedList;

public class FastNonRecursivePathFindingBot extends PathFindingBot {
	@Override
	protected LinkedList<Location> pathFind(HashMap<Location, Integer> minCosts, Location current, int currentCost) {
		LinkedList<Location> currentList = new LinkedList<>(getValidAdjacentOptions(current)), nextList = new LinkedList<>();
		Location treasure = null;
		while (!currentList.isEmpty()) {
			for (Location location : currentList)
				for (Location adjacent : getValidAdjacentOptions(location)) {
					Integer cost = minCosts.get(adjacent);
					if (cost == null || cost > minCosts.get(location) + 1) {
						minCosts.put(location, minCosts.get(location) + 1);
						nextList.push(adjacent);
					}
				}
			currentList = nextList;
			nextList = new LinkedList<>();
		}
	}
}
