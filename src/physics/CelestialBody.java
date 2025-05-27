package physics;
import processing.core.PApplet;
import processing.core.PVector;

public class CelestialBody extends Mover {
	protected int color;

	public CelestialBody(PVector pos, PVector vel, float mass, float radius, int color) {
		super(pos, vel, mass, radius);
		this.color = color;
	}
	
	public void display(PApplet p) {
		p.pushStyle();
		p.noStroke();
		p.fill(color);
		p.ellipse(pos.x, pos.y, 2*radius, 2*radius);
		p.popStyle();
	}
}