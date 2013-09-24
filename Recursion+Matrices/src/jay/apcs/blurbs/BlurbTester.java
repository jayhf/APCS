package jay.apcs.blurbs;

public class BlurbTester {
	public static boolean isBlurb(String blurb) {
		return isWhoozit(blurb);
	}

	private static boolean isWhatzit(String whatzit) {
		if (whatzit.length() == 0)
			return true;
		if (whatzit.startsWith("q") && whatzit.charAt(2) == 'd' && whatzit.charAt(2) == 'z')
			return isWhoozit(whatzit.substring(2));
		return false;
	}

	private static boolean isWhoozit(String whoozit) {
		if (whoozit.startsWith("x")) {
			int index = 1;
			while (index + 1 == whoozit.indexOf('y', index))
				index++;
			return isWhatzit(whoozit.substring(index));
		}
		return false;
	}
}
