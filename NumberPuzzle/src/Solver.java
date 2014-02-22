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
		HashMap<Board, Board> visited = new HashMap<Board, Board>();
		visited.put(board, null);
		PriorityQueue<Board> boards = new PriorityQueue<Board>(100, comparator);
		boards.add(board);
		Board solution = null;
		int i = 0;
		int threshold = 10;
		while (!boards.isEmpty()) {
			i++;
			if (boards.size() > threshold) {
				System.out.println(threshold);
				threshold *= 10;
			}
			Board currentBoard = boards.remove();
			if (currentBoard.isSolved()) {
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
