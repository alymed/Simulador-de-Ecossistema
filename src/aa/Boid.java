package aa;
import java.util.ArrayList;

import physics.Mover;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;

public class Boid extends Mover {
	protected PShape s;
	protected BoidDNA dna;
	protected float phiWander;
	protected PApplet p;
	protected boolean debug = false;

	public Boid(PApplet p, PVector pos, float mass, float radius) {
		super(pos, new PVector(), mass, radius);
		this.p = p;
		setShape(radius);
		dna=new BoidDNA(p);
	}

	public void setShape(float radius) {
		s = p.createShape();
		s.beginShape();
		s.noStroke();
		s.fill(0);
		s.vertex(-radius, radius/2);
		s.vertex(radius, 0);
		s.vertex(-radius, -radius/2);
		s.vertex(-radius/2, 0);
		s.endShape(PConstants.CLOSE);
	}

	@Override
	public void applyForce(PVector f)
	{
		super.applyForce(f.limit(dna.maxForce));
	}

	public boolean inSight(PVector t, float visionDistance) {
		PVector r = PVector.sub(t, pos);
		float d = r.mag();
		return ((d > 0) && (d < visionDistance));
	}

	public PVector brake() {
		PVector vd = new PVector();
		return PVector.sub(vd, vel);
	}

	public PVector seek(PVector target) {
		PVector vd = PVector.sub(target, pos);
		vd.normalize().mult(dna.maxSpeed);
		return PVector.sub(vd, vel);
	}

	public PVector flee(PVector target)
	{
		return seek(target).mult(-1);
	}

	public boolean arrive(PVector target, float erro) {
		PVector VetorDist = PVector.sub(target, pos);
		float dist = VetorDist.mag(); // Calcula a magnitude do vetor de distância
		return dist < erro;
	}

	public PVector pursuit(Boid b) {
		PVector d = PVector.mult(b.vel, dna.deltaTPursuit);
		PVector target = PVector.add(b.pos, d);
		return seek(target);
	}

	public PVector evade(Boid b) {
		return pursuit(b).mult(-1);
	}

	public PVector wander() {
		PVector center = pos.copy();
		center.add(PVector.mult(vel, dna.deltaTWander));
		PVector target = new PVector(dna.radiusWander * PApplet.cos(phiWander),
				dna.radiusWander * PApplet.sin(phiWander));
		target.add(center);
		phiWander += p.random(-dna.deltaPhiWander, dna.deltaPhiWander);
		return seek(target);
	}

	public PVector wanderHorizontal() {
		PVector center = pos.copy();
		center.add(PVector.mult(vel, dna.deltaTWander));
		PVector target = new PVector(center.x + dna.radiusWander, center.y+10);
		phiWander += p.random(-dna.deltaPhiWander, dna.deltaPhiWander);
		return seek(target);
	}

	public ArrayList<Boid> inCone(ArrayList<Boid> allBoids) {
		ArrayList<Boid> boidsInSight = new ArrayList<Boid>();
		for(Boid b : allBoids)
			if (inSight(b.pos, dna.visionDistanceLarge))
				boidsInSight.add(b);
		return boidsInSight;
	}

	public PVector separate(ArrayList<Boid> boids) {
		PVector vd = new PVector();
		for(Boid b : inCone(boids)) {
			PVector r = PVector.sub(pos, b.pos);
			float d = r.mag();
			r.div(d*d);
			vd.add(r);
		}
		vd.normalize().mult(dna.maxSpeed);
		return PVector.sub(vd, vel);
	}

	public PVector cohesion(ArrayList<Boid> boids) {
		PVector target = pos.copy();
		for(Boid b : inCone(boids)) target.add(b.pos);
		target.div(boids.size()+1);
		return seek(target);
	}

	public PVector align(ArrayList<Boid> boids) {
		PVector vd = this.vel.copy();
		for(Boid b : inCone(boids)) vd.add(b.vel);
		vd.normalize().mult(dna.maxSpeed);
		return PVector.sub(vd, vel);
	}

	public BoidDNA getDNA() {
		return dna;
	}

	public void setDNA(BoidDNA dna) {
		this.dna = dna;
	}

	public void setDebug(boolean debug)
	{
		this.debug = debug;
	}

	public void display() {
		p.pushMatrix();
		p.translate(pos.x, pos.y);
		p.rotate(vel.heading());
		p.shape(s, 0, 0);
		p.popMatrix();

		if (debug) {
			p.pushMatrix();
			p.pushStyle();
			p.stroke(255,255,0);
			p.strokeWeight(3);
			p.translate(pos.x, pos.y);
			p.rotate(vel.heading());
			p.rotate(dna.visionAngle);
			p.line(0, 0, dna.visionDistanceSmall, 0);
			p.rotate(-2*dna.visionAngle);
			p.line(0, 0, dna.visionDistanceSmall, 0);
			p.popStyle();
			p.popMatrix();
		}
	}
}