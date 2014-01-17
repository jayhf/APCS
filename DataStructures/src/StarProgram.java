import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import acm.program.GraphicsProgram;

@SuppressWarnings("serial")
public class StarProgram extends GraphicsProgram {
	public static void main(String[] args) {
		final StarProgram program = new StarProgram();
		program.start();
		program.loadStars();
		program.loadConstellations();
	}
	
	private Sky sky;
	
	@Override
	public void init() {
		setSize(1000, 1000);
		sky = new Sky();
		GBufferedImage img = new GBufferedImage(1000, 1000);
		add(img);
		img.startDrawLoop(sky);
	}
	
	private void loadConstellations() {
		String[] fileNames = { "Hydra_lines.txt" };
		for (String fileName : fileNames)
			try {
				Scanner s = new Scanner(new File(fileName));
				List<String[]> lines = new LinkedList<String[]>();
				while (s.hasNextLine())
					lines.add(s.nextLine().split(","));
				sky.addConstellation(new Constellation(lines, sky));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	}
	
	private void loadStars() {
		Scanner s = null;
		try {
			s = new Scanner(new File("stars.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
		while (s.hasNextLine()) {
			Scanner line = new Scanner(s.nextLine());
			double x = line.nextDouble(), y = line.nextDouble();
			line.nextDouble();
			int draperNumber = line.nextInt();
			double magnitude = line.nextDouble();
			line.nextInt();
			if (line.hasNextLine()) {
				String[] names = line.nextLine().split("; ");
				for (int i = 0; i < names.length; i++)
					sky.addStarName(names[i], draperNumber);
			}
			sky.addStar(draperNumber, new Star(x, y, magnitude));
		}
	}
}
