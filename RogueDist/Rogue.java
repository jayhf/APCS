import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Rogue implements MoveFinder {
	private Set<Site> cycleSites = null;
	private Dungeon dungeon;
	private LinkedGraph<Site> graph;
	
	public Rogue(Dungeon dungeon) {
		graph = MonsterRogueUtils.parseDungeon(this.dungeon = dungeon);
	}
	
	@Override
	public String getDisplayData() {
		return "R";
	}
	
	@Override
	public Site move(Site monster, Site rogue) {
		if (cycleSites == null)
			cycleSites = MonsterRogueUtils.reachableCycleSites(rogue, graph);
		Map<Site, Integer> rogueDistances = MonsterRogueUtils.createDistanceMap(rogue, Collections.<Site> emptyList(),
				graph);
		Map<Site, Integer> monsterDistances = MonsterRogueUtils.createDistanceMap(monster,
				Collections.<Site> emptyList(), graph);
		if (!monsterDistances.containsKey(rogue))
			return rogue;
		if (cycleSites.contains(rogue)) {
			if (monsterDistances.get(rogue) == 1) {
				Set<Site> options = new HashSet<Site>(cycleSites);
				options.removeAll(graph.adjacentToList(monster));
				options.remove(monster);
				options.retainAll(graph.adjacentToList(rogue));
				Iterator<Site> itr = options.iterator();
				while (itr.hasNext())
					if (dungeon.isWall(itr.next()))
						itr.remove();
				if (options.isEmpty()) {
					options = new HashSet<Site>(cycleSites);
					options.remove(monster);
					options.retainAll(graph.adjacentToList(rogue));
					return options.iterator().next();
				}
				return options.iterator().next();
			}
			else
				return rogue;
		}
		else {
			Site bestSite = null;
			int bestDifference = Integer.MIN_VALUE;
			for (Site site : cycleSites.size() > 0 ? cycleSites : graph) {
				int difference = monsterDistances.get(site) - rogueDistances.get(site);
				if (difference > bestDifference) {
					bestDifference = Math.max(bestDifference, difference);
					bestSite = site;
				}
			}
			if (bestSite.equals(rogue))
				return rogue;
			return MonsterRogueUtils.shortestPaths(rogue, bestSite, graph).get(0).get(1);
		}
	}
}