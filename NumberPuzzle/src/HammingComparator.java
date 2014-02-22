import java.util.Comparator;

public class HammingComparator implements Comparator<Board> {
	@Override
	public int compare(Board board1, Board board2) {
		return board1.hamming() - board2.hamming();
	}
}
