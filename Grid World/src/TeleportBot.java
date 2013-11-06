import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

/**
 * TeleportBot is a robot that automagically teleports to the treasure.
 * 
 * @see Robot
 * @author Jay Fleischer
 * @version 1.0 (11-5-13)
 */
public class TeleportBot extends Robot {
	/**
	 * Searches for the treasure and uses an infinite improbability drive to teleport to it. It then spins in circles!
	 */
	@Override
	public void act() {
		if (!hasFoundTreasure()) {
			Grid<Actor> grid = getGrid();
			Location location = getLocation();
			for (Location l : grid.getOccupiedLocations())
				if (grid.get(l) instanceof Treasure)
					location = l;
			super.moveTo(location);
			foundTreasure();
		} else
			turn();
	}
}
