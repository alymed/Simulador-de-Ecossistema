package ecosystem;

import processing.core.PApplet;

public class World {
	private Terrain terrain;
	private Population population;
	
	public World(PApplet p) {
		terrain = new Terrain(p);
		terrain.setStateColors(WorldConstants.TERRAIN_COLORS);
		terrain.setStates();
		population = new Population(p);
	}

	public void update(float dt) {
		terrain.regenerate(dt);
		population.update(dt, terrain);
	}

	public void display(PApplet p) {
		terrain.display();
		population.display();
	}
}