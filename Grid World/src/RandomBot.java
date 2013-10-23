public class RandomBot extends ActuallyRandomBot {
	@Override
	public void act() {
		if (canMove() && !turnAct)
			move();
		else
			super.act();
	}
}
