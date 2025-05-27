package ecosystem;
import processing.IProcessingApp;
import processing.core.PApplet;
import processing.sound.SoundFile;

public class EcoSystemApp implements IProcessingApp {
	private World world;
	private SoundFile music;

	@Override
	public void setup(PApplet p) {
		try {
			music = new SoundFile(p, "img/music.mp3");
			music.play();
		} catch (Exception e) {
			System.out.println("Loading sound...");
		}
		new WorldConstants(p);
		world = new World(p);
	}

	@Override
	public void draw(PApplet p, float dt) {
		world.update(dt);
		world.display(p);
	}
}