package jay.apcs.blurbs;

import java.util.Scanner;

public class BlurbTester {
	public static boolean isBlurb(String blurb) {
		try {
			new Blurb(blurb);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

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
		}
		s.close();
	}
}
