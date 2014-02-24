import java.util.HashMap;
import java.util.LinkedList;

public class HashedQueue {
	private HashMap<Integer, LinkedList<Board>> boards = new HashMap<Integer, LinkedList<Board>>();
	private int current = 0;
	
	public HashedQueue() {
	}
	
	public void add(Board board) {
		int value = board.manhattan();
		if (value < current)
			current = value;
		if (!boards.containsKey(value))
			boards.put(value, new LinkedList<Board>());
		boards.get(value).addFirst(board);
	}
	
	public boolean isEmpty() {
		return boards.isEmpty();
	}
	
	public Board remove() {
		while (!boards.containsKey(current))
			current++;
		Board result = boards.get(current).pop();
		if (boards.get(current).isEmpty())
			boards.remove(current);
		return result;
	}
}
