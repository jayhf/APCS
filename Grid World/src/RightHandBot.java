/**
 * RightHandBot is a robot that tries to keep its right hand on the wall. It follows the specification.
 * 
 * @see Robot
 * @see BetterRightHandBot.
 * @author Jay Fleischer
 * @version 1.0 (11-5-13)
 */
public class RightHandBot extends Robot {
	/**
	 * Tries to keep its right hand on a wall but not very well.
	 * 
	 */
	@Override
	public void act() {
		int right = Direction.right(getDirection());
		boolean blocked = !canMove(), rightBlocked = !canMove(getLocation().getAdjacentLocation(right));
		if (!blocked && rightBlocked)
			move();
		else if (!rightBlocked) {
			if (blocked)
				setDirection(Direction.right(getDirection()));
			else
				move();
		} else
			setDirection(Direction.left(getDirection()));
	}
}
