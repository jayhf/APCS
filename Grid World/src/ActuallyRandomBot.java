import info.gridworld.grid.Location;

import java.util.List;
import java.util.Random;

/**
 * ActuallyRandomBot is a robot that moves completely randomly. It alternates between turning towards a random location
 * and moving to that location.
 * 
 * @see Robot
 * @author Jay Fleischer
 * @version 2.0 (11-5-13)
 */
public class ActuallyRandomBot extends Robot {
	private Random random = new Random();
	protected boolean turnAct = false;

	/**
	 * Moves completely randomly, alternating between turning to an open location and moving in the direction it is
	 * facing.
	 */
	@Override
	public void act() {
		turnAct = !turnAct;
		if (!hasFoundTreasure())
			if (turnAct) {
				List<Location> options = getValidAdjacentOptions();
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
}
