package physics;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class ParticleSystem extends Mover {
    private ArrayList<Particle> particles;
    private PVector particleSpeed;
    private int particleColor;
    private float lifetime;
    protected PApplet p;

    public ParticleSystem(PApplet p, PVector pos, PVector vel, float mass,
                          PVector particleSpeed, int particleColor, float particleRadius,
                          float lifetime) {
        super(pos, vel, mass, particleRadius);
        this.particleSpeed = particleSpeed;
        this.particleColor = particleColor;
        this.lifetime = lifetime;
        this.particles = new ArrayList<>();
        this.p = p;
    }

    private void addParticle() {
        float vx = (float) (particleSpeed.x * 2 * (Math.random() - 0.5));
        float vy = (float) (particleSpeed.y * 2 * (Math.random() - 0.5));
        particles.add(new Particle(p, pos, new PVector(vx, vy), particleColor,
                radius, lifetime));
    }

    @Override
    public void move(float dt) {
        super.move(dt);
            addParticle();
            for (int i = particles.size() - 1; i >= 0; i--) {
                Particle p = particles.get(i);
                p.move(dt);
                if (p.isDead()){
                    particles.remove(i);
                    addParticle();
            }
        }
    }

    public void setLifetime(float lifetime) {
        this.lifetime = lifetime;
    }

    public void display() {
        for (Particle particle : particles) particle.display();
    }
}