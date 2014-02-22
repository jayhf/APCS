import java.util.Comparator;

public class JayHeuristicComparator implements Comparator<Board> {
	@Override
	public int compare(Board board1, Board board2) {
		return board1.jayHeuristic() - board2.jayHeuristic();
	}
}
