import info.gridworld.grid.Location;

import java.util.List;

public class ActuallyRandomBot extends RandomBot {
	private boolean turnAct = false;

	@Override
	public void act() {
		turnAct = !turnAct;
		if (!hasFoundTreasure())
			if (turnAct) {
				List<Location> options = getValidOptions(getGrid().getValidAdjacentLocations(getLocation()));
				setDirection(getLocation().getDirectionToward(
						options.get(options.size() > 1 ? random.nextInt(options.size()) : 0)));
			} else {
				if (getGrid().get(getLocation().getAdjacentLocation(getDirection())) instanceof Treasure)
					foundTreasure();
				move();
			}
	}
}
