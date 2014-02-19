import java.util.Arrays;
import java.util.LinkedList;

public class Board implements Comparable<Board> {
	private int[][] board;
	private int emptyX, emptyY, hashCode, moves, hamming = -1, manhattan = -1;
	
	public Board(int[][] board) {
		this.board = board;
		OUTER: for (int x = 0; x < board.length; x++)
			for (int y = 0; y < board.length; y++)
				if (board[x][y] == 0 || board[x][y] == board.length * board.length) {
					emptyX = x;
					emptyY = y;
					board[x][y] = board.length * board.length;
					break OUTER;
				}
		hashCode = Arrays.deepHashCode(board);
		moves = 0;
	}
	
	public Board(int[][] board, int emptyX, int emptyY, int moves) {
		this.board = board;
		this.emptyX = emptyX;
		this.emptyY = emptyY;
		hashCode = Arrays.deepHashCode(board);
		this.moves = moves;
	}
	
	@Override
	public int compareTo(Board board) {
		return board.manhattan() - manhattan();// board.hamming() - hamming();// return
		//
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Board other = (Board) obj;
		if (!Arrays.deepEquals(board, other.board))
			return false;
		return true;
	}
	
	public int hamming() {
		if (hamming == -1) {
			hamming = moves;
			int i = 0;
			for (int y = 0; y < board.length; y++)
				for (int x = 0; x < board.length; x++)
					if (board[x][y] == ++i)
						hamming++;
		}
		return hamming;
	}
	
	@Override
	public int hashCode() {
		return hashCode;
	}
	
	public boolean isSolvable() {
		// TODO WRITE THIS
		// Sum up number of swaps to get each one to right place. Even= solvable odd=not?
		return true;
	}
	
	public boolean isSolved() {
		int i = 0;
		for (int y = 0; y < board.length; y++)
			for (int x = 0; x < board.length; x++)
				if (board[x][y] != ++i)
					return false;
		return true;
	}
	
	public int manhattan() {
		if (manhattan == -1) {
			manhattan = moves;
			for (int y = 0; y < board.length; y++)
				for (int x = 0; x < board.length; x++)
					manhattan += Math.abs((board[x][y] - 1) / board.length - y)
							+ Math.abs((board[x][y] - 1) % board.length - x);
		}
		if (manhattan < 0) {
			System.out.println("BLAH");
			System.exit(0);
		}
		return manhattan;
	}
	
	private Board move(int x, int y) {
		int[][] newBoard = new int[board.length][board.length];
		for (int i = 0; i < newBoard.length; i++)
			System.arraycopy(board[i], 0, newBoard[i], 0, board.length);
		newBoard[emptyX][emptyY] = newBoard[x][y];
		newBoard[x][y] = board.length * board.length;
		return new Board(newBoard, x, y, moves + 1);
	}
	
	public Iterable<Board> neighbors() {
		LinkedList<Board> neighbors = new LinkedList<Board>();
		if (emptyX > 0)
			neighbors.add(move(emptyX - 1, emptyY));
		if (emptyX < board.length - 1)
			neighbors.add(move(emptyX + 1, emptyY));
		if (emptyY > 0)
			neighbors.add(move(emptyX, emptyY - 1));
		if (emptyY < board.length - 1)
			neighbors.add(move(emptyX, emptyY + 1));
		return neighbors;
	}
	
	@Override
	public String toString() {
		board[emptyX][emptyY] = 0;
		String result = Arrays.deepToString(board);
		board[emptyX][emptyY] = board.length * board.length;
		result = result.substring(2, result.length() - 2).replace("], [", "\n") + "\n\n";
		return result;
	}
}