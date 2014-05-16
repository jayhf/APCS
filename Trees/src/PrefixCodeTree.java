import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.TreeMap;

/**
 * Represents a Prefix Code Tree, allowing data to be compressed.
 * 
 * @author Jay Fleischer
 * @version 1.0
 */
public class PrefixCodeTree {
	private char character, specialChar;
	private PrefixCodeTree left = null, right = null;

	/**
	 * Creates a prefix code tree given the depths of the passed characters
	 * 
	 * @param m
	 *            - a map of depths to all of the characters at each of those depths
	 */
	public PrefixCodeTree(Map<Integer, Queue<Character>> m, char specialChar) {
		this(m, 0, specialChar);
	}

	private PrefixCodeTree(Map<Integer, Queue<Character>> map, int depth, char specialChar) {
		this.specialChar = specialChar;
		if (map.containsKey(depth)) {
			Queue<Character> queue = map.get(depth);
			character = queue.poll();
			if (queue.isEmpty())
				map.remove(depth);
		} else {
			character = specialChar;
			left = new PrefixCodeTree(map, depth + 1, specialChar);
			right = new PrefixCodeTree(map, depth + 1, specialChar);
		}
	}

	/**
	 * Creates a prefix code tree from a queue of characters from a precode String
	 * 
	 * @param chars
	 *            - the queue of characters
	 */
	public PrefixCodeTree(Queue<Character> chars) {
		this(chars, '*');
	}

	/**
	 * Creates a prefix code tree from a queue of characters from a precode String
	 * 
	 * @param chars
	 *            - the queue of characters
	 */
	public PrefixCodeTree(Queue<Character> chars, char specialChar) {
		this.specialChar = specialChar;
		character = chars.poll();
		if (character == specialChar) {
			left = new PrefixCodeTree(chars, specialChar);
			right = new PrefixCodeTree(chars, specialChar);
		}
	}

	/**
	 * Creates a map of characters to character codes
	 * 
	 * @return the map
	 */
	public Map<Character, Deque<Boolean>> generateMap() {
		Map<Character, Deque<Boolean>> result = new TreeMap<Character, Deque<Boolean>>();
		if (left != null) {
			Map<Character, Deque<Boolean>> map = left.generateMap();
			for (Entry<Character, Deque<Boolean>> entry : map.entrySet())
				entry.getValue().push(false);
			result.putAll(map);
		}
		if (right != null) {
			Map<Character, Deque<Boolean>> map = right.generateMap();
			for (Entry<Character, Deque<Boolean>> entry : map.entrySet())
				entry.getValue().push(true);
			result.putAll(map);
		}
		if (character != specialChar)
			result.put(character, new LinkedList<Boolean>());
		return result;
	}

	private char process(Queue<Boolean> data) {
		if (character == specialChar)
			return data.poll() ? right.process(data) : left.process(data);
		else
			return character;
	}

	/**
	 * Uses this precode tree to read the passed queue
	 * 
	 * @param queue
	 *            - the queue to be decompressed
	 * @return the String of the resulting decompressed data
	 */
	public String read(Queue<Boolean> queue) {
		StringBuilder builder = new StringBuilder();
		while (!queue.isEmpty())
			builder.append(process(queue));
		return builder.toString();
	}

	/**
	 * Creates the precode String for this tree
	 * 
	 * @return the precode String
	 */
	@Override
	public String toString() {
		return character + (left == null ? "" : left.toString()) + (right == null ? "" : right.toString());
	}
}
