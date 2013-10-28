import info.gridworld.grid.Location;

import java.util.Iterator;
import java.util.List;

/**
 * ActuallyRandomBot is a robot that moves completely randomly. It alternates between turning towards a random location
 * and moving to that location.
 * 
 * @see Robot
 * @author Jay Fleischer
 * @version 1.0 (10-27-13)
 */
public class ActuallyRandomBot extends Robot {
	protected boolean turnAct = false;

	@Override
	public void act() {
		turnAct = !turnAct;
		if (!hasFoundTreasure())
			if (turnAct) {
				List<Location> options = getValidOptions(getGrid().getValidAdjacentLocations(getLocation()));
				if (options.size() == 0)
					turnAct = !turnAct;
				else
					setDirection(getLocation().getDirectionToward(
							options.get(options.size() > 1 ? random.nextInt(options.size()) : 0)));
			} else if (canMove())
				move();
			else
				turnAct = !turnAct;
	}

	protected List<Location> getValidOptions(List<Location> locations) {
		Iterator<Location> itr = locations.iterator();
		while (itr.hasNext())
			if (!isValid(itr.next()))
				itr.remove();
		return locations;
	}

	protected boolean isValid(Location location) {
		return location.getDirectionToward(getLocation()) % 90 == 0 && canEverMove(location);
	}
}
