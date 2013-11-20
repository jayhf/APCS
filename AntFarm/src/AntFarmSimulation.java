import info.gridworld.actor.ActorWorld;

/**
 * This class contains a main method that creates a world with 3 ants, an ant hill and some food.
 * 
 * @author Jay Fleischer
 * @version 11-19-13 (1.0)
 */
public class AntFarmSimulation {
	public static void main(String[] args) {
		ActorWorld world = new ActorWorld();
		world.add(new WorkerAnt());
		world.add(new WorkerAnt());
		world.add(new WorkerAnt());
		world.add(new AntHill(20.0));
		world.add(new Cake());
		world.add(new SugarCube());
		world.show();
	}
}
