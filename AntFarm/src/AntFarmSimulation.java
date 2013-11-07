import info.gridworld.actor.ActorWorld;

public class AntFarmSimulation {
	public static void main(String[] args) {
		ActorWorld world = new ActorWorld();
		world.add(new WorkerAnt());
		world.add(new WorkerAnt());
		world.add(new WorkerAnt());
		world.add(new AntHill());
		world.add(new Cake());
		world.add(new SugarCube());
		world.show();
	}
}
