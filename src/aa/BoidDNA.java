package aa;
import processing.core.PApplet;

public class BoidDNA {
	public float maxSpeed;
	public float maxForce;
	public float maxSpeedShip;
	public float maxForceShip;
	public float visionDistanceSmallPrey;
	public float visionDistanceSmallPredator;
	public float visionAngle;
	public float deltaTPursuit;
	public float deltaTWander;
	public float deltaPhiWander;
	public float radiusWander;
	public int visionDistanceLargePrey;
	public int visionDistanceLargePredator;
	public float visionDistanceLarge;
	public float visionDistanceSmall;

	public BoidDNA(PApplet p) {
		// physics
		maxSpeed = p.random(60,100);
		maxForce = p.random(200,400);
		maxSpeedShip = 35;
		maxForceShip = 500F;
		// vision
		visionDistanceLarge = p.random(100,150);
		visionDistanceLargePrey = 100;
		visionDistanceLargePredator = 130;
		visionDistanceSmall = 0.8f*visionDistanceLarge;
		visionDistanceSmallPredator = 0.8f*visionDistanceLargePredator;
		visionDistanceSmallPrey = 0.8f*visionDistanceLargePrey;
		visionAngle = (float)Math.PI/6;
		// pursuit behavior
		deltaTPursuit = 0.8f;
		// wander behavior
		deltaTWander = 0.5f;
		deltaPhiWander = (float)(Math.PI/4);
		radiusWander = 150;
	}
}
