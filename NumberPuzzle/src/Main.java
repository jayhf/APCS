import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
	public static Board loadBoard(File file) throws FileNotFoundException {
		Scanner s = new Scanner(file);
		int size = s.nextInt();
		int[][] board = new int[size][size];
		for (int y = 0; y < size; y++)
			for (int x = 0; x < size; x++)
				board[x][y] = s.nextInt();
		return new Board(board);
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println(new Board(new int[][] { { 1, 2 }, { 3, 0 } }).isSolved());
		// System.out.println(new Board(new int[][] { { 8, 4, 7 }, { 1, 0, 6 }, { 3, 2, 5 } }).manhattan());
		// System.out.println(Solver.solve()));
		Board board = new Board(new int[][] { { 1, 2 }, { 3, 0 } }); // loadBoard(new File("files/puzzle45.txt"));
		/*
		 * System.out.println(board); System.out.println(board.neighbors());
		 * System.out.println(board.neighbors().iterator().next().neighbors());
		 */
		System.out.println(new Solver(board));// board.manhattan());
		// System.out.println(new Solver(board));
	}
}
