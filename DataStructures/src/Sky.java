import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Sky implements GBufferedImage.Paintable {
	
	private List<Constellation> constellations;
	private Map<String, Integer> starNames;
	private Map<Integer, Star> stars;
	
	public Sky() {
		constellations = new LinkedList<Constellation>();
		stars = new HashMap<Integer, Star>();
		starNames = new HashMap<String, Integer>();
	}
	
	public void addConstellation(Constellation constellation) {
		constellations.add(constellation);
	}
	
	public void addStar(int draperNumber, Star star) {
		synchronized (stars) {
			stars.put(draperNumber, star);
		}
	}
	
	public void addStarName(String name, int number) {
		starNames.put(name, number);
	}
	
	public Star findStar(String name) {
		return stars.get(starNames.get(name));
	}
	
	@Override
	public void paint(Graphics2D g) {
		synchronized (stars) {
			g.setColor(Color.WHITE);
			for (Star star : stars.values())
				g.fill(new Ellipse2D.Double(star.getX() * 500 + 500, star.getY() * 500 + 500, Math.pow(
						star.getMagnitude() / 3, 2), Math.pow(star.getMagnitude() / 3, 2)));
		}
		g.setStroke(new BasicStroke(4));
		g.setColor(Color.BLUE);
		for (Constellation constellation : constellations)
			constellation.paint(g);
	}
}
