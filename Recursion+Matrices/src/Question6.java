import java.util.Arrays;

public class Question6 {

	public static void doMove(char[][] board, char[][] result, int x, int y) {
		if (result[x][y] == '_')
			if (board[x][y] == '*')
				result[x][y] = '*';
			else {
				int total = 0;
				for (int i = Math.max(0, x); i < Math.min(x + 3, board.length); i++)
					for (int j = Math.max(0, y); j < Math.min(y + 3, board.length); j++)
						total++;
				result[x][y] = (total + "").charAt(0);
				if (total == 0)
					for (int i = Math.max(0, x); i < Math.min(x + 3, board.length); i++)
						for (int j = Math.max(0, y); j < Math.min(y + 3, board.length); j++)
							doMove(board, result, i, j);
			}
	}

	public static char[][] playMoves(char[][] board, int[][] moves) {
		char[][] result = new char[board.length][board[0].length];
		for (char[] row : result)
			for (char c : row)
				row[c] = '_';
		for (int[] move : moves)
			doMove(board, result, move[0], move[1]);
		System.out.println(Arrays.deepToString(result));
		return result;
	}
}
