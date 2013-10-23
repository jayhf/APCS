import info.gridworld.grid.Location;

import java.util.Iterator;
import java.util.List;

public class ActuallyRandomBot extends Robot {
	protected boolean turnAct = false;

	@Override
	public void act() {
		turnAct = !turnAct;
		if (!hasFoundTreasure())
			if (turnAct) {
				List<Location> options = getValidOptions(getGrid().getValidAdjacentLocations(getLocation()));
				setDirection(getLocation().getDirectionToward(
						options.get(options.size() > 1 ? random.nextInt(options.size()) : 0)));
			} else
				move();
	}

	protected List<Location> getValidOptions(List<Location> locations) {
		Iterator<Location> itr = locations.iterator();
		while (itr.hasNext())
			if (!isValid(itr.next()))
				itr.remove();
		return locations;
	}

	protected boolean isValid(Location location) {
		return location.getDirectionToward(getLocation()) % 90 == 0 && canMove(location);
	}
}
