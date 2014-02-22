import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class BasicBubble extends Bubble {
	private boolean clicked = false;
	private double number = 0;

	public BasicBubble(double x, double y, double vx, double vy) {
		super(x, y, vx, vy);
	}

	@Override
	public double getMass() {
		return getRadius() * getRadius();
	}

	@Override
	public int getNumber() {
		return (int) number;
	}

	@Override
	public double getRadius() {
		return Math.abs(number) * 4 + 40;
	}

	@Override
	public void paint(Graphics2D g) {
		double radius = getRadius();
		g.setColor(clicked ? Color.RED : Color.GRAY);
		g.fill(new Ellipse2D.Double(x - radius, JayACMCanvas.SCREEN_HEIGHT - y - radius, 2 * radius, 2 * radius));
	}

	@Override
	public boolean processClick(Mouse mouse) {
		number += .1;
		double radius = getRadius();
		if (x > JayACMCanvas.SCREEN_WIDTH - radius)
			x = JayACMCanvas.SCREEN_WIDTH - radius;
		else if (x < radius)
			x = radius;
		if (y > JayACMCanvas.SCREEN_HEIGHT - radius)
			y = JayACMCanvas.SCREEN_HEIGHT - radius;
		else if (y < radius)
			y = radius;
		clicked = mouse.isDown();
		return true;
	}

	@Override
	public void update() {
		double radius = getRadius();
		x += vx * World.dt;
		y += vy * World.dt;
		if (x > JayACMCanvas.SCREEN_WIDTH - radius && vx > 0)
			vx *= -1;
		else if (x < radius && vx < 0)
			vx *= -1;
		if (y > JayACMCanvas.SCREEN_HEIGHT - radius && vy > 0)
			vy *= -1;
		else if (y < radius && vy < 0)
			vy *= -1;
		// if(mouse.isClicked(this))
	}
}
