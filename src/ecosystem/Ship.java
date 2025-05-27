package ecosystem;

import aa.Boid;
import physics.ParticleSystem;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;

public class Ship extends Boid {
    protected float integraty;
    protected float chance;
    protected float timeToFix, timeToFish, timeToShoot;
    protected String type, secondType;
    protected boolean sentido, inFight;
    protected PVector netCord;
    protected Ship shipToAtack;
    protected PImage shipDraw, netE, netD;
    protected ParticleSystem rasto, shot;

    public Ship(PApplet p, PVector pos, float mass, float radius, float integridade) {
        super(p, pos, mass, radius);
        this.integraty = integridade;
        this.timeToFix = 0;
        this.timeToFish = 0;
        this.timeToShoot = 0;
        this.chance = 0;
        this.netE = p.loadImage("img/netE.png");
        this.netD = p.loadImage("img/netD.png");

        rasto = new ParticleSystem(p, new PVector(pos.x + 5, pos.y + radius / 3), new PVector(0, 0), 1, new PVector(-30, 10), p.color(255), 7, 3);
        float probability = p.random(1);
        sentido = !(probability < 0.5);
        inFight = false;

        if (this.integraty >= 75 && this.mass >= 20 && this.radius >= 240) {
            this.type = "bigShip";
            this.secondType = "PirateShip";
        } else {
            this.type = "smallShip";
            if (this.integraty <= 60 && this.radius >= 220) this.secondType = "Caravela";
            else this.secondType = "Ship";
        }
        netCord = new PVector(((pos.x + 70) - 80), (pos.y - 145) + radius);
    }


    @Override
    public void display() {
        netCord.x = (pos.x + 70) - 80;
        netCord.y = (pos.y - 145) + radius;
        if (shot != null) shot.display();
        rasto.display();
        if (type.equals("bigShip")) drawBigShip();
        else if (type.equals("smallShip")) drawSmallShip();
    }

    public void drawSmallShip() {
        if (this.secondType.equals("Caravela")) {
            if (this.sentido) {
                p.image(netE, netCord.x + 10, netCord.y - 25, 180, 100);
                this.shipDraw = p.loadImage("img/caravelaD.png");
            } else {
                p.image(netD, netCord.x + 60, netCord.y - 25, 180, 100);
                this.shipDraw = p.loadImage("img/caravelaE.png");
            }
        }
        if (this.secondType.equals("Ship")) {
            if (this.sentido) {
                p.image(netE, netCord.x + 20, netCord.y - 20, 180, 100);
                this.shipDraw = p.loadImage("img/navioD.png");

            } else {
                p.image(netD, netCord.x + 50, netCord.y - 20, 180, 100);
                this.shipDraw = p.loadImage("img/navioE.png");
            }
        }
        p.image(shipDraw, pos.x, pos.y - 145, radius, radius);
    }

    public void drawBigShip() {
        if (secondType.equals("PirateShip")) {
            if (this.sentido) {
                p.image(netE, netCord.x + 20, netCord.y - 25, 180, 100);
                this.shipDraw = p.loadImage("img/navio pirataD.png");
            } else {
                p.image(netD, netCord.x + 40, netCord.y - 25, 180, 100);
                this.shipDraw = p.loadImage("img/navio pirataE.png");
            }
        }
        p.image(shipDraw, pos.x, pos.y - 145, radius, radius);
    }

    @Override
    public void move(float dt) {
        super.move(dt);
        rasto.move(dt);
        if (shot != null) shot.move(dt);
    }

    public PVector navegar() {
        float targetX;
        if (sentido) targetX = pos.x + 200;
        else targetX = pos.x - 200;
        return new PVector(targetX, pos.y);
    }

    public void checkAndTurn() {
        if (pos.x < 60 && pos.x > 20) sentido = true;
        if (pos.x < 19) sentido = true;
        else if (pos.x > 1360) sentido = false;
    }

    public void repairShip() {
        if (!inFight) {
            float now = p.millis();
            if (now >= timeToFix + 5000 && this.integraty <= 92) {
                this.integraty += 2;
                this.timeToFix = p.millis();
            }
        }
    }

    public void fight() {
        if (shouldShoot()) {
            if (hitTarget()) {
                shot = new ParticleSystem(p, new PVector(shipToAtack.pos.x + 100, shipToAtack.pos.y + 30), new PVector(0, 0),
                        2, new PVector(20, 20), p.color(0), 5, 4);
                takeDamage(12);
                System.out.println("Atingiu! Vida do inimigo: " + shipToAtack.integraty);
                if (shipToAtack.integraty < 5) {
                    shot.setLifetime(0);
                }
            }
        }
    }

    public void takeDamage(float damage) {
        shipToAtack.integraty -= damage;
    }

    public boolean shouldShoot() {
        float now = p.millis();
        if (now >= timeToShoot + p.random(3000, 5000)) {
            timeToShoot = p.millis();
            return true;
        }
        return false;
    }

    public boolean hitTarget() {
        if (shipToAtack.type.equals("bigShip")) {
            this.chance = 0.7f;
        }
        if (shipToAtack.type.equals("smallShip")) {
            this.chance = 0.55f;
        }
        float rnd = p.random(1);
        return rnd < this.chance;
    }


    public boolean chaseAfterFight() {
        if (inFight) {
            inFight = false;
            return this.integraty > shipToAtack.integraty;
        }
        return false;
    }

    public boolean needToFight(ArrayList<Ship> allShips) {
        for (Ship a : allShips) {
            PVector distVector = PVector.sub(a.getPos(), pos);
            float dist = distVector.mag();

            if (dist <= 200 && dist != 0.0) {
                shipToAtack = a;
                inFight = true;
                return true;
            }
        }
        return false;
    }

    public boolean tryToFish(Animal peixe) {
        return (peixe.getPos().x >= netCord.x && peixe.getPos().x <= netCord.x + 180 &&
                peixe.getPos().y >= netCord.y && peixe.getPos().y <= netCord.y + 100);
    }


    public void applyBehaviour(ArrayList<Ship> allShips) {
        checkAndTurn();
        repairShip();

        if (needToFight(allShips)) {
            applyForce(brake());
            rasto.applyForce(brake().div(mass));
            fight();
        } else {
            if (chaseAfterFight()) {
                float rnd = p.random(1);
                if (rnd < 0.5) sentido = !sentido;
            }

            PVector vd = PVector.sub(navegar(), pos);
            vd.normalize().mult((float) (this.integraty / 100.0 * dna.maxSpeedShip));
            PVector f = PVector.sub(vd, vel);
            applyForce(f);
            rasto.applyForce(f.div(mass));
        }
    }
}