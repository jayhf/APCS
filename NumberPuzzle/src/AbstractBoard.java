import java.util.LinkedList;

public abstract class AbstractBoard {
	protected int emptyX, emptyY, hamming = -1, manhattan = -1, hashCode = -1, moves, size;
	
	public AbstractBoard(int size, int moves) {
		this.size = size;
		this.moves = moves;
	}
	
	public boolean equals(AbstractBoard other) {
		if (other.size != size)
			return false;
		for (int x = 0; x < size; x++)
			for (int y = 0; y < size; y++)
				if (getValue(x, y) != other.getValue(x, y))
					return false;
		return true;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj.hashCode() != hashCode())
			return false;
		if (!(obj instanceof AbstractBoard))
			return false;
		AbstractBoard other = (AbstractBoard) obj;
		if (other.size != size)
			return false;
		for (int x = 0; x < size; x++)
			for (int y = 0; y < size; y++)
				if (getValue(x, y) != other.getValue(x, y))
					return false;
		return true;
	}
	
	public int getSize() {
		return size;
	}
	
	public abstract int getValue(int x, int y);
	
	public int hamming() {
		if (hamming == -1) {
			hamming = 0;
			int i = 0;
			for (int y = 0; y < size; y++)
				for (int x = 0; x < size; x++)
					if (getValue(x, y) != ++i)
						hamming++;
			hamming--;
		}
		return hamming + moves;
	}
	
	@Override
	public int hashCode() {
		if (hashCode == -1) {
			hashCode = emptyX * 31 + emptyY;// Arrays.deepHashCode(board);
			for (int i = 0; i < size; i++)
				hashCode = hashCode * 31 + getValue(i, i);
			for (int i = 0; i < size; i++)
				hashCode = hashCode * 31 + getValue(size - i - 1, i);
			hashCode = Math.abs(hashCode);
		}
		return hashCode;
	}
	
	public boolean isSolvable() {
		int permutationmod2 = 0;
		for (int y = 0; y < size; y++)
			for (int x = 0; x < size; x++) {
				int value = getValue(x, y);
				permutationmod2 += (Math.abs((value - 1) / size - y) + Math.abs((value - 1) % size - x)) % 2;
			}
		return permutationmod2 == 1 ^ (size - emptyX + size - emptyY) % 2 == 1;
	}
	
	public boolean isSolved() {
		if (hamming == -1) {
			int i = 0;
			for (int y = 0; y < size; y++)
				for (int x = 0; x < size; x++)
					if (getValue(x, y) != ++i)
						return false;
			return true;
		} else
			return hamming == moves;
	}
	
	public int jayHeuristic(double factor) {
		if (manhattan == -1)
			manhattan();
		return manhattan + (int) (moves * factor);
	}
	
	public int manhattan() {
		if (manhattan == -1) {
			manhattan = 0;
			for (int y = 0; y < size; y++)
				for (int x = 0; x < size; x++) {
					int value = getValue(x, y);
					manhattan += Math.abs((value - 1) / size - y) + Math.abs((value - 1) % size - x);
				}
			int value = getValue(emptyX, emptyY);
			manhattan -= Math.abs((value - 1) / size - emptyY) + Math.abs((value - 1) % size - emptyX);
		}
		return manhattan + moves;
	}
	
	protected abstract AbstractBoard move(int x, int y);
	
	public Iterable<AbstractBoard> neighbors() {
		LinkedList<AbstractBoard> neighbors = new LinkedList<AbstractBoard>();
		if (emptyX > 0)
			neighbors.add(move(emptyX - 1, emptyY));
		if (emptyX < size - 1)
			neighbors.add(move(emptyX + 1, emptyY));
		if (emptyY > 0)
			neighbors.add(move(emptyX, emptyY - 1));
		if (emptyY < size - 1)
			neighbors.add(move(emptyX, emptyY + 1));
		return neighbors;
	}
	
	protected abstract int[][] toArray();
	
	@Override
	public String toString() {
		int numberSize = (int) Math.floor(Math.log10(size * size) + 1) + 1;
		StringBuilder result = new StringBuilder((numberSize * size + 1) * size);
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				StringBuilder number = new StringBuilder().append(getValue(x, y));
				while (number.length() < numberSize)
					number.insert(0, ' ');
				result.append(number);
			}
			result.append('\n');
		}
		return result.toString();
	}
}
