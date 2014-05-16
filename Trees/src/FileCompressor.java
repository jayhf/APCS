import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
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
		PrefixCodeTree tree = createPrefixCodeTree(string, '*');
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

	public static void compressJ(File file, String string)
	{
		PrefixCodeTree tree = createPrefixCodeTree(string, '\uffff');
		Map<Character, Deque<Boolean>> a = tree.generateMap();
		try {
			file.createNewFile();
			BitOutputStream s = new BitOutputStream(new FileOutputStream(file));
			String treeString = tree.toString();
			int bitsAtEnd = (11 - treeString.length() % 8) % 8;
			for (int i = 0; i < string.length(); i++)
				bitsAtEnd = (bitsAtEnd + 8 - a.get(string.charAt(i)).size()) % 8;
			s.write((bitsAtEnd & 4) != 0);
			s.write((bitsAtEnd & 2) != 0);
			s.write((bitsAtEnd & 1) != 0);
			for (int i = 0; i < treeString.length(); i++) {
				char c = treeString.charAt(i);
				if (c == '\uffff')
					s.write(true);
				else {
					s.write(false);
					s.write(c);
				}
			}
			for (int i = 0; i < string.length(); i++)
				for (boolean b : a.get(string.charAt(i)))
					s.write(b);
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static PrefixCodeTree createPrefixCodeTree(String string, char specialChar) {
		int[] count = new int[255];
		for (int i = 0; i < string.length(); i++)
			count[string.charAt(i)]++;
		double length = string.length();
		int numDistinct = 0;
		for (int i = 0; i < count.length; i++)
			if (count[i] > 0)
				numDistinct++;
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
			return new PrefixCodeTree(m, specialChar);
		} else if (numDistinct == 1) {
			Queue<Character> queue = new LinkedList<Character>(Arrays.asList(specialChar, string.charAt(0),
					string.charAt(0)));
			return new PrefixCodeTree(queue);
		} else
			return new PrefixCodeTree(new LinkedList<Character>(Arrays.asList('A')));
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
		LinkedList<Character> preCode = new LinkedList<Character>();
		int i = 0, numChars = 0, numStars = 0;
		while (numStars + 1 > numChars) {
			char character = (char) buffer[i++];
			if (character == '*')
				numStars++;
			else
				numChars++;
			preCode.add(character);
		}
		if (buffer.length - preCode.size() - 2 == 0)
			return "";
		Queue<Boolean> queue = new ArrayBlockingQueue<Boolean>(buffer.length - preCode.size() - 2);
		for (i++; i < buffer.length - 1; i++)
			queue.add(buffer[i] == one);
		PrefixCodeTree tree = new PrefixCodeTree(preCode);
		return tree.read(queue);
	}

	public static String decompressJ(File file) {
		try {
			BitInputStream s = new BitInputStream(new FileInputStream(file));
			int bitsAtEnd = 0;
			bitsAtEnd += s.readBit() ? 4 : 0;
			bitsAtEnd += s.readBit() ? 2 : 0;
			bitsAtEnd += s.readBit() ? 1 : 0;
			LinkedList<Character> preCode = new LinkedList<Character>();
			int numChars = 0, numStars = 0;
			while (numStars + 1 > numChars) {
				boolean specialChar = s.readBit();
				if (specialChar) {
					numStars++;
					preCode.add('\uffff');
				}
				else {
					numChars++;
					preCode.add((char) s.read());
				}
			}
			if (s.available() == 0) {
				s.close();
				return "";
			}
			Queue<Boolean> queue = new ArrayBlockingQueue<Boolean>(8 * (int) file.length() - preCode.size() * 9 / 2 + 7);
			while (s.bitsAvailable() > bitsAtEnd)
				queue.add(s.readBit());
			s.close();
			PrefixCodeTree tree = new PrefixCodeTree(preCode, '\uffff');
			return tree.read(queue);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
