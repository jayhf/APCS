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
		return MonsterRogueUtils.shortestPath(monster, rogue, graph).get(1);
	}
}
