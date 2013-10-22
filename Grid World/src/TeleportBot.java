import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class TeleportBot extends Robot {
	@Override
	public void act() {
		if (!hasFoundTreasure()) {
			Grid<Actor> grid = getGrid();
			Location location = getLocation();
			for (Location l : grid.getOccupiedLocations())
				if (grid.get(l) instanceof Treasure)
					location = l;
			removeSelfFromGrid();
			grid.get(location).removeSelfFromGrid();
			putSelfInGrid(grid, location);
			foundTreasure();
		} else
			turn();
	}
}
