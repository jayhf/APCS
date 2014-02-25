import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Solver {
	public static List<AbstractBoard> solve(AbstractBoard board) {
		return solve(board, new ManhattanComparator());// java8: (b1,b2)->b1.manhattan()-b2.manhattan()
	}

	public static List<AbstractBoard> solve(AbstractBoard board, Comparator<AbstractBoard> comparator) {
		if (!board.isSolvable())
			return null;
		int[][] solvedState = new int[board.getSize()][board.getSize()];
		int j = 1;
		for (int y = 0; y < board.getSize(); y++)
			for (int x = 0; x < board.getSize(); x++)
				solvedState[x][y] = j++;
		solvedState[board.getSize() - 1][board.getSize() - 1] = 0;
		AbstractBoard solved = new Board(solvedState);
		HashMap<AbstractBoard, AbstractBoard> visited = new HashMap<AbstractBoard, AbstractBoard>();
		visited.put(board, null);
		PriorityQueue<AbstractBoard> boards = new PriorityQueue<AbstractBoard>(100, comparator);
		// HashedQueue boards = new HashedQueue();
		boards.add(board);
		AbstractBoard solution = null;
		int i = 0;
		int threshold = 10;
		while (!boards.isEmpty()) {
			i++;
			if (i > threshold)
				threshold *= 10;
			// return null;
			AbstractBoard currentAbstractBoard = boards.remove();
			// System.out.println(currentAbstractBoard);
			if (currentAbstractBoard.equals(solved)) {
				solution = currentAbstractBoard;
				break;
			}
			for (AbstractBoard newAbstractBoard : currentAbstractBoard.neighbors())
				if (!visited.containsKey(newAbstractBoard)) {
					visited.put(newAbstractBoard, currentAbstractBoard);
					boards.add(newAbstractBoard);
				}
		}
		System.out.println("Number of AbstractBoards: " + i);
		if (solution != null && visited.containsKey(solution)) {
			LinkedList<AbstractBoard> path = new LinkedList<AbstractBoard>();
			AbstractBoard current = solution;
			do
				path.addFirst(current);
			while ((current = visited.get(current)) != null);
			return path;
		} else
			return null;
	}

	private List<AbstractBoard> solution;

	public Solver(AbstractBoard board) {
		solution = solve(board);
	}

	public Solver(AbstractBoard board, Comparator<AbstractBoard> comparator) {
		solution = solve(board, comparator);
	}

	public List<AbstractBoard> getMoves() {
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
