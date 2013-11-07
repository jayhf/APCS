import info.gridworld.actor.Actor;

public abstract class Food extends Actor implements Processable {
	private int biteSize;

	public Food(int biteSize) {
		this.biteSize = biteSize;
		setColor(null);
	}

	@Override
	public void act() {
	}

	@Override
	public void process(WorkerAnt ant) {
		// TODO make ant take a bite and share the location
	}

	@Override
	public String toString() {
		return "Food [biteSize=" + biteSize + ", toString()=" + super.toString() + "]";
	}
}
