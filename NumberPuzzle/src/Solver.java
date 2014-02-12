import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Solver {
	public static List<Board> solve(Board board) {
		if (!board.isSolvable())
			return null;
		HashMap<Board, Board> visited = new HashMap<Board, Board>();
		visited.put(board, null);
		PriorityQueue<Board> boards = new PriorityQueue<Board>();
		boards.add(board);
		Board solution = null;
		int i = 0;
		while (!boards.isEmpty()) {
			i++;
			Board currentBoard = boards.remove();
			// System.out.println(currentBoard);
			if (currentBoard.isSolved()) {
				solution = currentBoard;
				break;
			}
			for (Board newBoard : currentBoard.neighbors())
				if (!visited.containsKey(newBoard)) {
					visited.put(newBoard, board);
					boards.add(newBoard);
				}
		}
		System.out.println(i);
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
}
