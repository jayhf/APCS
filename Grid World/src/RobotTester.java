public class RobotTester {
	public static void main(String[] args) {
		RobotWorld world = RobotWorld.buildMaze("maze5", new PathFindingBot());
		world.show();
	}
}
