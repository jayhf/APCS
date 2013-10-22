public class RightHandBot extends Robot {
	@Override
	public void act() {
		int right = Direction.right(getDirection());
		if (canMove(getLocation().getAdjacentLocation(right)))
			setDirection(right);
		else if (canMove())
			move();
		else
			setDirection(Direction.left(getDirection()));
	}
}
