import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;

public class Cell {

	int i;
	int j;
	boolean[] walls;
	boolean visited;
	int cols;
	int rows;
	DrawingCanvas canvas;
	int width;

	public Cell(int i, int j, DrawingCanvas canvas) {
		this.canvas = canvas;
		this.width = canvas.CELL_WIDTH;
		this.cols = canvas.cols;
		this.rows = canvas.rows;
		this.i = i;
		this.j = j;
		this.visited = false;
		this.walls = new boolean[] {true, true, true, true};
	}

	public void show(Graphics g) {
		int x = this.i * width;
		int y = this.j * width;

		g.setColor(Color.WHITE);
		if (this.walls[0]) {
			g.drawLine(x, y, x+width, y); //Top Wall
		}
		if (this.walls[1]) {
			g.drawLine(x+width, y, x+width, y+width); //Right Wall
		}
		if (this.walls[2]) {
			g.drawLine(x+width, y+width, x, y+width); //Bot wall
		}
		if (this.walls[3]) {
			g.drawLine(x, y+width, x, y); //Left wall
		}

		if (this.visited) {
			g.setColor(new Color(255, 0, 255, 100));
			g.fillRect(x, y, width, width);
		}
	}

	public void highlight(Graphics g) {
		int x = this.i * width;
		int y = this.j * width;
		g.setColor(new Color(0, 0, 255, 100));
		g.fillRect(x, y, width, width);
	}

	public Cell checkNeighbors() {
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		//Look for the neighbors
		Cell top = null;
		Cell right = null;
		Cell bottom = null;
		Cell left = null;
		if (getIndex(i, j-1) != -1) {
			top = canvas.grid.get(getIndex(i, j-1));
		}
		if (getIndex(i+1, j) != -1) {
			right = canvas.grid.get(getIndex(i+1, j));
		}
		if (getIndex(i, j+1) != -1) {
			bottom = canvas.grid.get(getIndex(i, j+1));
		}
		if (getIndex(i-1, j) != -1) {
			left = canvas.grid.get(getIndex(i-1, j));
		}
		//Add them is they exist
		if (top != null && !top.visited) {
			neighbors.add(top);
		}
		if (right != null && !right.visited) {
			neighbors.add(right);
		}
		if (bottom != null && !bottom.visited) {
			neighbors.add(bottom);
		}
		if (left != null && !left.visited) {
			neighbors.add(left);
		}

		if (neighbors.size() > 0) {
			int rand = (int) (Math.random()*neighbors.size());
			return neighbors.get(rand);
		}
		else {
			return null;
		}
	}

	public int getIndex(int i, int j) { //Convert a 2D array into a 1D one
		if (i < 0 || j < 0 || i >= cols || j >= rows) {
			return -1;
		}
		return i + j * cols;
	}

}