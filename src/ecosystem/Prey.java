package ecosystem;

import java.util.ArrayList;

import aa.Boid;
import aa.BoidDNA;
import ca.GridConstants;
import physics.ParticleSystem;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

public class Prey extends Animal {
    protected int color;
    protected PImage preyDraw1;
    public Prey(PApplet p, PVector pos, int radius,int mass, int color) {
        super(p, pos, radius,mass);
        this.color=color;
        deathRate = WorldConstants.PREY_DEATH_RATE;
        birthRate = WorldConstants.PREY_BIRTH_RATE;
        energy = WorldConstants.PREY_ENERGY;
        energyToReproduce = WorldConstants.PREY_ENERGY_TO_REPRODUCE;
        type = "Prey";
        this.preyDraw1 = p.loadImage("img/prey1.png");
    }

    public Prey(PApplet p, Prey prey) {
        super(p, prey);
    }

    @Override
    public Animal reproduce(float dt, boolean stochastic) {
        Animal child = null;
        boolean reproduce = isItTimeToReproduce(dt, stochastic);
        if (reproduce) {
            energy /= 2.;
            child = new Prey(p, this);
        }
        return child;
    }

    public void applyBehaviour(ArrayList<Animal> allanimals) {
        PVector f = wander();
        applyForce(f);
        for (Animal a : allanimals) {
             if (a.getType().equals("Predator")&&inSight(a.getPos(), a.getDNA().visionDistanceLargePrey)) {
                    f=a.flee(a.getPos());
                    a.applyForce(f);
                 break;
            }
        }
    }

    @Override
    public void eat(Terrain terrain, ArrayList<Animal> allAnimals) {
        Patch patch = (Patch) terrain.getCell((int) pos.x, (int) pos.y);
        if (patch.getState() == GridConstants.State.FOOD.ordinal()) {
            energy += WorldConstants.ENERGY_FROM_PLANT;
            patch.setFertile();
        }
    }

    @Override
    public void display() {
        if (this.preyDraw1 != null) {
            radius = PApplet.map(energy, 0, 200, 5, 20);
            p.image(preyDraw1,pos.x - 26 ,pos.y -20,radius*5,radius*5);
        }
    }
}
