package ca;

import processing.core.PApplet;

public class CellularAutomata {
	protected int nrows, ncols;
	protected int w, h;
	protected Cell[][] cells;
	protected int radius;
	protected int numberOfStates;
	protected int[] colors;
	protected PApplet p;

	public CellularAutomata(PApplet p, int nrows, int ncols,
							int radius, int numberOfStates) {
		this.p = p;
		this.nrows = nrows;
		this.ncols = ncols;
		this.radius = radius;
		this.numberOfStates = numberOfStates;
		w = p.width/ncols;
		h = p.height/nrows;
		cells = new Cell[nrows][ncols];
		createGrid();
		colors = new int[numberOfStates];
	}

	protected void createGrid(){
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				cells[i][j] = new Cell(this, i, j);
			}
		}
	}

	public void setStateColors(int[] colors) {
		this.colors = colors;
	}

	public int[] getStateColors() {
		return colors;
	}

	public int getCellWidth() {
		return w;
	}

	public int getCellHeight() {
		return h;
	}

	public Cell getCell(int x, int y) {
		int row = y/h;
		int col = x/w;
		if (row >= nrows) row = nrows - 1;
		if (col >= ncols) col = ncols - 1;

		return cells[row][col];
	}

	public void setStates() {
		for (int j = 0; j < ncols; j++) {
			//céu, superfície
			for (int i = 0; i < nrows/2; i++) {
				cells[i][j].setState(0);
			}
			//mar
			for (int i = nrows/2; i < nrows; i++) {
				cells[i][j].setState(1);
			}
		}
		//terreno fértil aleatório
		for (int i = 0; i < 20; i++) {
			cells[(int) (p.random(nrows-nrows/2)+nrows/2)][(int) p.random(ncols)].setState(2);
		}
	}

	public void display() {
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				cells[i][j].display(p);
			}
		}
	}
}