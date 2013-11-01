/**
 * RobotTester is a simple class for testing a robot on different mazes.
 * 
 * @author Jay Fleischer
 * @version 1.0 (10-27-13)
 */
public class RobotTester {
	public static void main(String[] args) {
		RobotWorld world = RobotWorld.buildMaze("maze1", new MemoryBot());
		world.show();
	}
}