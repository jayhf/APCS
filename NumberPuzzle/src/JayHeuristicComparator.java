import java.util.Comparator;

/**
 * 
 * @author Jay
 * @version 1.0
 */
public class JayHeuristicComparator implements Comparator<AbstractBoard> {
	private double factor;

	/**
	 * Use different factors to find a balance between speed and performance. 1 is equivalent to Manhattan (slowest) and
	 * 0 doesn't care about path length at all and is fastest on very hard boards. .9999 may be a better value than 1,
	 * because it is still guaranteed to find the quickest solution for most boards and may be faster.
	 * 
	 * @param factor
	 */
	public JayHeuristicComparator(double factor) {
		if (factor < 0 || factor > 1)
			throw new IllegalArgumentException();
		this.factor = factor;
	}

	@Override
	public int compare(AbstractBoard board1, AbstractBoard board2) {
		return board1.jayHeuristic(factor) - board2.jayHeuristic(factor);
	}
}
