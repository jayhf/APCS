import java.util.Collections;
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
		Map<Site,Integer> s = MonsterRogueUtils.createDistanceMap(monster, Collections.<Site>emptyList(), graph);
		/*for (int r = 0; r < 10; r++) {
			for (int c = 0; c < 10; c++)
				System.out.print(s.contains(new Site(r, c)) ? 1 : " ");
			System.out.println();
		}
		System.out.println(MonsterRogueUtils.distance(monster, rogue, graph));*/
		if(!s.containsKey(rogue))
			return monster;
		return MonsterRogueUtils.shortestPaths(monster, rogue, graph).get(0).get(1);
	}
}
