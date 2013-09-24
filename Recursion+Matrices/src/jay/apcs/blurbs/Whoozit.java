package jay.apcs.blurbs;

public class Whoozit {
	private Whatzit whatzit = null;
	private String whoozit;

	public Whoozit() {
		whoozit = "x";
		while (Math.random() * Math.random() < Math.random())
			whoozit += "y";
		if (Math.random() * Math.random() < Math.random())
			whatzit = new Whatzit();
	}

	public Whoozit(String blurb) {
		if (blurb.startsWith("x")) {
			int index = 1;
			while (index + 1 == blurb.indexOf('y', index))
				index++;
			if (blurb.length() != index)
				whatzit = new Whatzit(blurb.substring(index));
		} else
			throw new IllegalArgumentException();
	}

	@Override
	public String toString() {
		return whoozit + whatzit != null ? whatzit.toString() : "";
	}
}
