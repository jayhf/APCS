public class ReferenceBoard extends AbstractBoard {
	private int depth;
	private AbstractBoard previous;
	
	public ReferenceBoard(AbstractBoard board, int nowEmptyX, int nowEmptyY) {
		moves = board.moves + 1;
		depth = 0;
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
	public boolean isSolvable() {
		return true;
	}
	
	@Override
	public int manhattan() {
		if (manhattan == -1) {
			manhattan = previous.manhattan + 1;
			int value = previous.getValue(emptyX, emptyY);
			manhattan -= Math.abs((value - 1) / size - emptyY) + Math.abs((value - 1) % size - emptyX);
			manhattan += Math.abs((value - 1) / size - previous.emptyY)
					+ Math.abs((value - 1) % size - previous.emptyX);
		}
		return manhattan;
		
	}
	
	@Override
	protected AbstractBoard move(int x, int y) {
		ReferenceBoard reference = new ReferenceBoard(this, x, y);
		if (depth < 4)
			return reference;
		else
			return reference.toBoard();
	}
	
	private Board toBoard() {
		int[][] array = new int[size][size];
		for (int x = 0; x < size; x++)
			for (int y = 0; y < size; y++)
				array[x][y] = getValue(x, y);
		return new Board(array, emptyX, emptyY, moves);
	}
	
}
