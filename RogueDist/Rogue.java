import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class Rogue implements MoveFinder {
	private LinkedGraph<Site> graph;
	private Set<Site> cycleSites=null;
	public Rogue(Dungeon dungeon) {
		graph = MonsterRogueUtils.parseDungeon(dungeon);
	}
	
	@Override
	public String getDisplayData() {
		return "R";
	}
	
	@Override
	public Site move(Site monster, Site rogue) {
		if(cycleSites==null)
			cycleSites=MonsterRogueUtils.reachableCycleSites(rogue, graph);
		Map<Site,Integer> rogueDistances=MonsterRogueUtils.createDistanceMap(rogue, Collections.<Site>emptyList(), graph);
		Map<Site,Integer> monsterDistances=MonsterRogueUtils.createDistanceMap(monster, Collections.<Site>emptyList(), graph);
		if(!monsterDistances.containsKey(rogue))
			return rogue;
		Site bestSite=null;
		int bestDifference=Integer.MIN_VALUE;
		for(Site site:cycleSites.size()>0?cycleSites:graph){
			int difference=monsterDistances.get(site)-rogueDistances.get(site);
			if(difference>bestDifference){
				bestDifference=Math.max(bestDifference,difference);
				bestSite=site;
			}
		}
		if(rogue.equals(bestSite))
			return rogue;
		return MonsterRogueUtils.shortestPaths(rogue, bestSite, graph).get(0).get(1);
	}
}