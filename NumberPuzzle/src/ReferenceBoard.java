import java.util.LinkedList;

public class ReferenceBoard extends AbstractBoard {
	private int depth;
	private AbstractBoard previous;

	public ReferenceBoard(AbstractBoard board, int nowEmptyX, int nowEmptyY) {
		moves = board.moves + 1;
		previous = board;
		emptyX = nowEmptyX;
		emptyY = nowEmptyY;
		init();
	}

	public ReferenceBoard(ReferenceBoard board, int nowEmptyX, int nowEmptyY) {
		moves = board.moves + 1;
		depth = board.depth + 1;
		previous = board;
		emptyX = nowEmptyX;
		emptyY = nowEmptyY;
		init();
	}

	@Override
	protected int getSize() {
		return previous.size;
	}

	@Override
	public int getValue(int x, int y) {
		if (x == emptyX && y == emptyY)
			return 0;
		else if (x == previous.emptyX && y == previous.emptyY)
			return previous.getValue(emptyX, emptyY);
		return previous.getValue(x, y);
	}

	@Override
	public int hamming() {
		if (hamming == -1)
			if (previous.hamming != -1) {
				hamming = previous.hamming;
				int value = previous.getValue(emptyX, emptyY);
				if ((value - 1) / size == emptyY && (value - 1) % size == emptyX)
					hamming--;
				else if ((value - 1) / size == previous.emptyY && (value - 1) % size == previous.emptyX)
					hamming++;
			}
			else
				hamming = super.hamming();
		return hamming;
	}

	@Override
	public boolean isSolvable() {
		return true;
	}

	@Override
	public int manhattan() {
		if (manhattan == -1)
			if (previous.manhattan != -1) {
				manhattan = previous.manhattan;
				int value = previous.getValue(emptyX, emptyY);
				manhattan -= Math.abs((value - 1) / size - emptyY) + Math.abs((value - 1) % size - emptyX);
				manhattan += Math.abs((value - 1) / size - previous.emptyY)
						+ Math.abs((value - 1) % size - previous.emptyX);
			}
			else
				manhattan = super.manhattan();
		return manhattan + moves;
	}

	@Override
	protected AbstractBoard move(int x, int y) {
		ReferenceBoard reference = new ReferenceBoard(this, x, y);
		return new Board(reference.toArray(), x, y, moves, reference.hamming, reference.manhattan);
	}

	@Override
	public Iterable<AbstractBoard> neighbors() {
		LinkedList<AbstractBoard> neighbors = new LinkedList<AbstractBoard>();
		if (emptyX > 0 && previous.emptyX != emptyX - 1)
			neighbors.add(move(emptyX - 1, emptyY));
		if (emptyX < size - 1 && previous.emptyX != emptyX + 1)
			neighbors.add(move(emptyX + 1, emptyY));
		if (emptyY > 0 && previous.emptyY != emptyY - 1)
			neighbors.add(move(emptyX, emptyY - 1));
		if (emptyY < size - 1 && previous.emptyY != emptyY + 1)
			neighbors.add(move(emptyX, emptyY + 1));
		return neighbors;
	}

	@Override
	protected int[][] toArray() {
		int[][] result = previous.toArray();
		result[previous.emptyX][previous.emptyY] = result[emptyX][emptyY];
		result[emptyX][emptyY] = 0;
		return result;
	}
}
