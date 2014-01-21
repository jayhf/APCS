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
 * @version 1.0 (1-20-14)
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

	public StarProgram() {}

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
		String[] fileNames = "BigDipper,Bootes,Cas,Cyg,Gemini,Hydra,UrsaMajor,UrsaMinor".split(",");
		for (String fileName : fileNames)
			try {
				Scanner s = new Scanner(new File(fileName + "_lines.txt"));
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
				double x = line.nextDouble(), y = line.nextDouble();
				line.nextDouble();
				int draperNumber = line.nextInt();
				double magnitude = line.nextDouble();
				line.nextInt();
				Star star = new Star(x, y, magnitude);
				if (line.hasNextLine()) {
					String[] names = line.nextLine().substring(1).split("; ");
					sky.addStar(star, draperNumber, names);
				}
				else
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
