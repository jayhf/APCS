
public class JayMonster implements MoveFinder {
	private LinkedGraph<Site> graph;

	public JayMonster(Dungeon dungeon) {
		graph = MonsterRogueUtils.parseDungeon(dungeon);
	}

	@Override
	public String getDisplayData() {
		return "M";
	}

	@Override
	public Site move(Site monster, Site rogue) {
		return MonsterRogueUtils.bestMove(monster, rogue, graph);
	}
}
