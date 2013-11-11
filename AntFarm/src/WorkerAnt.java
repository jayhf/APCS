import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.util.ArrayList;

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
		return null;
	}
	
	@Override
	public void process(WorkerAnt ant) {
		ant.shareAntHillLocation(hillLocation);
		ant.shareFoodLocation(foodLocation);
	}
	
	public Location selectLocation(ArrayList<Location> locations) {
		
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
		return foodCarried + (foodCarried = 0);
	}
	
	public void takeFood(int food) {
		foodCarried += food;
	}
	
	@Override
	public String toString() {
		return "WorkerAnt [foodLocation=" + foodLocation + ", foodCarried=" + foodCarried + ", hillLocation="
				+ hillLocation + ", toString()=" + super.toString() + "]";
	}
	@Override
	public ArrayList<Location> getActors(){
		return getGrid().getOccupiedAdjacentLocations(getLocation());
	}
	@Override
	public void processActors(ArrayList<Location> actors){
		for(Actor actor:actors)
			if(actor instanceof WorkerAnt)
				process((WorkerAnt)actor);
	}
}