package jay.apcs.blurbs;

public class Blurb {
	private Whoozit whoozit;

	public Blurb() {
		whoozit = new Whoozit();
	}

	public Blurb(String blurb) throws IllegalArgumentException {
		if (blurb != null)
			whoozit = new Whoozit(blurb);
	}

	@Override
	public String toString() {
		return whoozit.toString();
	}
}
