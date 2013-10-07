/**
 * The solution for problem 6 on the Philly Classic. It plays mine sweeper with a board and specified moves.
 * 
 * @author Jay Fleischer
 * @version 10-7-13
 */
public class Question6 {
	/**
	 * Does a move on the board
	 * 
	 * @param board
	 *            - the board to perform the move on
	 * @param result
	 *            - the array to store the results in
	 * @param x
	 *            - the x coordinate of the move
	 * @param y
	 *            - the y coordinate of the move
	 * @return - whether the player lost
	 */
	public static boolean doMove(char[][] board, char[][] result, int x, int y) {
		if (result[x][y] == '_')
			if (board[x][y] == '*') {
				result[x][y] = '*';
				return true;
			} else {
				int total = 0;
				for (int i = Math.max(0, x - 1); i < Math.min(x + 2, board.length); i++)
					for (int j = Math.max(0, y - 1); j < Math.min(y + 2, board.length); j++)
						if (board[i][j] == '*')
							total++;
				result[x][y] = (total + "").charAt(0);
				if (total == 0)
					for (int i = Math.max(0, x - 1); i < Math.min(x + 2, board.length); i++)
						for (int j = Math.max(0, y - 1); j < Math.min(y + 2, board.length); j++)
							doMove(board, result, i, j);
			}
		return false;
	}

	/**
	 * The play method
	 * 
	 * @param board
	 *            - the board to play the game on
	 * @param moves
	 *            - the moves to make
	 * @return the result of playing the passed moves
	 */
	public static char[][] playMoves(char[][] board, int[][] moves) {
		char[][] result = new char[board.length][board[0].length];
		for (char[] column : result)
			for (int i = 0; i < column.length; i++)
				column[i] = '_';
		for (int[] move : moves)
			if (doMove(board, result, move[0], move[1]))
				break;
		return result;
	}
}