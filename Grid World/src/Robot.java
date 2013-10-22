import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.util.Random;

public abstract class Robot extends Bug {
	private boolean foundTreasure = false;
	protected Random random = new Random();

	@Override
	public boolean canMove() {
		return canMove(getLocation().getAdjacentLocation(getDirection()));
	}

	public boolean canMove(Location location) {
		Grid<Actor> gr = getGrid();
		if (gr == null)
			return false;
		Location next = location;
		if (!gr.isValid(next))
			return false;
		Actor neighbor = gr.get(next);
		return neighbor == null || neighbor instanceof Flower || neighbor instanceof Treasure;
	}

	protected void foundTreasure() {
		foundTreasure = true;
	}

	protected boolean hasFoundTreasure() {
		return foundTreasure;
	}
}
