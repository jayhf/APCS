import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {
	public static Board loadBoard(File file) throws FileNotFoundException {
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
		// System.out.println(new Board(new int[][] { { 1, 3 }, { 2, 0 } }).isSolvable());
		// System.out.println(Solver.solve()));
		// write(Solver.scramble(AbstractBoard.solved(6), 200), new File("test.txt")); 79 moves
		// the optimal...)
		// Solver.solve(loadBoard(new File("files/puzzle45.txt")));
		AbstractBoard board = loadBoard(new File("files/puzzle4x4-hard2.txt"));// files/puzzle4x4-hard2
		// for (AbstractBoard b2 : b1.neighbors())
		// System.out.println(b2.manhattan());
		// System.out.println(board.manhattan());
		// for (AbstractBoard neighbor : board.neighbors())
		// System.out.println(neighbor.manhattan());
		System.out.println(new Solver(board, new ManhattanComparator()));
		// Solver.scramble(new Board(solvedState), 1000).write(new File("test.txt"));
		// System.out.println(new Solver(loadBoard(new File("test.txt"))));
		// System.out.println(board.isSolvable());
		
		for (int i = 0; i < 3; i++)
			System.out.println(new Solver(board));
		long time = System.nanoTime();
		for (int i = 0; i < 3; i++)
			System.out.println(new Solver(board));
		System.out.println((System.nanoTime() - time) * Math.pow(10, -9) / 3 + " seconds (average)");
		
	}
	
	public static void write(AbstractBoard board, File file) {
		PrintStream out = null;
		try {
			file.createNewFile();
			out = new PrintStream(new FileOutputStream(file));
			out.println(board.size);
			out.println(board);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (out != null)
				out.close();
		}
		
	}
}
