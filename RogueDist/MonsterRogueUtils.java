import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;
import java.util.Queue;

public class MonsterRogueUtils {
	public static List<Site> adjacentSites(Site site) {
		int c = site.col();
		int r = site.row();
		return new LinkedList<Site>(Arrays.asList(new Site[] { new Site(r + 1, c + 1), new Site(r - 1, c + 1),
				new Site(r - 1, c - 1), new Site(r + 1, c - 1), new Site(r + 1, c),
				new Site(r, c + 1), new Site(r - 1, c), new Site(r, c - 1) }));
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
		HashMap<Site, Integer> distancesAway = new HashMap<Site, Integer>();
		distancesAway.put(start, 0);
		Queue<Site> nearby = new LinkedList<Site>();
		Queue<Site> nextToCheck = new LinkedList<Site>();
		nextToCheck.add(start);
		boolean resultFound = false;
		distancesAway.put(start, 0);
		int currentDepth = -1;
		while (!nextToCheck.isEmpty() && !resultFound) {
			nearby = nextToCheck;
			nextToCheck = new LinkedList<Site>();
			currentDepth++;
			while (!nearby.isEmpty()) {
				Site site = nearby.poll();
				distancesAway.put(site, currentDepth);
				if (site.equals(finish)) {
					System.out.println(currentDepth);
					resultFound = true;
					break;
				}
				else
					for (Site adjacent : sites.adjacentTo(site))
						if (!distancesAway.containsKey(adjacent))
							nextToCheck.add(adjacent);
			}
		}
		int[][] distances = new int[10][10];
		for (Entry<Site, Integer> e : distancesAway.entrySet()) {
			Site s = e.getKey();
			distances[s.row()][s.col()] = e.getValue();
		}
		for (int r = 0; r < 10; r++) {
			for (int c = 0; c < 10; c++)
				System.out.print(distances[r][c]);
			System.out.println();
		}
		if (resultFound) {
			LinkedList<LinkedList<Site>> pathList = new LinkedList<LinkedList<Site>>();
			pathList.add(new LinkedList<Site>(Arrays.asList(finish)));
			for (currentDepth--; currentDepth >= 0; currentDepth--) {
				ListIterator<LinkedList<Site>> itr = pathList.listIterator();
				while (itr.hasNext()) {
					LinkedList<Site> path = itr.next();
					List<Site> adjacentSites = adjacentSites(path.getFirst());
					Iterator<Site> adjacentItr = adjacentSites.iterator();
					while (adjacentItr.hasNext()) {
						Site adjacent = adjacentItr.next();
						if (!distancesAway.containsKey(adjacent) || distancesAway.get(adjacent) != currentDepth)
							adjacentItr.remove();
					}
					for (int i = 0; i < adjacentSites.size() - 1; i++) {
						LinkedList<Site> newPath = new LinkedList<Site>(adjacentSites);
						newPath.addFirst(adjacentSites.get(i));
						itr.add(newPath);
					}
					path.addFirst(adjacentSites.get(adjacentSites.size() - 1));
				}
			}
			System.out.println(pathList);
			return pathList;
		} else
			return null;
	}
}
