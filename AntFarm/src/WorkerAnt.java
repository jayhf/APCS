import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * The most important Actor in this simulation. The WorkerAnt eats the food, gives it back to the ant hill and shares
 * it's knowledge with nearby ants. This includes telling ants about ant hills and food sources and when a food source
 * has run out of food. Ants have a certain weight, which is determined when they are born and determines how much food
 * they can carry.
 * 
 * @author Jay Fleischer
 * @version 11-19-13 (1.0)
 */
public class WorkerAnt extends Critter implements Processable {
	private static Random random = new Random();
	private int foodCarried;
	private Location foodLocation;
	private Location hillLocation;
	private Location previousFood = null;
	private int weight = (int) (1000 * (random.nextDouble() + .5));

	public WorkerAnt() {
		setColor(Color.BLACK);
		foodCarried = 0;
		setDirection(random.nextInt(8) * 45);
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
			if (!getGrid().isValid(location) || getGrid().get(location) != null)
				itr.remove();
		}
		return list;
	}

	public int getWeight() {
		return weight;
	}

	public boolean hasFood() {
		return foodCarried != 0;
	}

	@Override
	public void makeMove(Location l) {
		if (l == null) {
			setDirection(getDirection() + (random.nextBoolean() ? 45 : -45));
			return;
		}
		setDirection(getLocation().getDirectionToward(l));
		moveTo(l);
		if (l.equals(foodLocation)) {
			previousFood = foodLocation;
			foodLocation = null;
		}
	}

	/**
	 * Tells this nearby Ant about its hill and food locations and any places that used to have food, but nolonger do.
	 */
	@Override
	public void process(WorkerAnt ant) {
		ant.shareAntHillLocation(hillLocation);
		if (foodLocation != null)
			ant.shareFoodLocation(foodLocation);
		if (previousFood != null)
			ant.tellFoodGone(previousFood);
	}

	@Override
	public void processActors(ArrayList<Actor> actors) {
		for (Actor actor : actors)
			if (actor instanceof WorkerAnt)
				process((WorkerAnt) actor);
			else if (actor instanceof Processable)
				((Processable) actor).process(this);
	}

	@Override
	public Location selectMoveLocation(ArrayList<Location> locations) {
		return locations.isEmpty() ? null : locations.get(random.nextInt(locations.size()));
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

	public void takeFood(int weight) {
		setColor(Color.RED);
		foodCarried = weight;
	}

	private void tellFoodGone(Location previousFood) {
		if (foodLocation != null && foodLocation.equals(previousFood)) {
			foodLocation = null;
			this.previousFood = previousFood;
		}
	}

	@Override
	public String toString() {
		return "WorkerAnt [foodCarried=" + foodCarried + ", foodLocation=" + foodLocation + ", hillLocation="
				+ hillLocation + ", previousFood=" + previousFood + ", weight=" + weight + ", toString()="
				+ super.toString() + "]";
	}
}
