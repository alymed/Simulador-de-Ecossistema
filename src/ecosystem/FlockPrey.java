package ecosystem;

import aa.Flock;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;

public class FlockPrey extends Prey {
    protected Flock flock;
    protected PImage preyDraw2;

    public FlockPrey(PApplet p, PVector pos, Flock flock, int color) {
        super(p, pos, 10, 2, color);
        type = "FlockPrey";
        this.flock = flock;
        this.preyDraw2 = p.loadImage("img/prey2.png");
    }

    public FlockPrey(PApplet p, FlockPrey flockPrey) {
        super(p, flockPrey);
        this.flock = flockPrey.flock;
    }

    @Override
    public void applyBehaviour(ArrayList<Animal> allanimals) {
        flock.applyBehaviour(this);
    }

    @Override
    public Animal reproduce(float dt, boolean stochastic) {
        Animal child = null;
        boolean reproduce = isItTimeToReproduce(dt, stochastic);
        if (reproduce) {
            energy /= 2.;
            child = new FlockPrey(p, this);
            flock.addBoid(child);
        }
        return child;
    }

    @Override
    public void display() {
        if (this.preyDraw2 != null) {
            radius = PApplet.map(energy, 0, 200, 5, 20);
            p.image(preyDraw2, pos.x - 20, pos.y - 20, radius * 5, radius * 5);
        }
    }

    @Override
    protected boolean die(float dt, boolean stochastic) {
        boolean died = super.die(dt, stochastic);
        if (died) flock.delBoid(this);
        return died;
    }
}