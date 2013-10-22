import info.gridworld.grid.Location;

import java.util.Iterator;
import java.util.List;

public class RandomBot extends Robot {
	@Override
	public void act() {
		if (canMove()) {
			System.out.println("A");
			if (getGrid().get(getLocation().getAdjacentLocation(getDirection())) instanceof Treasure)
				foundTreasure();
			move();
		} else if (!hasFoundTreasure()) {
			System.out.println("B");
			List<Location> options = getValidOptions(getGrid().getValidAdjacentLocations(getLocation()));
			setDirection(getLocation().getDirectionToward(
					options.get(options.size() > 1 ? random.nextInt(options.size()) : 0)));
		}
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
