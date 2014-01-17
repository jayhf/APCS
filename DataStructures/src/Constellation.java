import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.LinkedList;
import java.util.List;

public class Constellation {
	private List<Star[]> starPairs = new LinkedList<Star[]>();
	
	public Constellation(List<String[]> lines, Sky sky) {
		for (String[] namePairs : lines) {
			System.out.println(namePairs[0] + sky.findStar(namePairs[0]));
			starPairs.add(new Star[] { sky.findStar(namePairs[0]), sky.findStar(namePairs[1]) });
		}
	}
	
	public void paint(Graphics2D g) {
		for (Star[] starPair : starPairs)
			g.draw(new Line2D.Double(starPair[0].getX(), starPair[0].getY(), starPair[1].getX(), starPair[1].getY()));
	}
}
