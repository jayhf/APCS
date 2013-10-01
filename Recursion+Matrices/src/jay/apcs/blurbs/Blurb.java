package jay.apcs.blurbs;

/**
 * Represents a Blurb, a Whoozit followed by at least one Whatzit. Can create a random Blurb or a Blurb from a String.
 * 
 * @see Whatzit
 * @see Whoozit
 * @author Jay Fleischer
 * @version 1.0 (9-27-13)
 */
public class Blurb {
	private Whoozit whoozit;

	/**
	 * Creates a random Blurb.
	 */
	public Blurb() {
		whoozit = new Whoozit(true);
	}

	/**
	 * Creates a blurb from a String.
	 * 
	 * @param blurb
	 *            - The String to create the blurb from.
	 * @throws IllegalArgumentException
	 *             if the passed String is not a valid blurb.
	 */
	public Blurb(String blurb) throws IllegalArgumentException {
		if (blurb != null && blurb.length() > 0)
			try {
				whoozit = new Whoozit(blurb, true);
			} catch (Exception e) {
				throw new IllegalArgumentException(e);
			}
		else
			throw new IllegalArgumentException("Blurb may not be null and must have characters!");
	}

	/**
	 * Returns the String representation of this Blurb.
	 */
	@Override
	public String toString() {
		return whoozit.toString();
	}
}