import java.util.Comparator;

public class JayHeuristicComparator implements Comparator<AbstractBoard> {
	@Override
	public int compare(AbstractBoard board1, AbstractBoard board2) {
		return board1.jayHeuristic() - board2.jayHeuristic();
	}
}
