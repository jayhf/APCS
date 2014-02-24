import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Solver {
	public static List<Board> solve(Board board) {
		return solve(board, new JayHeuristicComparator());// (b1,b2)->b1.hamming()-b2.hamming()
	}
	
	public static List<Board> solve(Board board, Comparator<Board> comparator) {
		if (!board.isSolvable())
			return null;
		int[][] solvedState = new int[board.getSize()][board.getSize()];
		int j = 1;
		for (int y = 0; y < board.getSize(); y++)
			for (int x = 0; x < board.getSize(); x++)
				solvedState[x][y] = j++;
		solvedState[board.getSize() - 1][board.getSize() - 1] = 0;
		Board solved = new Board(solvedState);
		HashMap<Board, Board> visited = new HashMap<Board, Board>();
		visited.put(board, null);
		PriorityQueue<Board> boards = new PriorityQueue<Board>(100, comparator);
		// HashedQueue boards = new HashedQueue();
		boards.add(board);
		Board solution = null;
		int i = 0;
		int threshold = 10;
		while (!boards.isEmpty()) {
			i++;
			if (i > threshold)
				threshold *= 10;
			// return null;
			Board currentBoard = boards.remove();
			// System.out.println(currentBoard);
			if (currentBoard.equals(solved)) {
				solution = currentBoard;
				break;
			}
			for (Board newBoard : currentBoard.neighbors())
				if (!visited.containsKey(newBoard)) {
					visited.put(newBoard, currentBoard);
					boards.add(newBoard);
				}
		}
		System.out.println("Number of Boards: " + i);
		if (solution != null && visited.containsKey(solution)) {
			LinkedList<Board> path = new LinkedList<Board>();
			Board current = solution;
			do
				path.addFirst(current);
			while ((current = visited.get(current)) != null);
			return path;
		} else
			return null;
	}
	
	private List<Board> solution;
	
	public Solver(Board board) {
		solution = solve(board);
	}
	
	public Solver(Board board, Comparator<Board> comparator) {
		solution = solve(board, comparator);
	}
	
	public List<Board> getMoves() {
		return solution;
	}
	
	public boolean isSolvable() {
		return solution == null;
	}
	
	public int moves() {
		return solution.size();
	}
	
	@Override
	public String toString() {
		String result = solution.toString();
		return result.substring(1, result.length() - 1).replace(", ", "\n") + "Number of moves to solution: "
				+ solution.size() + "\n";
	}
}
