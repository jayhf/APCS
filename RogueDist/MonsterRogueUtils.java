import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class MonsterRogueUtils {
	public static Iterable<Site> adjacentSites(Site site) {
		int r = site.col();
		int c = site.row();
		return Arrays.asList(new Site[] { new Site(r + 1, c + 1), new Site(r - 1, c + 1),
				new Site(r - 1, c - 1), new Site(r + 1, c - 1), new Site(r + 1, c),
				new Site(r, c + 1), new Site(r - 1, c), new Site(r, c - 1) });
	}
	
	public static LinkedGraph<Site> parseDungeon(Dungeon dungeon) {
		LinkedGraph<Site> graph = new LinkedGraph<Site>();
		for (int x = 0; x < dungeon.size(); x++)
			for (int y = 0; y < dungeon.size(); y++) {
				Site site = new Site(y, x);
				if (!dungeon.isWall(site))
					graph.addVertex(site);
			}
		System.out.println(adjacentSites(new Site(1, 2)));
		for (Site site : graph.vertices())
			for (Site adjacent : adjacentSites(site))
				if (dungeon.isLegalMove(site, adjacent)) {
					graph.addDirectedEdge(site, adjacent);
					System.out.println(site + "," + adjacent);
				}
		return graph;
	}
	
	public static LinkedList<Site> shortestPath(Site start, Site finish, LinkedGraph<Site> sites) {
		HashMap<Site, Site> visited = new HashMap<Site, Site>();
		visited.put(start, null);
		LinkedList<Site> nearby = new LinkedList<Site>();
		nearby.add(start);
		LinkedList<Site> result = new LinkedList<Site>();
		while (!nearby.isEmpty()) {
			Site site = nearby.pollFirst();
			if (site.equals(finish)) {
				Site previous = site;
				while (!visited.get(previous).equals(start)) {
					previous = visited.get(previous);
					result.addFirst(previous);
				}
				return result;
			}
			for (Site adjacent : sites.adjacentTo(site))
				if (!visited.containsKey(adjacent)) {
					nearby.add(adjacent);
					visited.put(adjacent, site);
				}
		}
		result.add(start);
		return result;
	}
}
