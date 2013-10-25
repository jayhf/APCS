public class RobotTester {
	public static void main(String[] args) {
		RobotWorld world = RobotWorld.buildMaze("maze3", new PathFindingBot());
		world.show();
	}
}
