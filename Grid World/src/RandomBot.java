/**
 * RandomBot is an ActuallyRandomBot that moves forward whenever possible.
 * 
 * @author Jay Fleischer
 * @version 1.0 (11-5-13)
 */
public class RandomBot extends ActuallyRandomBot {
	/**
	 * Moves forward whenever possible and uses ActuallyRandomBot's implementation when it is not.
	 */
	@Override
	public void act() {
		if (canMove() && !turnAct)
			move();
		else
			super.act();
	}
}
