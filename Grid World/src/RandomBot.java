/**
 * RandomBot is an ActuallyRandomBot that moves forward whenever possible.
 * 
 * @author Jay Fleischer
 * @version 1.0 (10-27-13)
 */
public class RandomBot extends ActuallyRandomBot {
	@Override
	public void act() {
		if (canMove() && !turnAct)
			move();
		else
			super.act();
	}
}
