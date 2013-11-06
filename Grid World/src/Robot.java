import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.util.Iterator;
import java.util.List;

/**
 * The main Robot class that all robots inherit from. It has a few helper methods for all robots.
 * 
 * @author Jay Fleischer
 * @version 2.0 (11-5-13)
 */
public abstract class Robot extends Bug {
	private boolean foundTreasure = false;

	/**
	 * Checks if the robot can move to this location.
	 * 
	 * @param location
	 *            - the Location to check
	 * @return whether or not the Robot can move to this location
	 */
	public boolean canMove(Location location) {
		return canMove(location, null);
	}

	/**
	 * Returns whether the robot can ever move to this location.
	 * 
	 * @param location
	 *            - The Location to check.
	 * @param ever
	 *            - whether or not to ignore other robots.
	 * @return whether or not the Robot can ever move to this Location
	 */
	public boolean canMove(Location location, boolean ever) {
		return canMove(location, null, ever);
	}

	/**
	 * canMove, where Robots block the path.
	 * 
	 * @param location
	 *            - the Location to check
	 * @param previous
	 *            - the previous Location of the robot
	 * @return whether or not the Robot can move to this Location
	 */
	public boolean canMove(Location location, Location previous) {
		return canMove(location, previous, false);
	}

	/**
	 * Determines whether or not the robot can move to the specified location from the specified previous location
	 * 
	 * @param location
	 *            - the new location the robot wants to go to.
	 * @param previous
	 *            - the location the robot is starting from. Pass null, if this does not matter.
	 * @param ever
	 *            - If true, returns whether the robot can ever move to this tile, ignoring a Robot in the tile already
	 * @return whether or not the Robot can move to this Location
	 */
	public boolean canMove(Location location, Location previous, boolean ever) {
		Grid<Actor> gr = getGrid();
		if (gr == null || !gr.isValid(location))
			return false;
		Actor neighbor = gr.get(location);
		if (previous != null && !(location.getDirectionToward(previous) % 90 == 0))
			return false;
		return neighbor == null || neighbor instanceof Flower || ever && neighbor instanceof Robot;
	}

	/**
	 * Tells the robot it has found the treasure and should stop moving.
	 */
	protected void foundTreasure() {
		foundTreasure = true;
	}

	/**
	 * @return all of the empty adjacent Locations to the current Location
	 */
	public List<Location> getValidAdjacentOptions() {
		return getValidAdjacentOptions(getLocation());
	}

	/**
	 * Finds all of the adjacent tiles to the passed Location that the robot can move to.
	 * 
	 * @param location
	 *            - the Location to check
	 * @return the List of valid Locations
	 */
	public List<Location> getValidAdjacentOptions(Location location) {
		return getValidAdjacentOptions(location, false);
	}

	/**
	 * Finds all of the adjacent tiles to the passed Location that the robot can move to.
	 * 
	 * @param location
	 *            - the Location to check
	 * @param ever
	 *            - whether or not to ignore Robots
	 * @return the List of valid Locations
	 */
	public List<Location> getValidAdjacentOptions(Location location, boolean ever) {
		List<Location> locations = getGrid().getValidAdjacentLocations(location);
		Iterator<Location> locationItr = locations.iterator();
		while (locationItr.hasNext())
			if (!canMove(locationItr.next(), location))
				locationItr.remove();
		return locations;
	}

	/**
	 * @return whether or not this Robot has found a Treasure
	 */
	public boolean hasFoundTreasure() {
		return foundTreasure;
	}

	/**
	 * The same as the super class move method, except it checks for a Treasure before moving and records that a
	 * Treasure has been found.
	 */
	@Override
	public void move() {
		if (getGrid().get(getLocation().getAdjacentLocation(getDirection())) instanceof Treasure)
			foundTreasure();
		super.move();
	}
}
