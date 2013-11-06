/**
 * BetterRightHandBot is a robot that tries to keep its right hand on the wall, and works better than RightHandBot, by
 * turning when there is an open spot on its right and a wall behind it.
 * 
 * @see Robot
 * @author Jay Fleischer
 * @version 1.0 (11-5-13)
 */
public class BetterRightHandBot extends Robot {
	private boolean moveNext = false;

	/**
	 * Moves one step, keeping its right hand on a wall.
	 */
	@Override
	public void act() {
		if (hasFoundTreasure())
			return;
		if (moveNext) {
			moveNext = false;
			move();
			return;
		}
		int right = Direction.right(getDirection()), left = Direction.left(getDirection());
		boolean blocked = !canMove();
		boolean rightBlocked = !canMove(getLocation().getAdjacentLocation(right));
		boolean leftBlocked = !canMove(getLocation().getAdjacentLocation(left));
		if (!rightBlocked
				&& !canMove(getLocation().getAdjacentLocation(right).getAdjacentLocation((getDirection() + 180) % 360))) {
			setDirection(right);
			moveNext = true;
		} else if (blocked && leftBlocked)
			setDirection(right);
		else if (blocked)
			setDirection(left);
		else
			move();
	}
}