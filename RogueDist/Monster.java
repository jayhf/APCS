import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;

public class Monster implements MoveFinder {
	private LinkedGraph<Site> graph;
	
	public Monster(Dungeon dungeon) {
		graph = MonsterRogueUtils.parseDungeon(dungeon);
	}
	
	@Override
	public String getDisplayData() {
		return "M";
	}
	
	@Override
	public Site move(Site monster, Site rogue) {
		Map<Site, Integer> s = MonsterRogueUtils.createDistanceMap(monster, Collections.<Site> emptyList(), graph);
		if (!s.containsKey(rogue))
			return monster;
		LinkedList<LinkedList<Site>> paths = MonsterRogueUtils.shortestPaths(monster, rogue, graph);
		LinkedList<Site> adjacentOptions = new LinkedList<Site>();
		for (LinkedList<Site> path : paths)
			adjacentOptions.add(path.get(1));
		int r = 0;
		int c = 0;
		for (Site adjacent : adjacentOptions) {
			r += adjacent.row() - monster.row();
			c += adjacent.col() - monster.col();
		}
		if (r > 1)
			r = 1;
		if (r < -1)
			r = -1;
		if (c > 1)
			c = 1;
		if (c < -1)
			c = -1;
		Site ideal = new Site(r + monster.row(), c + monster.col());
		if (graph.hasEdge(monster, ideal))
			return ideal;
		ideal = new Site(monster.row(), c + monster.col());
		if (graph.hasEdge(monster, ideal))
			return ideal;
		ideal = new Site(r + monster.row(), monster.col());
		if (graph.hasEdge(monster, ideal))
			return ideal;
		return monster;
	}
}
