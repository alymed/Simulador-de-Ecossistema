package processing;



import ecosystem.EcoSystemApp;
import processing.core.PApplet;

public class ProcessingSetup extends PApplet {

    private static IProcessingApp processingApp;
    private float lastUpdateTime;


    @Override
    public void settings() {
        size(1800, 900);
    }

    @Override
    public void setup() {
        processingApp.setup(this);
        lastUpdateTime = 0;
    }

    @Override
    public void draw() {
        float now = millis();
        float dt = (float) ((now - lastUpdateTime) / 1000.);
        processingApp.draw(this, dt);
        lastUpdateTime = now;
    }

    public static void main(String[] args) {
        processingApp = new EcoSystemApp();
        PApplet.main(ProcessingSetup.class);
    }
}