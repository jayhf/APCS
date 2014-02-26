import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
	public static AbstractBoard loadAbstractBoard(File file) throws FileNotFoundException {
		Scanner s = new Scanner(file);
		int size = s.nextInt();
		int[][] board = new int[size][size];
		for (int y = 0; y < size; y++)
			for (int x = 0; x < size; x++)
				board[x][y] = s.nextInt();
		s.close();
		return new Board(board);
	}

	public static void main(String[] args) throws FileNotFoundException {
		// System.out.println(new AbstractBoard(new int[][] { { 1, 3 }, { 2, 0 } }).isSolved());
		// System.out.println(Solver.solve()));
		AbstractBoard board = loadAbstractBoard(new File("files/puzzle4x4-hard2.txt")); // new AbstractBoard(new int[][]
		// { {3,
		// 2}, {1, 0}}); //
		// loadAbstractBoard(new File("files/puzzle45.txt"));
		/*
		 * System.out.println(board); System.out.println(board.neighbors());
		 * System.out.println(board.neighbors().iterator().next().neighbors());
		 */
		for (int i = 0; i < 10; i++)
			System.out.println(new Solver(board));
		long time = System.nanoTime();
		for (int i = 0; i < 10; i++)
			System.out.println(new Solver(board));
		System.out.println((System.nanoTime() - time) * Math.pow(10, -9) / 10 + " seconds (average)");
	}
}
