package physics;
import processing.core.PApplet;
import processing.core.PVector;

public class Particle extends Mover {
	private float lifespan;
	private int color;
	private float timer;
	protected PApplet p;

	public Particle(PApplet p, PVector pos, PVector vel, int color,
			float radius, float lifespan) {
		super(pos, vel, 0f, radius);
		this.color = color;
		this.lifespan = lifespan;
		this.timer = 0;
		this.p=p;
	}

	@Override
	public void move(float dt) {
		super.move(dt);
		timer += dt;
	}

	public boolean isDead() {
		return (timer > lifespan);
	}

	public void display() {
		p.pushStyle();
		float alpha = PApplet.map(timer, 0, lifespan, 255, 0);
		p.fill(color, alpha);
		p.noStroke();
		p.ellipse(pos.x, pos.y, 2*radius, 2*radius);
		p.popStyle();
	}
}
