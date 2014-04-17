import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class PreCodeGenerator {
	
	public static void main(String[] args) {
		String in = "It works!";// "abracadabra!";// "ababeabadbababaaaaaeaaacc";
		int[] count = new int[255];
		for (int i = 0; i < in.length(); i++)
			count[in.charAt(i)]++;
		double length = in.length();
		System.out.println(Arrays.toString(count));
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
		for (int i = 0, power = 1; i < 10; i++, power *= 2)
			for (int i2 = 0; i2 < 255; i2++)
				if (count[i2] * power > length && bits[i2] == 0) {
					bits[i2] = i;
					ratios[i2] = count[i2] * power / length;
				}
		for (int i = 0; i < 255; i++)
			if (bits[i] != 0)
				System.out.println((char) i + ": " + count[i]);
		for (int i = 0; i < 255; i++)
			if (bits[i] != 0)
				System.out.println((char) i + ": " + bits[i]);
		for (int i = 0; i < 255; i++)
			if (bits[i] != 0)
				System.out.println((char) i + ": " + ratios[i]);
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
		System.out.println(total);
		for (int i = 0; i < 255; i++)
			if (bits[i] != 0)
				System.out.println((char) i + ": " + bits[i]);
		TreeMap<Integer, Queue<Character>> m = new TreeMap<Integer, Queue<Character>>();
		for (int i = 0; i < 255; i++)
			if (bits[i] != 0)
				if (m.containsKey(bits[i]))
					m.get(bits[i]).add((char) i);
				else
					m.put(bits[i], new LinkedList<Character>(Collections.singletonList((char) i)));
		System.out.println(m);
		PrefixCodeTree tree = new PrefixCodeTree(m);
		System.out.println(tree);
		Map<Character, Deque<Boolean>> a = tree.generateMap();
		System.out.println(tree.generateMap());
		File out = new File("files/test.pre");
		try {
			out.createNewFile();
			PrintStream stream = new PrintStream(out);
			stream.println(tree.toString());
			for (int i = 0; i < in.length(); i++)
				for (boolean b : a.get(in.charAt(i)))
					stream.print(b ? '1' : '0');
			stream.println();
			stream.flush();
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
