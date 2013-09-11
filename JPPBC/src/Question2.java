import java.util.Arrays;
import java.util.Collections;

/**
 * The second problem of the year: sorting words with a different alphabet.
 * 
 * @author Jay Fleischer
 * @version 1.0 (9-9-13)
 */
public class Question2 {
	public static final String ALPHABET = "VOFLTSUQXJGBCAHNMDEZRYKWIP#$";

	private static boolean checkIfSorted(String[] strings) {
		for (int i = 0; i < strings.length - 2; i++)
			if (!compare(strings[i], strings[i + 1]))
				return false;
		return true;
	}

	private static boolean compare(String s1, String s2) {
		int comparison;
		for (int i = 0; i < Math.min(s1.length(), s2.length()); i++)
			if ((comparison = ALPHABET.indexOf(s1.charAt(i)) - ALPHABET.indexOf(s2.charAt(i))) != 0)
				return comparison <= 0;
		return s1.length() < s2.length();
	}

	public static void main(String[] args) {
		System.out.println(Arrays.toString(wackySort(new String[] {"JUICE$", "W$#RDS", "EAGLES", "EAGERS", "W$$RDS"})));
	}

	/**
	 * Sorts the given array of strings based on the new Alphabet
	 * 
	 * @param unordered
	 *            - the unordered array of strings
	 * @return the sorted array of strings
	 */
	public static String[] wackySort(String[] unordered) {
		while (!checkIfSorted(unordered))
			for (int i = 0; i < unordered.length - 1; i++)
				if (!compare(unordered[i], unordered[i + 1]))
					Collections.swap(Arrays.asList(unordered), i, i + 1);
		return unordered;
	}
}