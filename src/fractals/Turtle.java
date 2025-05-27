package fractals;

import processing.core.PApplet;
import processing.core.PVector;

public class Turtle {
	protected float len;
	protected float theta;
	protected LSystem lsystem;
	protected PVector pos;
	protected PApplet p;

	public Turtle(PApplet p, LSystem lsystem, float len, float theta, PVector pos) {
		this.p = p;
		this.lsystem = lsystem;
		this.len = len;
		this.theta = theta;
		this.pos=pos;
	}

	public void scaling(float s)
	{
		len *= s;
	}

	public void render() {
		p.pushMatrix();
		p.translate(pos.x,pos.y);
		p.rotate((float)-Math.PI/2);
		p.stroke(0,100,0);
		p.strokeWeight(3);

		for (int i = 0; i < lsystem.getSentence().length(); i++) {
			char c = lsystem.getSentence().charAt(i);

			if (c == 'F' || c == 'G') {
				p.line(0,0,len,0);
				p.translate(len,0);
			}
			else if (c == 'f') p.translate(len,0);
			else if (c == '+') p.rotate(theta);
			else if (c == '-') p.rotate(-theta);
			else if (c == '[') p.pushMatrix();
			else if (c == ']') p.popMatrix();
		}
		p.popMatrix();
	}

	public PVector getPos() {
		return pos;
	}
}