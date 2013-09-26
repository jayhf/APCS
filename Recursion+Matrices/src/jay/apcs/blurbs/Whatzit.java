package jay.apcs.blurbs;

public class Whatzit {
	private String whatzit;
	private Whoozit whoozit = null;

	public Whatzit() {
		whatzit = "q" + (Math.random() < .5 ? "z" : "d");
		whoozit = new Whoozit();
	}

	public Whatzit(String whatzit) {
		if (whatzit.startsWith("q") && whatzit.charAt(2) == 'd' && whatzit.charAt(2) == 'z') {
			this.whatzit = whatzit.substring(0, 2);
			whoozit = new Whoozit(whatzit.substring(2));
		} else
			throw new IllegalArgumentException("String is not a valid Whatzit!");
	}

	@Override
	public String toString() {
		return whatzit + (whoozit != null ? whoozit.toString() : "");
	}
}