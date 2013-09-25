package jay.apcs.blurbs;

public class Whoozit {
	private Whatzit whatzit = null;
	private String whoozit = "x";

	public Whoozit() {
		while (Math.random() * Math.random() < Math.random())
			whoozit += "y";
		if (Math.random() * Math.random() < Math.random())
			whatzit = new Whatzit();
	}

	public Whoozit(String whoozit) {
		if (whoozit.startsWith("x")) {
			int index = 1;
			while (index + 1 == whoozit.indexOf('y', index)) {
				index++;
				this.whoozit += "y";
			}
			if (whoozit.length() != index)
				whatzit = new Whatzit(whoozit.substring(index));
		} else
			throw new IllegalArgumentException("String is not a valid Whoozit!");
	}

	@Override
	public String toString() {
		return whoozit + whatzit != null ? whatzit.toString() : "";
	}
}
