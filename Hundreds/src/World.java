import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class World implements JayACMCanvas.Paintable {
	public static final double dt = .01;
	private List<Bubble> bubbles = new ArrayList<>();
	private Bubble clicked = null;
	private Mouse mouse;

	public World(Mouse mouse) {
		this.mouse = mouse;
	}

	public void add(Bubble bubble) {
		bubbles.add(bubble);
	}

	@Override
	public void paint(Graphics2D g) {
		for (Bubble bubble : bubbles)
			bubble.paint(g);
	}

	public boolean update() {
		if (mouse.isDown()) {
			if (clicked != null)
				clicked.processClick(mouse);
			else
				for (Bubble bubble : bubbles)
					if (mouse.isClicked(bubble)) {
						(clicked = bubble).processClick(mouse);
						break;
					}
		}
		else if (clicked != null) {
			clicked.processClick(mouse);
			clicked = null;
		}
		for (Bubble bubble : bubbles)
			bubble.update();
		for (int i = 0; i < bubbles.size(); i++)
			for (int i2 = i + 1; i2 < bubbles.size(); i2++)
				if (bubbles.get(i).checkCollision(bubbles.get(i2)))
					if (bubbles.get(i).bounce(bubbles.get(i2)))
						return false;
		double number = 0;
		for (Bubble bubble : bubbles)
			number += bubble.getNumber();
		return number <= 100;
	}
}
