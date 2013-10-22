import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class MemoryBot extends ActuallyRandomBot {
	private Grid<Object> grid;
	private Object obj = new Object();

	private int countNearby(Location location) {
		int total = 0;
		for (Location adjacent : getGrid().getEmptyAdjacentLocations(location))
			if (grid.get(adjacent) == null)
				total++;
		return total;
	}

	@Override
	protected boolean isValid(Location location) {
		if (grid == null)
			grid = new BoundedGrid<Object>(getGrid().getNumRows(), getGrid().getNumCols());
		if (countNearby(location) == 1)
			grid.put(location, obj);
		return super.isValid(location) && grid.get(location) == null;
	}
}