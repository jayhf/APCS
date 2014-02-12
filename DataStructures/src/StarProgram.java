
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import acm.graphics.GCanvas;
import acm.program.GraphicsProgram;

/**
 * The main class that runs the Star Chart program and loads its resources
 * 
 * @see Sky
 * @author Jay
 * @version 1.1 (1-25-14)
 */
@SuppressWarnings("serial")
public class StarProgram extends GraphicsProgram {
	/**
	 * The main method that starts the program and then loads all of the resources.
	 * 
	 * @param args
	 *            - not used
	 */
	public static void main(String[] args) {
		final StarProgram program = new StarProgram();
		program.start();
		program.loadStars();
		program.loadConstellations();
	}

	private Sky sky;

	public StarProgram() {
		addKeyListeners(sky);
	}

	/**
	 * Initializes my canvas
	 */
	@Override
	public GCanvas createGCanvas() {
		JayACMCanvas img = new JayACMCanvas();
		sky = new Sky();
		img.startDrawLoop(sky, 20);
		return img;
	}

	/**
	 * Resizes the screen.
	 */
	@Override
	public void init() {
		setSize(JayACMCanvas.SCREEN_WIDTH, JayACMCanvas.SCREEN_HEIGHT);
	}

	private void loadConstellations() {
		File currentDirectory = new File(".");
		for (File file : currentDirectory.listFiles())
			if (file.getName().endsWith("_lines.txt"))
				try {
					Scanner s = new Scanner(file);
					List<String[]> lines = new LinkedList<String[]>();
					while (s.hasNextLine())
						lines.add(s.nextLine().split(","));
					sky.addConstellation(new Constellation(lines, sky));
					s.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
	}

	private void loadStars() {
		try {
			Scanner s = new Scanner(new File("stars.txt"));
			while (s.hasNextLine()) {
				Scanner line = new Scanner(s.nextLine());
				double x = line.nextDouble(), y = line.nextDouble(), z = line.nextDouble();
				int draperNumber = line.nextInt();
				double magnitude = line.nextDouble();
				line.nextInt();
				Star star = new Star(x, y, z, magnitude);
				if (line.hasNextLine()) {
					String[] names = line.nextLine().substring(1).split("; ");
					sky.addStar(star, draperNumber, names);
				} else
					sky.addStar(star, draperNumber);
				line.close();
			}
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
