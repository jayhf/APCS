import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MonsterRogueUtils {
	public static List<Site> adjacentSites(Site site) {
		int c = site.col();
		int r = site.row();
		return new ArrayList<Site>(Arrays.asList(new Site[] {new Site(r + 1, c + 1), new Site(r + 1, c),
				new Site(r + 1, c - 1),new Site(r, c - 1),new Site(r - 1, c - 1), new Site(r - 1, c),
				new Site(r - 1, c + 1),new Site(r, c + 1)}));
	}

	public static Map<Site, Integer> createDistanceMap(Site start, Collection<Site> finishSites, LinkedGraph<Site> sites) {
		HashMap<Site, Integer> distanceMap = new HashMap<Site, Integer>();
		distanceMap.put(start, 0);
		List<Site> nextToCheck = new LinkedList<Site>();
		nextToCheck.add(start);
		distanceMap.put(start, 0);
		int currentDepth = -1;
		while (!nextToCheck.isEmpty()) {
			List<Site> nearby = nextToCheck;
			nextToCheck = new LinkedList<Site>();
			currentDepth++;
			Iterator<Site> itr = nearby.iterator();
			while (itr.hasNext()) {
				Site site = itr.next();
				if (finishSites.contains(site))
					return distanceMap;
				else
					for (Site adjacent : sites.adjacentTo(site))
						if (!distanceMap.containsKey(adjacent)) {
							nextToCheck.add(adjacent);
							distanceMap.put(adjacent, currentDepth + 1);
						}
			}
		}
		return distanceMap;
	}

	public static int distance(Site start, Site finish, LinkedGraph<Site> sites) {
		Integer result = createDistanceMap(start, Collections.singletonList(finish), sites).get(finish);
		return result == null ? -1 : result;
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

	public static Set<Site> reachableCycleSites(Site start, LinkedGraph<Site> sites) {
		Set<Site> results = new HashSet<Site>();
		Set<Site> reachableSites = new HashSet<Site>();
		for (Entry<Site, Integer> set : createDistanceMap(start, Collections.<Site> emptyList(), sites).entrySet())
			reachableSites.add(set.getKey());
		Set<Site> checkList = new HashSet<Site>(reachableSites);
		while (!checkList.isEmpty()) {
			Iterator<Site> checkItr=checkList.iterator();
			Site current = checkItr.next();
			checkItr.remove();
			if(!reachableSites.contains(current))
				continue;
			List<Site> adjacentSites = new ArrayList<Site>(8);
			Iterator<Site> adjacent = sites.adjacentTo(current).iterator();
			while (adjacent.hasNext())
				adjacentSites.add(adjacent.next());
			adjacentSites.retainAll(reachableSites);
			List<Site> allAdjacents=adjacentSites(current);
			ListIterator<Site> itr=allAdjacents.listIterator();
			while(itr.hasNext()){
				if(!adjacentSites.contains(itr.next()))
					itr.set(null);
			}
			outer:
			for(int i=0;i<allAdjacents.size();i++){
				int numberAdjacent=0;
				boolean foundBreak=false;
				for(int i2=0;i2<allAdjacents.size();i2++){
					if(allAdjacents.get((i+i2)%allAdjacents.size())!=null){
						numberAdjacent++;
						if(foundBreak)
							continue outer;
					}
					else
						if(numberAdjacent>0)
							foundBreak=true;
				}
				if(numberAdjacent<=4){
					reachableSites.remove(current);
					checkList.addAll(adjacentSites);
					break;
				}
			}
		}
		ArrayList<Site> reachableSiteList = new ArrayList<Site>(reachableSites);
		for (int i = 0; i < reachableSiteList.size(); i++)
			for (int i2 = i + 1; i2 < reachableSiteList.size(); i2++) {
				Site s1 = reachableSiteList.get(i);
				Site s2 = reachableSiteList.get(i2);
				if (!results.contains(s1) || !results.contains(s2)) {
					LinkedList<LinkedList<Site>> paths = shortestPaths(s1, s2, sites);
					int distance = paths.get(0).size() - 1;
					boolean validLoop = distance > 1 && paths.size() > 1;
					for (int i3 = 0; i3 < paths.size() && validLoop; i3++)
						for (int i4 = i3 + 1; i4 < paths.size() && validLoop; i4++) {
							Set<Site> sitesInPaths = new HashSet<Site>();
							sitesInPaths.addAll(paths.get(i3));
							sitesInPaths.addAll(paths.get(i4));
							if (sitesInPaths.size() == distance * 2) {
								Site m1 = paths.get(i3).get(distance / 2);
								Site m2 = paths.get(i4).get(distance / 2);
								if (distance(m1, m2, sites) != distance)
									validLoop = false;
							}
							else
								validLoop = false;
						}
					if (validLoop)
						for (LinkedList<Site> path : paths)
							results.addAll(path);
				}
			}
		return results;
	}

	public static LinkedList<LinkedList<Site>> shortestPaths(Site start, Site finish, LinkedGraph<Site> sites) {
		Map<Site, Integer> distancesAway = createDistanceMap(start, Collections.singletonList(finish), sites);
		if (distancesAway.containsKey(finish)) {
			LinkedList<LinkedList<Site>> pathList = new LinkedList<LinkedList<Site>>();
			pathList.add(new LinkedList<Site>(Arrays.asList(finish)));
			for (int currentDepth = distancesAway.get(finish) - 1; currentDepth >= 0; currentDepth--) {
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
						LinkedList<Site> newPath = new LinkedList<Site>(path);
						newPath.addFirst(adjacentSites.get(i));
						itr.add(newPath);
					}
					path.addFirst(adjacentSites.get(adjacentSites.size() - 1));
				}
			}
			return pathList;
		} else
			return null;
	}
}
