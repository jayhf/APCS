import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import java.util.concurrent.ArrayBlockingQueue;

public class FileCompressor {
	private static final byte one = (byte) '1';

	public static void compress(File file, String string) {
		int[] count = new int[255];
		for (int i = 0; i < string.length(); i++)
			count[string.charAt(i)]++;
		double length = string.length();
		int numDistinct = 0;
		for (int i = 0; i < count.length; i++)
			if (count[i] > 0)
				numDistinct++;
		int[] map = new int[numDistinct], usedCount = new int[numDistinct];
		for (int i = 0, j = 0; i < count.length; i++)
			if (count[i] != 0)
				usedCount[j] = count[map[j++] = i];
		int[] bits = new int[255];
		double[] ratios = new double[255];
		for (int i = 0, power = 1; power <= length * 2; i++, power *= 2)
			for (int i2 = 0; i2 < 255; i2++)
				if (count[i2] * power >= length && bits[i2] == 0) {
					bits[i2] = i;
					ratios[i2] = count[i2] * power / length;
				}
		double total = 0;
		for (int i = 0; i < 255; i++)
			if (bits[i] > 0)
				total += Math.pow(2, -bits[i]);
		int maxIndex = 0;
		double max = 0;
		while (total < 1) {
			for (int i = 0; i < 255; i++)
				if (ratios[i] > max || ratios[i] == max && bits[i] < bits[maxIndex])
					max = ratios[maxIndex = i];
			if (total + Math.pow(2, -bits[maxIndex]) <= 1)
				total += Math.pow(2, -bits[maxIndex]--);
			ratios[maxIndex] /= 2;
			max = 0;
			maxIndex = 0;
		}
		TreeMap<Integer, Queue<Character>> m = new TreeMap<Integer, Queue<Character>>();
		for (int i = 0; i < 255; i++)
			if (bits[i] != 0)
				if (m.containsKey(bits[i]))
					m.get(bits[i]).add((char) i);
				else
					m.put(bits[i], new LinkedList<Character>(Collections.singletonList((char) i)));
		PrefixCodeTree tree = new PrefixCodeTree(m);
		Map<Character, Deque<Boolean>> a = tree.generateMap();
		try {
			file.createNewFile();
			StringBuilder output = new StringBuilder();
			output.append(tree.toString());
			output.append('\n');
			for (int i = 0; i < string.length(); i++)
				for (boolean b : a.get(string.charAt(i)))
					output.append(b ? '1' : '0');
			output.append("\n");
			PrintStream stream = new PrintStream(file);
			stream.print(output);
			stream.flush();
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String decompress(File file) throws IOException {
		FileInputStream in = new FileInputStream(file);
		byte[] buffer = new byte[(int) file.length()];
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
		Queue<Boolean> queue = new ArrayBlockingQueue<Boolean>(buffer.length - preCodes.size() - 2);
		for (; i < buffer.length - 1; i++)
			queue.add(buffer[i] == one);
		PrefixCodeTree tree = new PrefixCodeTree(preCodes);
		return tree.read(queue);
	}
}
