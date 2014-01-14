import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import acm.program.GraphicsProgram;

@SuppressWarnings("serial")
public class StarProgram extends GraphicsProgram {
	public static void main(String[] args) {
		final StarProgram program = new StarProgram();
		program.start();
		new Thread() {
			{
				setDaemon(true);
			}
			
			@Override
			public void run() {
				while (program.isActive()) {
					program.draw();
					try {
						sleep(20);
					} catch (InterruptedException e) {
						break;
					}
				}
			}
		}.start();
		;
		try {
			program.loadStars();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	private GBufferedImage img;
	
	private Map<Integer, Star> stars = Collections.synchronizedMap(new HashMap<Integer, Star>());
	
	public void draw() {
		System.out.println("HI!");
		synchronized (stars) {
			for (Star star : stars.values())
				img.drawOval((int) (star.getX() * 500 + 500), (int) (star.getY() * 500 + 500),
						(int) star.getMagnitude(), (int) star.getMagnitude(), Color.WHITE);
		}
		img.done();
		super.repaint();
	}
	
	@Override
	public void init() {
		setSize(1000, 1000);
		img = new GBufferedImage(1000, 1000);
		add(img);
	}
	
	private void loadStars() throws FileNotFoundException {
		Scanner s = new Scanner(new File("stars.txt"));
		while (s.hasNextLine()) {
			Scanner line = new Scanner(s.nextLine());
			double x = line.nextDouble(), y = line.nextDouble();
			line.nextDouble();
			int draperNumber = line.nextInt();
			double magnitude = line.nextDouble();
			line.nextInt();
			List<String> nameList = null;
			if (line.hasNextLine()) {
				String[] names = line.nextLine().split("; ");
				nameList = Arrays.asList(names);
			}
			Star star = new Star(x, y, magnitude);
			System.out.println(x * 500 + 500 + " " + (y * 500 + 500));
			stars.put(draperNumber, star);
		}
	}
}
