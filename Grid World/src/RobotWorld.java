import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RobotWorld extends ActorWorld {
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

	private RobotWorld(Grid<Actor> actors) {
		super(actors);
	}
}
