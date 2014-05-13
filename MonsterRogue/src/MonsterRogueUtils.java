import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class MonsterRogueUtils {
	public static Iterable<Site> adjacentSites(Site site) {
		return Arrays.asList(new Site[] { new Site(site.col() + 1, site.row() + 1),
				new Site(site.col() - 1, site.row() + 1), new Site(site.col() - 1, site.row() - 1),
				new Site(site.col() + 1, site.row() - 1), new Site(site.col() + 1, site.row()),
				new Site(site.col(), site.row() + 1), new Site(site.col() - 1, site.row()),
				new Site(site.col(), site.row() - 1) });
	}
	
	public static LinkedGraph<Site> parseDungeon(Dungeon dungeon) {
		LinkedGraph<Site> graph = new LinkedGraph<Site>();
		for (int x = 0; x < dungeon.size(); x++)
			for (int y = 0; y < dungeon.size(); y++) {
				Site site = new Site(y, x);
				if (!dungeon.isWall(site))
					graph.addVertex(site);
			}
		for (Site site : graph.vertices())
			for (Site adjacent : adjacentSites(site))
				if (dungeon.isLegalMove(site, adjacent))
					graph.addEdge(site, adjacent);
		return graph;
	}
	
	public static Site shortestPath(Site start, Site finish, LinkedGraph<Site> sites) {
		HashMap<Site, Site> visited = new HashMap<Site, Site>();
		visited.put(start, null);
		LinkedList<Site> nearby = new LinkedList<Site>();
		nearby.add(start);
		while (!nearby.isEmpty()) {
			Site site = nearby.pollFirst();
			if (site.equals(finish)) {
				Site previous = site;
				while (!visited.get(previous).equals(start))
					previous = visited.get(previous);
				return previous;
			}
			for (Site adjacent : sites.adjacentTo(site))
				if (!visited.containsKey(adjacent)) {
					nearby.add(adjacent);
					visited.put(adjacent, site);
				}
		}
		return start;
	}
}
