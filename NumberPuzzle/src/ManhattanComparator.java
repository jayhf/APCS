import java.util.Comparator;

public class ManhattanComparator implements Comparator<Board> {
	@Override
	public int compare(Board board1, Board board2) {
		return board1.manhattan() - board2.manhattan();
	}
}
