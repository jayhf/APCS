import info.gridworld.actor.Actor;

import java.awt.Color;

public class AntHill extends Actor implements Processable {
	private int foodReturned;

	public AntHill() {
		foodReturned = 0;
		setColor(Color.BLACK);
	}

	@Override
	public void act() {
	}

	@Override
	public void process(WorkerAnt ant) {
		ant.shareAntHillLocation(getLocation());
		foodReturned+=ant.takeFood();
	}

	@Override
	public String toString() {
		return "AntHill [foodReturned=" + foodReturned + ", toString()=" + super.toString() + "]";
	}
}
