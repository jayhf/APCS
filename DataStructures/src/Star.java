import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.Random;

/**
 * Represents a flickering star that can be drawn on my buffered image canvas.
 * 
 * @see Constellation
 * @author Jay
 * @version 1.0 (1-20-14)
 */
public class Star implements JayACMCanvas.Paintable {
	private static final Random random = new Random();
	private double x, y, magnitude, flicker = 2 * Math.PI, flickerSpeed = Math.pow(Math.abs(random
			.nextGaussian()), .25) / 5;

	/**
	 * Creates a star with the given coordinates and magnitude
	 * 
	 * @param x
	 *            - the x coordinate
	 * @param y
	 *            - the y coordinate
	 * @param magnitude
	 *            - the star's apparent magnitude
	 */
	public Star(double x, double y, double magnitude) {
		this.x = x;
		this.y = y;
		this.magnitude = magnitude;
	}

	/**
	 * @return the stars magnitude
	 */
	public double getMagnitude() {
		return magnitude;
	}

	/**
	 * @return the star's screen x coordinate
	 */
	public double getScreenX() {
		return (x + 1) * JayACMCanvas.SCREEN_WIDTH / 2;
	}

	/**
	 * @return the star's screen x coordinate
	 */
	public double getScreenY() {
		return JayACMCanvas.SCREEN_HEIGHT - (y + 1) * JayACMCanvas.SCREEN_HEIGHT / 2;
	}

	/**
	 * @return the star's actual x coordinate
	 */
	public double getX() {
		return x;
	}

	/**
	 * @return the star's actual y coordinate
	 */
	public double getY() {
		return y;
	}

	/**
	 * Paints the star onto the passed graphics
	 */
	@Override
	public void paint(Graphics2D g) {
		g.setColor(new Color(255, 255, 200));
		double radius = Math.pow(getMagnitude() / 3 + 1.2 + Math.sin(flicker += flickerSpeed) / 7.5, 1.5) / 2;
		g.fill(new Ellipse2D.Double(getScreenX() - radius, getScreenY() - radius, radius * 2, radius * 2));
		if (flicker > 2 * Math.PI)
			flicker -= 2 * Math.PI;
	}
}
