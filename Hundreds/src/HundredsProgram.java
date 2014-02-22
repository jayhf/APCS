import acm.graphics.GCanvas;
import acm.program.GraphicsProgram;

@SuppressWarnings("serial")
public class HundredsProgram extends GraphicsProgram {
	public static void main(String[] args) {
		new HundredsProgram().start();
	}

	private JayACMCanvas canvas;
	private Mouse mouse;
	private World world;

	public HundredsProgram() {
		mouse = new Mouse();
		world = new World(mouse);
		super.addMouseListeners(mouse);
		world.add(new BasicBubble(256, 256, 150, 50));
		world.add(new BasicBubble(500, 256, -350, 50));
		world.add(new BasicBubble(500, 500, 100, 170));
		world.add(new BasicBubble(256, 500, 180, 100));
	}

	@Override
	public GCanvas createGCanvas() {
		canvas = new JayACMCanvas();
		return canvas;
	}

	@Override
	public void init() {
		canvas.startDrawLoop(world, 10);
		setSize(JayACMCanvas.SCREEN_WIDTH + 14, JayACMCanvas.SCREEN_HEIGHT + 60);
	}

	@Override
	public void run() {
		long time = System.nanoTime();
		while (world.update())
			try {
				Thread.sleep(Math.max(0, (time - System.nanoTime()) / 1000000 + 10));
				time = System.nanoTime();
			} catch (InterruptedException e) {
				return;
			}
		System.exit(0);
	}
}
