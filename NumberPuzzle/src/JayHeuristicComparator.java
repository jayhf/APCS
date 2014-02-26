import java.util.Comparator;

public class JayHeuristicComparator implements Comparator<AbstractBoard> {
	private double factor;

	/**
	 * Use different factors to find a balance between speed and performance. 1 is equivalent to Manhattan (slowest) and
	 * 0 doesn't care about path length at all. A value in the middle is the fastest. .5 works well.
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
