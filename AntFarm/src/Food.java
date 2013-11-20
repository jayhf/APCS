import info.gridworld.actor.Actor;

import java.util.Random;

/**
 * The superclass of all foods. Different foods have different sizes and these are used to determine the weight of each
 * food, with some randomness.
 * 
 * @see Cake
 * @see SugarCube
 * @author Jay Fleischer
 * @version 11-19-13 (1.0)
 */
public abstract class Food extends Actor implements Processable {
	private static Random random = new Random();
	private int weightLeft;

	public Food(int size) {
		setColor(null);
		weightLeft = (int) (size * Math.abs(random.nextGaussian()));
	}

	@Override
	public void act() {
		if (weightLeft == 0)
			removeSelfFromGrid();
	}

	@Override
	public void process(WorkerAnt ant) {
		if (!ant.hasFood() && weightLeft > 0) {
			ant.shareFoodLocation(getLocation());
			int foodTaken = Math.min(weightLeft, ant.getWeight() * 20);
			ant.takeFood(foodTaken);
			weightLeft -= foodTaken;
		}
	}

	@Override
	public String toString() {
		return "Food [weightLeft=" + weightLeft + ", toString()=" + super.toString() + "]";
	}
}
