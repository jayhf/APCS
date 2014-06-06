import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class JayRogue implements MoveFinder {
	private Set<Site> cycleSites = null;
	private Dungeon dungeon;
	private LinkedGraph<Site> graph;

	public JayRogue(Dungeon dungeon) {
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
		if (cycleSites.contains(rogue) && monsterDistances.get(rogue) == 1) {
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
		else {
			Site bestSite = null;
			int bestDifference = Integer.MIN_VALUE;
			int greatestMonsterDistance = Integer.MIN_VALUE;
			for (Site site : cycleSites.size() > 0 ? cycleSites : graph) {
				int monsterDistance = monsterDistances.get(site);
				int difference = monsterDistance - rogueDistances.get(site);
				if (bestDifference < 1 && difference > bestDifference) {
					bestDifference = Math.max(bestDifference, difference);
					greatestMonsterDistance = monsterDistance;
					bestSite = site;
				}
				else if (difference > 0 && monsterDistance > greatestMonsterDistance) {
					greatestMonsterDistance = monsterDistance;
					bestSite = site;
				}
			}
			if (bestDifference <= 0 && cycleSites.size() > 0)
				for (Site site : graph) {
					int monsterDistance = monsterDistances.get(site);
					int difference = monsterDistance - rogueDistances.get(site);
					if (bestDifference < 1 && difference > bestDifference) {
						bestDifference = Math.max(bestDifference, difference);
						greatestMonsterDistance = monsterDistance;
						bestSite = site;
					}
					else if (difference > 0 && monsterDistance > greatestMonsterDistance) {
						greatestMonsterDistance = monsterDistance;
						bestSite = site;
					}
				}
			return MonsterRogueUtils.bestMove(rogue, bestSite, graph);
		}
	}
}