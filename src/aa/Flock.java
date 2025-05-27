package aa;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Flock {
	protected ArrayList<Boid> boids;
	protected float radius;
	protected int mass;
	protected int nboids;
	public Flock(PApplet p, int nboids, float radius, int mass) {
		this.radius = radius;
		this.mass=mass;
		this.nboids=nboids;
		boids = new ArrayList<Boid>();
		createFlock(p, nboids);
	}

	protected void createFlock(PApplet p, int nboids) {
		for(int i=0;i<nboids;i++) {
			Boid b = new Boid(p, new PVector(p.random(p.width), p.random(p.height)), 1, radius);
			boids.add(b);
		}
	}

	public void addBoid(Boid b)
	{
		boids.add(b);
	}

	public Boid getBoid(int n)
	{
		return boids.get(n);
	}

	public void delBoid(Boid b)
	{
		boids.remove(b);
	}

	public void applyBehaviour(Boid b) {
		ArrayList<Boid> boidsInSight = b.inCone(boids);
		PVector fs = b.separate(boidsInSight);
		PVector fa = b.align(boidsInSight);
		PVector fc = b.cohesion(boidsInSight);
		PVector f;
		if (boids.size()<nboids/2) f = b.wanderHorizontal();
		else f=b.wander();
		b.applyForce(fc.add(fa).add(f));
	}

	public void display() {
		for(Boid b : boids) b.display();
	}
}
