package jay.apcs.blurbs;

public class Blurb {
	private Whoozit whoozit;

	public Blurb() {
		whoozit = new Whoozit(true);
	}

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

	@Override
	public String toString() {
		return whoozit.toString();
	}
}