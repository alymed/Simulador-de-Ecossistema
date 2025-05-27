package ecosystem;

import java.util.ArrayList;
import ca.GridConstants;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Predator extends Animal {
	protected PImage predatorDraw;

	public Predator(PApplet p, PVector pos, int radius, int mass) {
		super(p, pos,radius,mass);
		deathRate = WorldConstants.PREDATOR_DEATH_RATE;
		birthRate = WorldConstants.PREDATOR_BIRTH_RATE;
		energy = WorldConstants.PREDATOR_ENERGY;
		energyToReproduce = WorldConstants.PREDATOR_ENERGY_TO_REPRODUCE;
		type = "Predator";
		this.predatorDraw = p.loadImage("img/predatorD.png");
	}

	public Predator(PApplet p, Predator predator) {
		super(p, predator);
	}

	@Override
	public void display() {
		if (this.predatorDraw != null) {
			radius = PApplet.map(energy, 0, 200, 20, 20);
			p.image(predatorDraw, pos.x - 23 , pos.y -23, radius * 5, radius * 4);
		}
	}

	public void applyBehaviour(ArrayList<Animal> allanimals) {
		PVector f = wander();
		applyForce(f);
		for (Animal a: allanimals) {
			if (a.getType().equals("Prey")){
				if (inSight(a.getPos(), a.getDNA().visionDistanceLargePredator)){
					f = seek(a.getPos());
					applyForce(f);
					return;
				}
			}
		}
	}

	@Override
	public Animal reproduce(float dt, boolean stochastic) {
		Animal child = null;
		boolean reproduce = isItTimeToReproduce(dt, stochastic);
		if (reproduce) {
			energy /= 2.;
			child = new Predator(p, this);
		}
		return child;
	}

	@Override
	public void eat(Terrain terrain, ArrayList<Animal> allAnimals) {
		Patch patch = (Patch) terrain.getCell((int)pos.x, (int)pos.y);

		if (patch.getState() == GridConstants.State.FOOD.ordinal()) {
			energy += WorldConstants.ENERGY_FROM_PLANT;
			patch.setFertile();
		}

		ArrayList<Animal> patchAnimals = patch.getAnimals();
		for(int i=0;i<patchAnimals.size();i++) {
			Animal a = patchAnimals.get(i);
			if (a.type == "Prey") {
				patchAnimals.remove(a);
				allAnimals.remove(a);
				energy += WorldConstants.ENERGY_FROM_PREY;
				break;
			}
		}
	}
}