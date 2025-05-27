package ecosystem;

import ca.GridConstants;
import processing.core.PApplet;

public class WorldConstants {
	// Terrain
	public final static float[] REGENERATION_TIME = {10.f, 15.f};
	public static int[] TERRAIN_COLORS;

	// Prey Population
	public final static int PREY_POPULATION = 20;
	public final static float PREY_ENERGY = 100f;
	public final static float ENERGY_FROM_PLANT = 25f;
	public final static float PREY_ENERGY_TO_REPRODUCE = 200f;
	public final static float PREY_BIRTH_RATE = 0.2f;
	public final static float PREY_DEATH_RATE = 0.02f;

	// Flock Prey Population
	public final static int FLOCK_PREY_POPULATION = 15;

	// Predator Population
	public final static int PREDATOR_POPULATION = 3;
	public final static float PREDATOR_ENERGY = 100f;
	public final static float ENERGY_FROM_PREY = 10f;
	public final static float PREDATOR_ENERGY_TO_REPRODUCE = 200f;
	public final static float PREDATOR_BIRTH_RATE = 0.02f;
	public final static float PREDATOR_DEATH_RATE = 0.15f;

	// Ship Population
	public final static int SHIP_POPULATION = 3;

	// Simulation mode
	public final static boolean STOCHASTIC = false;

	public WorldConstants(PApplet p) {
		TERRAIN_COLORS = new int[GridConstants.NSTATES];

		TERRAIN_COLORS[0] = p.color(190,244,255);
		TERRAIN_COLORS[1] = p.color(0, 95, 255);
		TERRAIN_COLORS[2] = p.color(179,225,172);
		TERRAIN_COLORS[3] = p.color(60,160,60);
	}
}
