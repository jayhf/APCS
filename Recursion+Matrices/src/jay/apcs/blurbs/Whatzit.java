package jay.apcs.blurbs;

public class Whatzit {
	private String whatzit;
	private Whoozit whoozit = null;

	public Whatzit() {
		whatzit = "x" + (Math.random() < .5 ? "z" : "d");
		whoozit = new Whoozit();
	}

	public Whatzit(String blurb) {
		if (blurb.startsWith("q") && blurb.charAt(2) == 'd' && blurb.charAt(2) == 'z') {
			whatzit = blurb.substring(2);
			whoozit = new Whoozit(blurb);
		} else
			throw new IllegalArgumentException();
	}

	@Override
	public String toString() {
		return whatzit + whoozit != null ? whoozit.toString() : "";
	}
}
