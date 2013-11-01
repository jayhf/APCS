import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * The main Robot class that all robots inherit from. It has a few basic helper methods for all robots.
 * 
 * @author Jay Fleischer
 * @version 1.0 (10-27-13)
 */
public abstract class Robot extends Bug {
	private boolean foundTreasure = false;
	protected Random random = new Random();

	public boolean canEverMove(Location location) {
		Grid<Actor> gr = getGrid();
		if (gr == null || !gr.isValid(location))
			return false;
		Actor neighbor = gr.get(location);
		return neighbor == null || neighbor instanceof Flower || neighbor instanceof Robot;
	}

	public boolean canMove(Location location) {
		Grid<Actor> gr = getGrid();
		if (gr == null || !gr.isValid(location))
			return false;
		Actor neighbor = gr.get(location);
		return neighbor == null || neighbor instanceof Flower;
	}

	public boolean canMove(Location location, Location previous) {
		return canMove(location);
	}

	protected void foundTreasure() {
		foundTreasure = true;
	}

	public List<Location> getValidAdjacentOptions() {
		return getValidAdjacentOptions(getLocation());
	}

	public List<Location> getValidAdjacentOptions(Location location) {
		List<Location> locations = getGrid().getValidAdjacentLocations(location);
		Iterator<Location> locationItr = locations.iterator();
		while (locationItr.hasNext())
			if (!canMove(locationItr.next()))
				locationItr.remove();
		return locations;
	}

	protected boolean hasFoundTreasure() {
		return foundTreasure;
	}

	@Override
	public void move() {
		if (getGrid().get(getLocation().getAdjacentLocation(getDirection())) instanceof Treasure)
			foundTreasure();
		super.move();
	}
}
