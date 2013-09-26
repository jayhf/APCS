package jay.apcs.blurbs;

public class Whoozit {
	private Whatzit whatzit = null;
	private String whoozit = "x";

	public Whoozit() {
		this(false);
	}

	public Whoozit(boolean needsNext) {
		while (Math.random() * Math.random() < Math.random())
			whoozit += "y";
		if (needsNext || Math.random() * Math.random() < Math.random())
			whatzit = new Whatzit();
	}

	public Whoozit(String whoozit) {
		this(whoozit, false);
	}

	public Whoozit(String whoozit, boolean needsNext) {
		if (whoozit.startsWith("x")) {
			int index = 0;
			while ('y' == whoozit.charAt(++index))
				this.whoozit += "y";
			System.out.println(this.whoozit);
			if (whoozit.length() != index)
				whatzit = new Whatzit(whoozit.substring(index));
			else if (needsNext)
				throw new IllegalArgumentException("String needs another Whatzit!");
		} else
			throw new IllegalArgumentException("String is not a valid Whoozit!");
	}

	@Override
	public String toString() {
		return whoozit + (whatzit != null ? whatzit.toString() : "");
	}
}
