import java.util.Arrays;

/**
 * I think this is the solution to the last problem on the Philly Classic.
 * 
 * @author Jay
 * 
 */
public class Mercy {
	public static long changes(long n) {
		boolean[] generatedPrimes = generatePrimes((int) Math.sqrt(n));
		long total = 2;
		int max = (int) (Math.log(n) / Math.log(2) + .000001);
		for (int i = 2; i < max; i++) {
			int value = countPrimes((int) (Math.pow(n, 1.0 / i) + .000001), generatedPrimes);
			total += value;
		}
		return total;
	}

	public static int countPrimes(int max, boolean[] generatedPrimes) {
		int total = 0;
		for (int i = 2; i <= max; i++)
			if (!generatedPrimes[i - 2])
				total++;
		return total;
	}

	public static boolean[] generatePrimes(int max) {
		boolean[] generatedPrimes = new boolean[max];
		for (int i = 2; i < generatedPrimes.length + 2; i++) {
			if (generatedPrimes[i - 2])
				continue;
			for (int j = 2; j < generatedPrimes.length / i + 1; j++)
				generatedPrimes[i * j - 2] = true;
		}
		return generatedPrimes;
	}

	public static void main(String[] args) {
		System.out.println(Arrays.toString(generatePrimes(31)));
		System.out.println(countPrimes(31, generatePrimes(1000000)));
		System.out.println(changes(10l));
		System.out.println(changes(6l));
		System.out.println(changes(1000l));
		System.out.println(changes(1000000000000l));
	}
}
