import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

public class Mouse extends MouseInputAdapter {
	private boolean down;
	private int x, y;

	public int getX() {
		return x;
	}

	public int getY() {
		return JayACMCanvas.SCREEN_HEIGHT - y;
	}

	public boolean isClicked(Bubble bubble) {
		double dx = bubble.getX() - getX(), dy = bubble.getY() - getY(), radius = bubble.getRadius();
		return down && dx * dx + dy * dy < radius * radius;
	}

	public boolean isDown() {
		return down;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		if (e.getButton() == MouseEvent.BUTTON1)
			down = true;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		if (e.getButton() == MouseEvent.BUTTON1)
			down = false;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		if (e.getButton() == MouseEvent.BUTTON1)
			down = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		if (e.getButton() == MouseEvent.BUTTON1)
			down = false;
	}
}
