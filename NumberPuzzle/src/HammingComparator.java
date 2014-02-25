import java.util.Comparator;

public class HammingComparator implements Comparator<AbstractBoard> {
	@Override
	public int compare(AbstractBoard board1, AbstractBoard board2) {
		return board1.hamming() - board2.hamming();
	}
}
