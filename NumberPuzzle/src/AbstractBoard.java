public abstract class AbstractBoard {
	
	public abstract boolean equals(Board board);
	
	public abstract int getSize();
	
	public abstract int getValue(int x, int y);
	
	public abstract int hamming();
	
	public abstract boolean isSolvable();
	
	public abstract int jayHeuristic();
	
	public abstract int manhattan();
	
	public abstract Iterable<Board> neighbors();
}
