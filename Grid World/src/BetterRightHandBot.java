/**
 * BetterRightHandBot is a robot that tries to keep its right hand on the wall, and works better than RightHandBot.
 * 
 * @see Robot
 * @author Jay Fleischer
 * @version 1.0 (10-27-13)
 */
public class BetterRightHandBot extends Robot {
	private boolean moveNext = false;

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
		boolean blocked = !canMove(), rightBlocked = !canMove(getLocation().getAdjacentLocation(right)), leftBlocked = !canMove(getLocation()
				.getAdjacentLocation(left));
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