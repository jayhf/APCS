import java.util.HashMap;
import java.util.LinkedList;

/**
 * This is an old file that is not being used any more.
 * 
 * @author Jay
 * @version 1.0
 */
public class HashedQueue {
	private HashMap<Integer, LinkedList<AbstractBoard>> boards = new HashMap<Integer, LinkedList<AbstractBoard>>();
	private int current = 0;

	public HashedQueue() {}

	public void add(AbstractBoard board) {
		int value = board.manhattan();
		if (value < current)
			current = value;
		if (!boards.containsKey(value))
			boards.put(value, new LinkedList<AbstractBoard>());
		boards.get(value).addFirst(board);
	}

	public boolean isEmpty() {
		return boards.isEmpty();
	}

	public AbstractBoard remove() {
		while (!boards.containsKey(current))
			current++;
		AbstractBoard result = boards.get(current).pop();
		if (boards.get(current).isEmpty())
			boards.remove(current);
		return result;
	}
}
