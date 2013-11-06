import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 * RobotWorld is a utility class that helps load mazes with robots and treasure.
 * 
 * Multiple robots interact by either pretending other robots are walls or waiting until the other robot has moved. A
 * Robot will not be greedy and try to find more than one Treasure.
 * 
 * 
 * @see Robot
 * @see Treasure
 * @author Jay Fleischer
 * @version 1.0 (11-5-13)
 */
public class RobotWorld extends ActorWorld {
	/**
	 * Loads the maze with the given name and places a Robot
	 * 
	 * @param name
	 *            - the name of the maze
	 * @param finder
	 *            - the Robot
	 * @return - the created RobotWorld
	 */
	public static RobotWorld buildMaze(String name, Robot finder) {
		try {
			Scanner s = new Scanner(new File("mazes/" + name + ".txt"));
			int y = 0, ymax;
			Grid<Actor> grid = new BoundedGrid<Actor>(ymax = s.nextInt(), s.nextInt());
			finder.putSelfInGrid(grid, new Location(s.nextInt(), s.nextInt()));
			new Treasure().putSelfInGrid(grid, new Location(s.nextInt(), s.nextInt()));
			while (s.hasNextLine() && y < ymax) {
				Location current = new Location(y, -1);
				String line = s.nextLine();
				if (line.length() > 0) {
					for (char c : line.toCharArray())
						if (grid.get(current = current.getAdjacentLocation(Location.EAST)) == null && c == '*')
							grid.put(current, new Wall());
					y++;
				}
			}
			s.close();
			return new RobotWorld(grid);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Loads the maze with the given name and adds extra robots and treasures.
	 * 
	 * @param name
	 *            - The name of the maze to load.
	 * @param finder
	 *            - The Robot to go on the Robot Location indicated by the maze file
	 * @param moreBots
	 *            - The extra Robots
	 * @param botLocations
	 *            - The extra Locations, in the same order as their corresponding Robots
	 * @param moreTreasure
	 *            - The extra Treasure Locations
	 * @return the created RobotWorld
	 */
	public static RobotWorld buildMaze(String name, Robot finder, List<Robot> moreBots, List<Location> botLocations,
			List<Location> moreTreasure) {
		RobotWorld world = buildMaze(name, finder);
		for (int i = 0; i < Math.min(botLocations.size(), moreBots.size()); i++)
			moreBots.get(i).putSelfInGrid(world.getGrid(), botLocations.get(i));
		for (Location location : moreTreasure)
			new Treasure().putSelfInGrid(world.getGrid(), location);
		return world;
	}

	private RobotWorld(Grid<Actor> actors) {
		super(actors);
	}
}
