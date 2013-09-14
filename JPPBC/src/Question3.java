import java.util.Arrays;
import java.util.Collections;

/**
 * The second problem of the year: sorting words with a different alphabet.
 * 
 * @author Jay Fleischer
 * @version 1.0 (9-9-13)
 */
public class Question3 {
	private static final String ALPHABET = "VOFLTSUQXJGBCAHNMDEZRYKWIP#$";

	/**
	 * Determines if s1 should be ordered before s2.
	 * 
	 * @param s1
	 *            - the first string
	 * @param s2
	 *            - the second string
	 * @return whether the strings are in the correct order
	 */
	private static boolean compare(String s1, String s2) {
		int comparison, minLength = Math.min(s1.length(), s2.length());
		for (int i = 0; i < minLength; i++)
			if ((comparison = ALPHABET.indexOf(s1.charAt(i)) - ALPHABET.indexOf(s2.charAt(i))) != 0)
				return comparison < 0;
		return s1.length() < s2.length();
	}

	/**
	 * Sorts the given array of strings based on the new Alphabet
	 * 
	 * @param unordered
	 *            - the unordered array of strings
	 * @return the sorted array of strings
	 */
	public static String[] wackySort(String[] unordered) {
		boolean modified = true;
		while (modified) {
			modified = false;
			for (int i = 0; i < unordered.length - 1; i++)
				if (!compare(unordered[i], unordered[i + 1])) {
					modified = true;
					Collections.swap(Arrays.asList(unordered), i, i + 1);
				}
		}
		return unordered;
	}
}
