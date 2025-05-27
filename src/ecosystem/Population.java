package ecosystem;

import java.util.ArrayList;

import aa.Flock;
import physics.ParticleSystem;
import processing.core.PApplet;
import processing.core.PVector;

public class Population {
    private ArrayList<Animal> allAnimals;
    private ArrayList<Ship> allShips;
    private Flock flock;
    private boolean stochastic = WorldConstants.STOCHASTIC;
    protected float lastTime;
    private PApplet p;
    private int numberOfPreys, numberOfShips;
    private int numberOfPredators, numberOfFlockPreys, numberOfFlock;
    private ParticleSystem burntShip;

    public Population(PApplet p) {
        this.p = p;
        allAnimals = new ArrayList<Animal>();
        allShips = new ArrayList<Ship>();
        this.lastTime = 0;

        flock = new FlockOfPreys(p, WorldConstants.FLOCK_PREY_POPULATION, 5);
        for (int i = 0; i < WorldConstants.FLOCK_PREY_POPULATION; i++) {
            Animal a = (Animal) flock.getBoid(i);
            allAnimals.add(a);
        }

        for (int i = 0; i < WorldConstants.PREY_POPULATION; i++) {
            PVector pos = new PVector(p.random(p.width), p.random(460) + 460);
            Animal a = new Prey(p, pos, 5, 2, p.color(255, 255, 0));
            allAnimals.add(a);
        }

        for (int i = 0; i < WorldConstants.PREDATOR_POPULATION; i++) {
            PVector pos = new PVector(p.random(p.width), p.random(460) + 460);
            Animal a = new Predator(p, pos, 20, 6);
            allAnimals.add(a);
        }

        for (int i = 0; i < WorldConstants.SHIP_POPULATION; i++) {
            PVector pos = new PVector(p.random(1300), 370);
            Ship a = new Ship(p, pos, p.random(10, 30), p.random(220, 250), 50 + (float) Math.random() * 50);
            allShips.add(a);
        }
    }

    private void displayPopulation(PApplet p) {
        p.pushStyle();
        p.fill(0);
        p.textSize(16);
        String Coords = String.format(" Preys | FlockPreys | Predators | Ships\n    " + getNumberOfPreys() + "        "
                + getnumberOfFlock() + " (" + getnumberOfFlockPreys() + ")           " + getNumberOfPredators() + "            "
                + getNumberOfShips());

        p.text(Coords, 10, 20);
        p.popStyle();
    }

    public void update(float dt, Terrain terrain) {
        die(dt, stochastic);
        countAnimals();
        reproduce(dt, stochastic);
        randomSpawnShip();
        applyBehaviour(dt);
        move(dt);
        terrain.setAnimalLists(allAnimals);
        eat(terrain);
        terrain.clearAnimalLists();
    }

    public void randomSpawnShip() {
        PVector shipLeft = new PVector(-175, 370);
        PVector shipRight = new PVector(1790, 370);
        PVector ship = (p.random(1) > 0.5) ? shipLeft : shipRight;

        float now = p.millis();
        if (now >= this.lastTime + 40000) {
            float randomMass = p.random(10, 30);
            float randomIntegrity = 50 + p.random(50);

            Ship newShip = new Ship(p, ship, randomMass, 250, randomIntegrity);
            allShips.add(newShip);
            this.lastTime = now;
        }
    }

    private void applyBehaviour(float dt) {
        for (Animal a : allAnimals) {
            switch (a.getType()) {
                case "Prey":
                    ((Prey) a).applyBehaviour(allAnimals);
                    break;
                case "Predator":
                    ((Predator) a).applyBehaviour(allAnimals);
                    break;
                case "FlockPrey":
                    ((FlockPrey) a).applyBehaviour(allAnimals);
                    break;
            }
        }
        for (Ship s : allShips) {
            s.applyBehaviour(allShips);
            fishing();
        }
    }

    public void fishing() {
        for (Ship s : allShips) {
            if (!s.inFight) {
                for (int i = allAnimals.size() - 1; i >= 0; i--) {
                    Animal a = allAnimals.get(i);
                    if (s.tryToFish(a) && s.integraty < 100) {
                        System.out.println("Pescou!");
                        s.integraty += (int) a.energy / 40;
                        allAnimals.remove(a);
                    }
                }
            }
        }
    }

    private void move(float dt) {
        if (burntShip != null) burntShip.move(dt);
        for (Ship s : allShips) s.move(dt);
        for (Animal a : allAnimals) a.move(dt);
    }

    private void eat(Terrain terrain) {
        for (int i = allAnimals.size() - 1; i >= 0; i--) {
            Animal a = allAnimals.get(i);
            a.eat(terrain, allAnimals);
        }
    }

    private void die(float dt, boolean stochastic) {
        for (int i = allAnimals.size() - 1; i >= 0; i--) {
            Animal a = allAnimals.get(i);
            if (a.die(dt, stochastic))allAnimals.remove(a);
        }
        for (int i = allShips.size() - 1; i >= 0; i--) {
            Ship s = allShips.get(i);
            if (s.integraty < 5) {
                allShips.remove(s);
                burntShip = new ParticleSystem(p, new PVector(s.getPos().x + 5, (s.getPos().y + s.getRadius() / 3)),
                        new PVector(0, 0), 3, new PVector(10, -50), p.color(89, 35, 13), 10, 2);
            }
        }
    }

    private void reproduce(float dt, boolean stochastic) {
        for (int i = allAnimals.size() - 1; i >= 0; i--) {
            Animal a = allAnimals.get(i);
            Animal child = a.reproduce(dt, stochastic);
            if (child != null) allAnimals.add(child);
        }
    }

    public void countAnimals() {
        numberOfPreys = 0;
        numberOfPredators = 0;
        numberOfShips = 0;
        numberOfFlockPreys = 0;
        numberOfFlock = 0;
        for (Animal a : allAnimals) {
            switch (a.getType()) {
                case "Prey":
                    numberOfPreys++;
                    break;
                case "Predator":
                    numberOfPredators++;
                    break;
                case "FlockPrey":
                    numberOfFlockPreys++;
                    if (numberOfFlockPreys > 0) numberOfFlock = 1;
                    break;
            }
        }
        for (Ship s : allShips) {
            numberOfShips++;
        }
    }

    public int getNumberOfPreys() {
        return numberOfPreys;
    }

    public int getNumberOfPredators() {
        return numberOfPredators;
    }

    public int getNumberOfShips() {
        return numberOfShips;
    }

    public int getnumberOfFlockPreys() {
        return numberOfFlockPreys;
    }

    public int getnumberOfFlock() {
        return numberOfFlock;
    }

    public void display() {
        displayPopulation(p);
        if (burntShip != null) burntShip.display();
        for (Ship s : allShips) s.display();
        for (Animal a : allAnimals) a.display();
    }
}