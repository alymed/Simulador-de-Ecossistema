package ecosystem;

import java.util.ArrayList;

import ca.Cell;
import ca.GridConstants.State;
import processing.core.PApplet;
import processing.core.PImage;

public class Patch extends Cell {
	protected long eatenTime;
	protected int timeToGrow;
	protected ArrayList<Animal> animals;
	protected PApplet p;

	public Patch(Terrain terrain, int row, int col, int timeToGrow, PApplet p) {
		super(terrain, row, col);
		this.timeToGrow = timeToGrow;
		this.p=p;
		eatenTime = System.currentTimeMillis();
		animals = new ArrayList<Animal>();
	}

	public void setFertile() {
		state = State.FERTILE.ordinal();
		eatenTime = System.currentTimeMillis();
	}

	public void regenerate() {
		if (state == State.FERTILE.ordinal()
				&& System.currentTimeMillis() > (eatenTime + timeToGrow))
			state = State.FOOD.ordinal();
	}

	public ArrayList<Animal> getAnimals() {
		return animals;
	}

	public void clearAnimalsList() {
		animals.clear();
	}

	public void addAnimal(Animal a)
	{
		animals.add(a);
	}
}