import java.util.Comparator;

/**
 * 
 * @author Jay
 * @version 1.0
 */
public class HammingComparator implements Comparator<AbstractBoard> {
	@Override
	public int compare(AbstractBoard board1, AbstractBoard board2) {
		return board1.hamming() - board2.hamming();
	}
}
