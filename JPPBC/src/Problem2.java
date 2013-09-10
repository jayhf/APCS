import java.util.Arrays;
import java.util.Comparator;

/**
 * The second problem of the year: sorting words with a different alphabet.
 * 
 * @author Jay Fleischer
 * @version 1.0 (9-9-13)
 */
public class Problem2 {
	public static final String ALPHABET = "VOFLTSUQXJGBCAHNMDEZRYKWIP#$";

	/**
	 * Sorts the given array of strings based on the new Alphabet
	 * 
	 * @param unordered
	 *            - the unordered array of strings
	 * @return the sorted array of strings
	 */
	public static String[] wackySort(String[] unordered) {
		Arrays.sort(unordered, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				int minLength = Math.min(s1.length(), s2.length());
				for (int i = 0; i < minLength; i++) {
					int comparison = ALPHABET.indexOf(s1.charAt(i)) - ALPHABET.indexOf(s2.charAt(i));
					if (comparison != 0)
						return comparison;
				}
				return s1.length() - s2.length();
			}
		});
		return unordered;
	}
}