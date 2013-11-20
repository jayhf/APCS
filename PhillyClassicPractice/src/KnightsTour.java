/**
 * This is problem 6 on the philly classic from 2010, a hard year.
 * 
 * @author Jay
 * 
 */
public class KnightsTour {
	private static int[][] possibleMoves = new int[][] { {1, 2}, {2, 1}, {-1, 2}, {2, -1}, {-1, -2}, {-2, -1}, {-2, 1},
			{1, -2}};

	public static boolean KnightsToura(int height, int width, int startX, int startY) {
		return recursion(new boolean[width * height], height, width, startX, startY, 1);
	}

	public static void main(String[] args) {
		System.out.println(KnightsToura(8, 8, 0, 0));
	}

	public static boolean recursion(boolean[] area, int height, int width, int currentx, int currenty, int depth) {
		area[currenty * height + currentx] = true;
		for (int[] move : possibleMoves) {
			int newx = move[0] + currentx, newy = move[1] + currenty;
			if (newx >= 0 && newx < width && newy >= 0 && newy < height && !area[newy * height + newx]) {
				boolean[] copy = new boolean[area.length];
				System.arraycopy(area, 0, copy, 0, area.length);
				if (recursion(copy, height, width, currentx + move[0], currenty + move[1], depth + 1))
					return true;
			}
		}
		return width * height == depth;
	}
}