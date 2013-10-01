package jay.apcs.blurbs;

import java.util.Scanner;

/**
 * A console program that allows the user to check to see if an input is a blurb or create a random blurb. It also has
 * an isBlurb method that can check if a String is a blurb.
 * 
 * @see Blurb
 * @author Jay Fleischer
 * @version 1.0 (9-27-13)
 */
public class BlurbTester {
	/**
	 * Checks to see if a String is a Blurb.
	 * 
	 * @param blurb
	 *            - the potential Blurb
	 * @return whether the String is a Blurb.
	 */
	public static boolean isBlurb(String blurb) {
		try {
			new Blurb(blurb);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	/**
	 * The main method for the console program. It lets the user enter an arbitrary string to test if it's a blurb, exit
	 * the program or nothing to get a random valid blurb.
	 * 
	 * @param args
	 *            - unused
	 */
	public static void main(String[] args) {
		System.out.println("Enter a blurb to test it, nothing to get a random blurb or exit to exit.");
		Scanner s = new Scanner(System.in);
		String input;
		while (true) {
			input = s.nextLine();
			if (input.length() == 0)
				System.out.println(new Blurb());
			else if (input.equalsIgnoreCase("exit"))
				break;
			else
				System.out.println(isBlurb(input) ? "Blurb!" : "!Blurb!");
			System.out.println("\nEnter a blurb to test it, nothing to get a random blurb or exit to exit.");
		}
		s.close();
	}
}
