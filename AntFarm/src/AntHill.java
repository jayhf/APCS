import info.gridworld.actor.Actor;
import info.gridworld.grid.Location;

import java.util.ArrayList;
import java.util.Random;

public class AntHill extends Actor implements Processable {
	private static Random random = new Random();
	private int foodReturned, antsBorn = 0;
	
	public AntHill() {
		foodReturned = 0;
		setColor(null);
	}
	
	@Override
	public void act() {
		if (foodReturned > 10 && Math.log(foodReturned) * random.nextGaussian() > antsBorn) {
			antsBorn++;
			foodReturned = 0;
			ArrayList<Location> emptyLocations = getGrid().getEmptyAdjacentLocations(getLocation()), adjacentLocations = getGrid()
					.getValidAdjacentLocations(getLocation());
			for (int i = 0; emptyLocations.isEmpty() && i < 5; i++) {
				ArrayList<Location> newList = new ArrayList<Location>();
				for (Location location : adjacentLocations) {
					emptyLocations.addAll(getGrid().getEmptyAdjacentLocations(location));
					newList.addAll(getGrid().getOccupiedAdjacentLocations(location));
				}
				adjacentLocations.addAll(newList);
			}
			if (emptyLocations.isEmpty())
				return;
			new WorkerAnt().putSelfInGrid(getGrid(), emptyLocations.get(random.nextInt(emptyLocations.size())));
		}
		
	}
	
	@Override
	public void process(WorkerAnt ant) {
		ant.shareAntHillLocation(getLocation());
		foodReturned += ant.takeFood();
	}
	
	@Override
	public String toString() {
		return "AntHill [foodReturned=" + foodReturned + ", toString()=" + super.toString() + "]";
	}
}
