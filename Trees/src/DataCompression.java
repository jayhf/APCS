import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class DataCompression {
	private static final byte one = (byte) '1';
	
	public static void main(String[] args) throws IOException {
		File f = new File("files/abra.pre");
		FileInputStream in = new FileInputStream(f);
		byte[] buffer = new byte[(int) f.length()];
		in.read(buffer);
		in.close();
		LinkedList<Character> preCodes = new LinkedList<Character>();
		int i = 0, numChars = 0, numStars = 0;
		while (true) {
			char character = (char) buffer[i++];
			if (character == '*')
				numStars++;
			else
				numChars++;
			if (numStars + 2 == numChars)
				break;
			preCodes.add(character);
		}
		Queue<Boolean> queue = new ArrayBlockingQueue<Boolean>((buffer.length - preCodes.size()) * 8 - 1);
		for (; i < buffer.length - 1; i++)
			queue.add(buffer[i] == one);
		PrefixCodeTree tree = new PrefixCodeTree(preCodes);
		System.out.println(tree.read(queue));
	}
}
