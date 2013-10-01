package jay.apcs.blurbs;

/**
 * This class represents a Whatzit, a string with a q followed by either a z or a d, followed by a Whoozit. This class
 * can either create a random Whatzit or a Whatzit from a String.
 * 
 * @see Whoozit
 * @see Blurb
 * @author Jay Fleischer
 * @version 1.0 (9-27-13)
 */
public class Whatzit {
	private String whatzit;
	private Whoozit whoozit = null;

	/**
	 * Creates a random Whatzit.
	 */
	public Whatzit() {
		whatzit = "q" + (Math.random() < .5 ? "z" : "d");
		whoozit = new Whoozit();
	}

	/**
	 * Creates a Whatzit from a String.
	 * 
	 * @param whatzit
	 *            - the String to create the Whatzit from.
	 * @throws IllegalArgumentException
	 *             if the passed String is not a valid Whatzit.
	 */
	public Whatzit(String whatzit) throws IllegalArgumentException {
		if (whatzit.startsWith("q") && (whatzit.charAt(1) == 'd' || whatzit.charAt(1) == 'z')) {
			this.whatzit = whatzit.substring(0, 2);
			whoozit = new Whoozit(whatzit.substring(2));
		} else
			throw new IllegalArgumentException("String is not a valid Whatzit!");
	}

	/**
	 * returns the String representation of this Whatzit.
	 */
	@Override
	public String toString() {
		return whatzit + (whoozit != null ? whoozit.toString() : "");
	}
}
