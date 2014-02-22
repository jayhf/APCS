public abstract class Bubble implements JayACMCanvas.Paintable {
	protected double x, y, vx, vy;

	public Bubble(double x, double y, double vx, double vy) {
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
	}

	public boolean bounce(Bubble b) {
		double o = Math.atan2(b.x - x, b.y - y), sino = Math.sin(o), coso = Math.cos(o);
		double vnx1 = coso * vy + sino * vx, vny1 = coso * vx - sino * vy;
		double vnx2 = coso * b.vy + sino * b.vx, vny2 = coso * b.vx - sino * b.vy;
		if (vnx2 - vnx1 > 0)
			return false;
		double thisMass = getMass(), otherMass = b.getMass();
		double cmvxn = (thisMass * vnx1 + otherMass * vnx2) / (thisMass + otherMass);
		vnx1 = 2 * cmvxn - vnx1;
		vnx2 = 2 * cmvxn - vnx2;
		vx = coso * vny1 + sino * vnx1;
		vy = coso * vnx1 - sino * vny1;
		b.vx = coso * vny2 + sino * vnx2;
		b.vy = coso * vnx2 - sino * vny2;
		update();
		b.update();
		return false;
	}

	public boolean checkCollision(Bubble bubble) {
		double thisRadius = getRadius(), bubbleRadius = bubble.getRadius();
		return (bubble.x - x) * (bubble.x - x) + (bubble.y - y) * (bubble.y - y) < (bubbleRadius + thisRadius)
				* (bubbleRadius + thisRadius);
	}

	public abstract double getMass();

	public int getNumber() {
		return 0;
	}

	public abstract double getRadius();

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public abstract boolean processClick(Mouse mouse);

	public abstract void update();
}
