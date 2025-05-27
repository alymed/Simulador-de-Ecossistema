package ca;

public class GridConstants {
	public final static int NROWS = 45;
	public final static int NCOLS = 50;
	public static enum State {SKY, OCEAN, FERTILE, FOOD}
	//1,2,3,4
	public final static int NSTATES = State.values().length;
}