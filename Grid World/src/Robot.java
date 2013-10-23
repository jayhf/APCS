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
		if (gr == null || !gr.isValid(location))
			return false;
		Actor neighbor = gr.get(location);
		return neighbor == null || neighbor instanceof Flower || neighbor instanceof Treasure;
	}

	protected void foundTreasure() {
		foundTreasure = true;
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
