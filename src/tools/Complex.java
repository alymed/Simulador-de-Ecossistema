package tools;

// from https://mathcs.clarku.edu/~djoyce/julia/MandelbrotApplet.html
public class Complex {

	public double x, y;

	public Complex() {
		x = 0.0;
		y = 0.0;
	}

	public Complex(double x) {
		this.x = x;
		this.y = 0.0;
	}

	public Complex(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Complex(Complex z) {
		this.x = z.x;
		this.y = z.y;
	}

	public String toString() {
		if (y >= 0)
			return x + "+" + y + "i";
		else
			return x + "-" + (-y) + "i";
	}

	// find the inner product when the numbers are treated as vectors
	public double innerProduct(Complex w) {
		return x * w.x + y * w.y;
	}

	public static double innerProduct(Complex z, Complex w) {
		return z.x * w.x + z.y * w.y;
	}

	public double normSquared() {
		return x * x + y * y;
	}

	public double norm() {
		return Math.sqrt(x * x + y * y);
	}

	public Complex conjugate() {
		return new Complex(x, -y);
	}

	public Complex negation() {
		return new Complex(-x, y);
	}

	public Complex minus(Complex w) {
		return new Complex(x - w.x, y - w.y);
	}

	public Complex minus(double t) {
		return new Complex(x - t, y);
	}

	public static Complex subtract(Complex z, Complex w) {
		return new Complex(z.x - w.x, z.y - w.y);
	}

	public static Complex subtract(double t, Complex w) {
		return new Complex(t - w.x, -w.y);
	}

	public static Complex subtract(Complex z, double t) {
		return new Complex(z.x - t, z.y);
	}

	public Complex plus(Complex w) {
		return new Complex(x + w.x, y + w.y);
	}

	public Complex plus(double t) {
		return new Complex(x + t, y);
	}

	public static Complex add(Complex z, Complex w) {
		return new Complex(z.x + w.x, z.y + w.y);
	}

	public static Complex add(double t, Complex w) {
		return new Complex(t + w.x, w.y);
	}

	public static Complex add(Complex z, double t) {
		return new Complex(z.x + t, z.y);
	}

	public Complex times(Complex w) {
		return new Complex(x * w.x - y * w.y, y * w.x + x * w.y);
	}

	public Complex times(double t) {
		return new Complex(t * x, t * y);
	}

	public static Complex multiply(Complex z, Complex w) {
		return new Complex(z.x * w.x - z.y * w.y, z.y * w.x + z.x * w.y);
	}

	public static Complex multiply(double t, Complex w) {
		return new Complex(t * w.x, t * w.y);
	}

	public static Complex multiply(Complex z, double t) {
		return new Complex(t * z.x, t * z.y);
	}

	public Complex reciprocal() {
		double den = normSquared();
		return new Complex(x / den, -y / den);
	}

	public Complex over(Complex w) {
		double den = w.normSquared();
		return new Complex((x * w.x + y * w.y) / den, (y * w.x - x * w.y) / den);
	}

	public Complex over(double t) {
		return new Complex(x / t, y / t);
	}

	public static Complex divide(Complex z, Complex w) {
		double den = w.normSquared();
		return new Complex((z.x * w.x + z.y * w.y) / den, (z.y * w.x - z.x * w.y) / den);
	}

	public static Complex divide(double t, Complex w) {
		double den = w.normSquared();
		return new Complex(t * w.x / den, -t * w.y / den);
	}

	public static Complex divide(Complex z, double t) {
		return new Complex(z.x / t, z.y / t);
	}

	public Complex sqrt() {
		double n = norm();
		double s = Math.sqrt((n + x) / 2.0);
		double t = (y >= 0.0) ? Math.sqrt((n - x) / 2.0) : -Math.sqrt((n - x) / 2.0);
		return new Complex(s, t);
	}
} // Complex