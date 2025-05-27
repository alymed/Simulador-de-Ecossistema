package fractals;

import processing.core.PApplet;
import tools.Complex;

public abstract class FractalSet {
	
	protected int tmax;
	protected float amin;
	protected float amax;
	protected float bmin;
	protected float bmax;
	protected int posX;
	protected int posY;
	protected FractalSet(int posX, int posY, int tmax, float amin, float amax, float bmin, float bmax) {
		this.tmax = tmax;
		this.amin = amin;
		this.amax = amax;
		this.bmin = bmin;
		this.bmax = bmax;
		this.posX=posX;
		this.posY=posY;
	}
	
	public void draw(PApplet p) {
		p.loadPixels();	
		
		for(int x = 0; x < p.width; ++x) {
			for(int y = 0; y < p.height; ++y){
				Complex xt = new Complex(PApplet.map(x, 0, p.width, amin, amax), 
						PApplet.map(y, 0, p.height, bmin, bmax));
				xt.x+=posX;
				xt.y+=posY;


				Complex c = getNextC(xt);
				int t;

				for(t = 1; t < tmax; ++t) {					
					Complex xtmais1 = Complex.multiply(xt, xt).plus(xt.times(c));
					xt = xtmais1;
					if(xt.norm() > 2) {
						break;
					}

				}
				if (t >= tmax) p.pixels[x + y * p.width] = p.color(153,204,255);
			}
		}
		p.updatePixels();
	}

	protected abstract Complex getNextC(Complex xt);
}
