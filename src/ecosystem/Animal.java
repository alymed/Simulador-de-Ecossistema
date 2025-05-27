package ecosystem;

import java.util.ArrayList;
import aa.Boid;
import processing.core.PApplet;
import processing.core.PVector;

public abstract class Animal extends Boid {
	protected float energy;
	protected String type = "";
	protected int birthday;
	protected float deathRate;
	protected float birthRate;
	protected float energyToReproduce;

	protected Animal(PApplet p, PVector pos, float radius, int mass) {
		super(p, pos, mass, radius);
		birthday = p.millis();
	}

	protected Animal(PApplet p, Animal a) {
		super(p, a.pos, 1f, a.radius);
		this.energy = a.energy;
		this.type = a.type;
		this.birthday = p.millis();
		this.deathRate = a.deathRate;
		this.birthRate = a.birthRate;
		this.energyToReproduce = a.energyToReproduce;
	}
	
	protected boolean die(float dt, boolean stochastic) {
		if (stochastic && p.random(1) < deathRate*dt) 
			return true;
		if(!stochastic && energy < 0)
			return true;	
		return false;
	}

	@Override
	public void move(float dt) {
		super.move(dt);
		while (pos.x < 0) pos.x += p.width;
		while (pos.y < p.height/2) pos.y += p.height/2;
		while (pos.x >= p.width) pos.x -= p.width;
		while (pos.y >= p.height) pos.y -= p.height/2;
		energy -= dt;
	}

	protected String getType() {
		return type;
	}

	public boolean isItTimeToReproduce(float dt, boolean stochastic) {
		boolean reproduce = false;
		if (stochastic) {
			double br = birthRate*dt;
			if (p.random(1) < br) reproduce = true;
		} 
		else if (energy > energyToReproduce)
			reproduce = true;
		return reproduce;
	}
	
	@Override
	public void display() {
		radius = PApplet.map(energy, 0, 200, 3, 20);
		setShape(radius);
		super.display();
	}
	
	protected abstract Animal reproduce(float dt, boolean stochastic);

	protected abstract void eat(Terrain terrain, ArrayList<Animal> allAnimals);
}
