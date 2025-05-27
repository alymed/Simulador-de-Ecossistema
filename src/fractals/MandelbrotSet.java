package fractals;

import tools.Complex;

public class MandelbrotSet extends FractalSet {
	public MandelbrotSet(int posX, int posY, int tmax, float amin, float amax, float bmin, float bmax) {
		super(posX, posY, tmax, amin, amax, bmin, bmax);
	}

	@Override
	protected Complex getNextC(Complex xt) {
		return xt;
	}
}
