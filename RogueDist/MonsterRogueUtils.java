import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class MonsterRogueUtils {
	public static Iterable<Site> adjacentSites(Site site) {
		int c = site.col();
		int r = site.row();
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
		for (Site site : graph.vertices())
			for (Site adjacent : adjacentSites(site))
				if (dungeon.isLegalMove(site, adjacent))
					graph.addDirectedEdge(site, adjacent);
		return graph;
	}
	
	public static LinkedList<LinkedList<Site>> shortestPaths(Site start, Site finish, LinkedGraph<Site> sites) {
		LinkedList<LinkedList<Site>> pathList = new LinkedList<LinkedList<Site>>();
		HashMap<Site, LinkedList<Site>> visited = new HashMap<Site, LinkedList<Site>>();
		HashMap<Site, Integer> distancesAway = new HashMap<Site, Integer>();
		visited.put(start, null);
		Queue<Site> nearby = new LinkedList<Site>();
		Queue<Site> nextToCheck = new LinkedList<Site>();
		nearby.add(start);
		boolean resultFound = false;
		int currentDepth = -1;
		while (!nextToCheck.isEmpty() && !resultFound) {
			nearby = nextToCheck;
			nextToCheck = new LinkedList<Site>();
			currentDepth++;
			while (!nearby.isEmpty()) {
				Site site = nearby.poll();
				if (site.equals(finish)) {
					Site previous = site;
					LinkedList<Site> path = new LinkedList<Site>();
					path.add(finish);
					while (!visited.get(previous).equals(start)) {
						previous = visited.get(previous);
						path.addFirst(previous);
					}
					path.addFirst(start);
					pathList.add(path);
					visited.remove(finish);
					resultFound = true;
				}
				if (!resultFound)
					for (Site adjacent : sites.adjacentTo(site))
						if (!visited.containsKey(adjacent) || distancesAway.get(site)) {
							nextToCheck.add(adjacent);
							
							visited.put(adjacent, site);
						}
			}
		}
		System.out.println(pathList);
		return pathList;
	}
}
