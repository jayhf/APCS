import java.util.Comparator;

/**
 * 
 * @author Jay
 * @version 1.0
 */
public class ManhattanComparator implements Comparator<AbstractBoard> {
	@Override
	public int compare(AbstractBoard board1, AbstractBoard board2) {
		return board1.manhattan() - board2.manhattan();
	}
}
