import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

public class WorkerAnt extends Critter implements Processable {
	private int foodCarried;
	private Location foodLocation;
	private Location hillLocation;
	
	public WorkerAnt() {
		setColor(Color.BLACK);
		foodCarried = 0;
		setDirection((int) (Math.random() * 8) * 45);
		foodLocation = null;
		hillLocation = null;
	}
	
	@Override
	public ArrayList<Actor> getActors() {
		return getGrid().getNeighbors(getLocation());
	}
	
	@Override
	public ArrayList<Location> getMoveLocations() {
		int direction = getDirection();
		if (foodCarried > 0 && hillLocation != null)
			direction = getLocation().getDirectionToward(hillLocation);
		else if (foodCarried == 0 && foodLocation != null)
			direction = getLocation().getDirectionToward(foodLocation);
		
		ArrayList<Location> list = new ArrayList<Location>();
		Location towardsGoal = getLocation().getAdjacentLocation(direction);
		list.add(towardsGoal);
		if (getGrid().isValid(towardsGoal) && getGrid().get(towardsGoal) == null)
			return list;
		list.add(getLocation().getAdjacentLocation(direction + Location.HALF_RIGHT));
		list.add(getLocation().getAdjacentLocation(direction + Location.HALF_LEFT));
		for (Iterator<Location> itr = list.iterator(); itr.hasNext();) {
			Location location = itr.next();
			if (!getGrid().isValid(itr.next()) || getGrid().get(location) != null)
				itr.remove();
		}
		return list;
	}
	
	@Override
	public void makeMove(Location l) {
		setDirection(getLocation().getDirectionToward(l));
		moveTo(l);
	}
	
	@Override
	public void process(WorkerAnt ant) {
		ant.shareAntHillLocation(hillLocation);
		ant.shareFoodLocation(foodLocation);
	}
	
	@Override
	public void processActors(ArrayList<Actor> actors) {
		for (Actor actor : actors)
			if (actor instanceof WorkerAnt)
				process((WorkerAnt) actor);
	}
	
	public Location selectLocation(ArrayList<Location> locations) {
		return null;
	}
	
	@Override
	public Location selectMoveLocation(ArrayList<Location> locations) {
		return locations.get((int) (Math.random() * locations.size()));
	}
	
	public void shareAntHillLocation(Location hillLocation) {
		if (this.hillLocation == null)
			this.hillLocation = hillLocation;
	}
	
	public void shareFoodLocation(Location foodLocation) {
		if (this.foodLocation == null)
			this.foodLocation = foodLocation;
	}
	
	public int takeFood() {
		setColor(Color.BLACK);
		return foodCarried + (foodCarried = 0);
	}
	
	public void takeFood(int food) {
		setColor(Color.RED);
		foodCarried += food;
	}
	
	@Override
	public String toString() {
		return "WorkerAnt [foodLocation=" + foodLocation + ", foodCarried=" + foodCarried + ", hillLocation="
				+ hillLocation + ", toString()=" + super.toString() + "]";
	}
}
