/**
 * 
 * @author Jay
 * @version 1.0
 */
public class Board extends AbstractBoard {
	protected int[][] board;

	public Board(int[][] board) {
		super(board.length, 0);
		this.board = board;
		moves = 0;
		OUTER:
		for (int x = 0; x < board.length; x++)
			for (int y = 0; y < board.length; y++)
				if (board[x][y] == 0) {
					emptyX = x;
					emptyY = y;
					break OUTER;
				}
	}

	public Board(int[][] board, int emptyX, int emptyY, int moves) {
		super(board.length, moves);
		this.board = board;
		this.emptyX = emptyX;
		this.emptyY = emptyY;
	}

	public Board(int[][] board, int x, int y, int moves, int hamming, int manhattan) {
		this(board, x, y, moves);
		this.hamming = hamming;
		this.manhattan = manhattan;
	}

	@Override
	public int getValue(int x, int y) {
		return board[x][y];
	}

	@Override
	protected AbstractBoard move(int x, int y) {
		return new ReferenceBoard(this, x, y);
	}

	@Override
	protected int[][] toArray() {
		int[][] newBoard = new int[board.length][board.length];
		for (int i = 0; i < newBoard.length; i++)
			System.arraycopy(board[i], 0, newBoard[i], 0, board.length);
		return newBoard;
	}
}
