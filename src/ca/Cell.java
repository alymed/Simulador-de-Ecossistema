package ca;
import processing.core.PApplet;

public class Cell {
	protected CellularAutomata ca;
	protected int row, col;
	protected int state;
	protected Cell[] neighbors;
	protected int w, h;

	public Cell(CellularAutomata ca, int row, int col) {
		this.ca = ca;
		this.row = row;
		this.col = col;
		state = 0;
		neighbors = null;
		w = ca.getCellWidth();
		h = ca.getCellHeight();
	}

	public void setState(int state)
	{
		this.state = state;
	}

	public int getState() {
		return state;
	}

	public void display(PApplet p) {
		p.pushStyle();
		p.noStroke();
		p.fill(ca.getStateColors()[state]);
		p.rect(col*w, row*h, w, h);
		p.popStyle();
	}
}