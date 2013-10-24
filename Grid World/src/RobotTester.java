public class RobotTester {
	public static void main(String[] args) {
		RobotWorld world = RobotWorld.buildMaze("maze1", new PathFindingBot());
		world.show();
	}
}
