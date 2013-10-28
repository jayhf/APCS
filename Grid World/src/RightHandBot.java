/**
 * RightHandBot is a robot that tries to keep its right hand on the wall.
 * 
 * @see Robot
 * @author Jay Fleischer
 * @version 1.0 (10-27-13)
 */
public class RightHandBot extends Robot {
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
