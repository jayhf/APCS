import info.gridworld.actor.Actor;
import info.gridworld.grid.Location;

import java.util.ArrayList;
import java.util.Random;

/**
 * An Ant Hill that Ants return food to. It uses the food to create more ants.
 * 
 * @author Jay Fleischer
 * @version 11-19-13 (1.0)
 */
public class AntHill extends Actor implements Processable {
	private static Random random = new Random();
	private double antBirthProbabilityModifier;
	private int foodReturned, antsBorn = 0;

	/**
	 * A greater probability modifier makes it less likely that a new ant will be born. The chance that a new ant is
	 * born decreases with each new ant. AntHills share their locations with new ants.
	 * 
	 * @see WorkerAnt
	 */
	public AntHill(double antBirthProbabilityModifier) {
		foodReturned = 0;
		setColor(null);
		this.antBirthProbabilityModifier = antBirthProbabilityModifier;
	}

	@Override
	public void act() {
		if (foodReturned > 10 && Math.log(foodReturned) * Math.pow(Math.abs(random.nextGaussian()), 2) >
				antsBorn * antBirthProbabilityModifier) {
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
		return "AntHill [antBirthProbabilityModifier=" + antBirthProbabilityModifier + ", foodReturned=" + foodReturned
				+ ", antsBorn=" + antsBorn + ", toString()=" + super.toString() + "]";
	}
}
