import java.util.Scanner;

public class Rogue implements MoveFinder {
	private LinkedGraph<Site> graph;
	private Scanner s = new Scanner(System.in);
	
	public Rogue(Dungeon dungeon) {
		graph = MonsterRogueUtils.parseDungeon(dungeon);
	}
	
	@Override
	public String getDisplayData() {
		return "R";
	}
	
	@Override
	public Site move(Site monster, Site rogue) {
		// return new Site(s.nextInt() + rogue.row(), s.nextInt() + rogue.col());
		// return rogue;// new FunSite(rogue.row(), rogue.col());
		return MonsterRogueUtils.shortestPaths(rogue, monster, graph).get(0).get(1);
	}
	
}
