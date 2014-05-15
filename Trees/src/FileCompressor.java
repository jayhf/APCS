import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * A utility class for compressing and decompressing files and strings
 * 
 * @author Jay Fleischer
 * @version 1.0
 */
public class FileCompressor {
	private static final byte one = (byte) '1';
	
	/**
	 * Compresses the passed string and writes it to a file
	 * 
	 * @param file
	 *            - the file to write it to
	 * @param string
	 *            - the String to compress
	 */
	public static void compress(File file, String string) {
		int[] count = new int[255];
		for (int i = 0; i < string.length(); i++)
			count[string.charAt(i)]++;
		double length = string.length();
		int numDistinct = 0;
		for (int i = 0; i < count.length; i++)
			if (count[i] > 0)
				numDistinct++;
<<<<<<< HEAD
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
=======
		PrefixCodeTree tree;
		if (numDistinct > 1) {
			int[] bits = new int[255];
			double[] ratios = new double[255];
			for (int i = 0, power = 1; i < 10; i++, power *= 2)
				for (int i2 = 0; i2 < 255; i2++)
					if (count[i2] * power > length && bits[i2] == 0) {
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
					if (ratios[i] > max)
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
			tree = new PrefixCodeTree(m);
		} else if (numDistinct == 1) {
			Queue<Character> queue = new LinkedList<Character>(Arrays.asList('*', string.charAt(0), string.charAt(0)));
			tree = new PrefixCodeTree(queue);
		} else
			tree = new PrefixCodeTree(new LinkedList<Character>(Arrays.asList('A')));
>>>>>>> e085bfe73b88596e79c874bcd928e06e006cb6b9
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
	
	public static void compressJ(File file, String string) {
		try {
			BitOutputStream s = new BitOutputStream(new FileOutputStream(file));
			for (int i = 0; i < string.length(); i++)
				s.write(string.charAt(i));
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Decompresses a file and returns the decompressed string
	 * 
	 * @param file
	 *            - the File to decompress
	 * @return the decompressed String
	 * @throws IOException
	 *             if an error has occurred in reading the file
	 */
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
		if (buffer.length - preCodes.size() - 2 == 0)
			return "";
		Queue<Boolean> queue = new ArrayBlockingQueue<Boolean>(buffer.length - preCodes.size() - 2);
		for (; i < buffer.length - 1; i++)
			queue.add(buffer[i] == one);
		PrefixCodeTree tree = new PrefixCodeTree(preCodes);
		return tree.read(queue);
	}
	
	public static String decompressJ(File file) {
		try {
			String result = "";
			BitInputStream s = new BitInputStream(new FileInputStream(file));
			while (s.available() > 0)
				result += (char) s.read();
			s.close();
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
