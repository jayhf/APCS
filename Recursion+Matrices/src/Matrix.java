/**
 * A matrix class that creates a random matrix. Elements can be added and it can be printed.
 * 
 * @author Jay Fleischer
 * @version 1.0 (9-23-13)
 */
public class Matrix {
	/**
	 * Tests the matrix
	 * 
	 * @param args
	 *            - not used
	 */
	public static void main(String[] args) {
		Matrix m = new Matrix();
		m.print();
		m.add(new int[] { 2, 4, 6 }, 1);
		m.print();
	}

	/**
	 * Creates a matrix of size 3 with fixed elements according to the problem description.
	 */
	private double[][] grid;

	public Matrix() {
		grid = new double[][] { { .5, .3, .123 }, { .75, .25, .55 }, { .1, .7, .23 } };
	}

	/**
	 * Creates a matrix with random elements and a random size
	 * 
	 * @param size
	 *            - the x and y size of the matrix
	 */
	public Matrix(int size) {
		grid = new double[size][size];
		for (int x = 0; x < size; x++)
			for (int y = 0; y < size; y++)
				grid[x][y] = Math.random();
	}

	/**
	 * Adds the given values to the input x coordinate
	 * 
	 * @param values
	 *            - the values to add
	 * @param x
	 *            - the column to add the values to
	 */
	public void add(int[] values, int x) {
		for (int y = 0; y < grid[x].length; y++)
			grid[x][y] += values[y];
	}

	/**
	 * Prints out the Matrix.
	 */
	public void print() {
		for (int y = 0; y < grid[0].length; y++) {
			for (double[] arr : grid)
				System.out.print(arr[y] + " ");
			System.out.println();
		}
	}
}
