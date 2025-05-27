package ecosystem;

import aa.Flock;
import processing.core.PApplet;
import processing.core.PVector;

public class FlockOfPreys extends Flock {
    public FlockOfPreys(PApplet p, int npreys, int radius) {
        super(p, npreys, radius, 2);
    }

    @Override
    public void createFlock(PApplet p, int nboids) {
        for(int i=0;i<nboids;i++) {
            Animal a = new FlockPrey(p, new PVector(p.random(p.width),
                    p.random(p.height)), this,p.color(255,99,71));
            a.setVel(new PVector(p.random(-2,2),p.random(-2,2)));
            boids.add(a);
        }
    }
}