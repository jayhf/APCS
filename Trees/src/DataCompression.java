import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class DataCompression {
	public static void main(String[] args) throws IOException {
		File f = new File("files/aesop.pre");
		FileInputStream in = new FileInputStream(f);
		byte[] buffer = new byte[(int) f.length()];
		in.read(buffer);
		in.close();
		LinkedList<Character> preCodes = new LinkedList<Character>();
		byte one = (byte) '1';
		int i = 0;
		while (true) {
			char character = (char) buffer[i];
			if (character == '0' || character == '1')
				break;
			i++;
			preCodes.add(character);
		}
		Queue<Boolean> queue = new ArrayBlockingQueue<Boolean>((buffer.length - preCodes.size()) * 8);
		preCodes.removeLast();
		for (; i < buffer.length - 1; i++)
			queue.add(buffer[i] == one);
		System.out.println(preCodes);
		PrefixCodeTree tree = new PrefixCodeTree(preCodes);
		System.out.println(tree.read(queue));
	}
}
