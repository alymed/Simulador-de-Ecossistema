package ecosystem;

import java.util.ArrayList;

import ca.CellularAutomata;
import ca.GridConstants;
import fractals.LSystem;
import fractals.MandelbrotSet;
import fractals.Rule;
import fractals.Turtle;
import physics.CelestialBody;
import physics.ParticleSystem;
import processing.core.PApplet;
import processing.core.PVector;

public class Terrain extends CellularAutomata {
	protected float speedUp = 1f;
	protected float timer;
	protected int y;
	protected LSystem algas;
	protected Turtle turtle1, turtle2;
	protected CelestialBody sun;
	protected ParticleSystem bubbles, bubbles2, bubbles3;
	protected MandelbrotSet cloud1, cloud2, cloud3, cloud4;

	public Terrain(PApplet p) {
		super(p, GridConstants.NROWS, GridConstants.NCOLS, 1, GridConstants.NSTATES);
		sun = new CelestialBody(new PVector(p.width - 100, 0), new PVector(0, 0), 902820,
				150, p.color(255, 167, 0));
		cloud1=new MandelbrotSet(3,3,100,-4f,4f,-4f,4f);
		cloud2=new MandelbrotSet(1,4,100,-4f,4f,-4f,4f);
		cloud3=new MandelbrotSet(-4,3,100,-4f,4f,-4f,4f);
		cloud4=new MandelbrotSet(-1,3,100,-4f,4f,-4f,4f);
		Rule[] ruleset = new Rule[1];
		ruleset[0] = new Rule('F', "F[+F]F[-F][F]");
		algas = new LSystem("F", ruleset);
		turtle1 = new Turtle(p, algas, p.height / 4, PApplet.radians(20), new PVector(p.width / 3, p.height + 200));
		turtle2 = new Turtle(p, algas, p.height / 4, PApplet.radians(20), new PVector(p.width - 100, p.height + 200));

		bubbles=new ParticleSystem(p,new PVector(p.width / 4, p.height + 25), new PVector(0, 0), 1, new PVector(100, 100), p.color(255), 5, 3);
		bubbles2=new ParticleSystem(p,new PVector(p.width / 2, p.height + 25), new PVector(0, 0), 1, new PVector(100, 100), p.color(255), 5, 3);
		bubbles3=new ParticleSystem(p,new PVector(p.width , p.height + 25), new PVector(0, 0), 1, new PVector(-100, 100), p.color(255), 5, 3);

		timer = 0.0f;
		y = 38;
	}

	@Override
	protected void createGrid() {
		int minRT = (int) (WorldConstants.REGENERATION_TIME[0] * 1000);
		int maxRT = (int) (WorldConstants.REGENERATION_TIME[1] * 1000);
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				int timeToGrow = (int) (minRT + (maxRT - minRT) * Math.random());
				cells[i][j] = new Patch(this, i, j, timeToGrow,p);
			}
		}
	}

	public void regenerate(float dt) {
		bubbles.move(dt);
		bubbles2.move(dt);
		bubbles3.move(dt);

		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				((Patch) cells[i][j]).regenerate();
			}
		}

		timer += (speedUp * dt);

		if ((int) timer != 0 && (int) timer % 15 == 0 && timer < 61) {
			algas.nextGeneration();
			turtle1.scaling(0.5f);
			turtle2.scaling(0.5f);

			for (int i = y; i < nrows; i++) {
				for (int j = 46; j < 46 + 3; j++) {
					cells[i][j].setState(3);
				}
				for (int j = 15; j < 15 + 3; j++) {
					cells[i][j].setState(3);
				}
			}
			y -= 2;
			timer += 1;
		}
	}

	@Override
	public void display() {
		super.display();
		sun.display(p);
		bubbles.display();
		bubbles2.display();
		bubbles3.display();

		/*cloud1.draw(p);
		cloud2.draw(p);
		cloud3.draw(p);
		cloud4.draw(p);*/

		turtle1.render();
		turtle2.render();
	}

	public void setAnimalLists(ArrayList<Animal> animals) {
		for (Animal a : animals) {
			PVector pos = a.getPos();
			Patch patch = (Patch) getCell((int) pos.x, (int) pos.y);
			patch.addAnimal(a);
		}
	}

	void clearAnimalLists() {
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				((Patch) cells[i][j]).clearAnimalsList();
			}
		}
	}
}