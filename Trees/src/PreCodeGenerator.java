import java.util.Arrays;

public class PreCodeGenerator {
	
	public static void main(String[] args) {
		String in = "ababeabadbababaaaaaeaaacc";
		int[] count = new int[255];
		for (int i = 0; i < in.length(); i++)
			count[in.charAt(i)]++;
		double length = in.length();
		System.out.println(Arrays.toString(count));
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
		int k = 0;
		while (total < 1 && k++ < 10) {
			for (int i = 0; i < 255; i++)
				if (ratios[i] > max)
					max = ratios[maxIndex = i];
			if (total + Math.pow(2, -bits[maxIndex]) <= 1)
				total += Math.pow(2, -bits[maxIndex]--);
			ratios[maxIndex] /= 2;
			max = 0;
			maxIndex = 0;
		}
		for (int i = 0; i < 255; i++)
			if (bits[i] != 0)
				System.out.println((char) i + ": " + bits[i]);
		
	}
}
