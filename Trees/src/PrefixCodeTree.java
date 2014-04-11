import java.util.Queue;

public class PrefixCodeTree {
	private char character;
	private PrefixCodeTree left = null, right = null;
	
	public PrefixCodeTree(Queue<Character> chars) {
		character = chars.poll();
		if (character == '*') {
			left = new PrefixCodeTree(chars);
			right = new PrefixCodeTree(chars);
		}
	}
	
	private char process(Queue<Boolean> data) {
		if (character == '*')
			return data.poll() ? right.process(data) : left.process(data);
		else
			return character;
	}
	
	public String read(Queue<Boolean> data) {
		StringBuilder builder = new StringBuilder();
		while (!data.isEmpty())
			builder.append(process(data));
		return builder.toString();
	}
}
