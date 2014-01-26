import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * A class that represents the Sky. It can hold Constellations and Stars
 * 
 * @see Constellation
 * @see Star
 * @author Jay
 * @version 1.1 (1-25-14)
 */
public class Sky implements JayACMCanvas.Paintable, KeyListener {
	private Collection<Constellation> constellations;
	private TransformationMatrix coordinateTransformationMatrix, screenTransformationMatrix;
	private boolean qDown, wDown, eDown, aDown, sDown, dDown, backVisible;
	private TransformationMatrix qMatrix = new TransformationMatrix().rotatez(.01),
			eMatrix = new TransformationMatrix().rotatez(-.01),
			aMatrix = new TransformationMatrix().rotatey(.01),
			dMatrix = new TransformationMatrix().rotatey(-.01),
			wMatrix = new TransformationMatrix().rotatex(-.01),
			sMatrix = new TransformationMatrix().rotatex(.01);
	private Map<String, Integer> starNames;
	private Map<Integer, Star> stars;

	/**
	 * Creates a Sky
	 */
	public Sky() {
		constellations = new LinkedList<Constellation>();
		stars = new HashMap<Integer, Star>();
		starNames = new HashMap<String, Integer>();
		screenTransformationMatrix = new TransformationMatrix()
				.scale(JayACMCanvas.SCREEN_WIDTH / 2.0, JayACMCanvas.SCREEN_HEIGHT / 2.0, 0)
				.translate(1, 1, 1)
				.scale(1, -1, 1);
		coordinateTransformationMatrix = new TransformationMatrix();
	}

	/**
	 * Adds a Constellation to the sky
	 * 
	 * @param constellation
	 *            - The Constellation to add
	 */
	public void addConstellation(Constellation constellation) {
		synchronized (constellations) {
			constellations.add(constellation);
		}
	}

	/**
	 * Adds a Star to the sky
	 * 
	 * @param star
	 *            - The Star to add
	 * @param draperNumber
	 *            - The Star's Draper Number
	 * @param names
	 *            - The Star's names, if any
	 */
	public void addStar(Star star, int draperNumber, String... names) {
		star.setScreenTransformationMatrix(screenTransformationMatrix);
		star.setTransformationMatrix(coordinateTransformationMatrix);
		synchronized (stars) {
			stars.put(draperNumber, star);
		}
		synchronized (starNames) {
			for (String name : names)
				starNames.put(name, draperNumber);
		}
	}

	/**
	 * Returns the Star with the given name, if there is one
	 * 
	 * @param name
	 *            - the name of the Star
	 * @return - the Star or null, if no such star exists
	 */
	public Star findStar(String name) {
		return stars.get(starNames.get(name));
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_Q)
			qDown = true;
		else if (e.getKeyCode() == KeyEvent.VK_W)
			wDown = true;
		else if (e.getKeyCode() == KeyEvent.VK_E)
			eDown = true;
		else if (e.getKeyCode() == KeyEvent.VK_A)
			aDown = true;
		else if (e.getKeyCode() == KeyEvent.VK_S)
			sDown = true;
		else if (e.getKeyCode() == KeyEvent.VK_D)
			dDown = true;
		else if (e.getKeyCode() == KeyEvent.VK_SPACE)
			backVisible = !backVisible;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_Q)
			qDown = false;
		else if (e.getKeyCode() == KeyEvent.VK_W)
			wDown = false;
		else if (e.getKeyCode() == KeyEvent.VK_E)
			eDown = false;
		else if (e.getKeyCode() == KeyEvent.VK_A)
			aDown = false;
		else if (e.getKeyCode() == KeyEvent.VK_S)
			sDown = false;
		else if (e.getKeyCode() == KeyEvent.VK_D)
			dDown = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	/**
	 * Draws the stars and constellations onto the graphics passed
	 * 
	 * @param g
	 *            - the graphics to draw on
	 */
	@Override
	public void paint(Graphics2D g) {
		if (qDown)
			coordinateTransformationMatrix.prepend(qMatrix);
		if (eDown)
			coordinateTransformationMatrix.prepend(eMatrix);
		if (aDown)
			coordinateTransformationMatrix.prepend(aMatrix);
		if (dDown)
			coordinateTransformationMatrix.prepend(dMatrix);
		if (wDown)
			coordinateTransformationMatrix.prepend(wMatrix);
		if (sDown)
			coordinateTransformationMatrix.prepend(sMatrix);
		synchronized (stars) {
			for (Star star : stars.values())
				star.updateVisibility(backVisible);
		}
		g.setStroke(new BasicStroke(6, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		synchronized (constellations) {
			for (Constellation constellation : constellations)
				constellation.paint(g);
		}
		g.setColor(Color.WHITE);
		synchronized (stars) {
			for (Star star : stars.values())
				if (star.isVisible())
					star.paint(g);
		}
		synchronized (constellations) {
			for (Constellation constellation : constellations)
				constellation.paint(g);
		}
	}
}
