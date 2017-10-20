import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class DrawingCanvas extends Canvas {

	private BufferStrategy buffer;
	private Graphics g;
	int rows;
	int cols;
	final int CELL_WIDTH = 40;
	ArrayList<Cell> grid;
	Cell current;
	ArrayList<Cell> stack;

	public DrawingCanvas(int width, int height) {
		setSize(width, height);
		cols = (int) (width / CELL_WIDTH);
		rows = (int) (height / CELL_WIDTH);
		grid = new ArrayList<Cell>();

		for (int j = 0; j < rows; j++) { //J or Y are the rows
			for (int i = 0; i < cols; i++) { //I or X are the columns
				Cell cell = new Cell(i, j, this);
				grid.add(cell);
			}
		}
		current = grid.get(0);
		stack = new ArrayList<Cell>();
	}

	public void draw() {
		buffer = getBufferStrategy();
		if (buffer == null) {
			createBufferStrategy(2);
			return;
		}
		g = buffer.getDrawGraphics();

			//DRAW HERE
			g.setColor(new Color(51, 51, 51)); //Background
			g.fillRect(0, 0, canvasSize().intX(), canvasSize().intY());

			for (int i = 0; i < grid.size(); i++) { //Draw the cells
				grid.get(i).show(g);
			}

			current.visited = true;
			current.highlight(g);
			//STEP 1
			Cell next = current.checkNeighbors();
			if (next != null) {
				next.visited = true;
				//STEP 2
				stack.add(current);

				//STEP 3
				removeWalls(current, next);

				//STEP 4
				current = next;
			}
			else if (stack.size() > 0) {
				Cell back = stack.get(stack.size() - 1);
				stack.remove(stack.size() - 1);
				current = back;
			}

		g.dispose();
		buffer.show();
	}

	public JVector canvasSize() {
		return new JVector(this.getWidth(), this.getHeight());
	}

	public void removeWalls(Cell a, Cell b) {
		int x = a.i - b.i;
		if (x == 1) {
			a.walls[3] = false;
			b.walls[1] = false;
		}
		else if (x == -1) {
			a.walls[1] = false;
			b.walls[3] = false;
		}

		int y = a.j - b.j;
		if (y == 1) {
			a.walls[0] = false;
			b.walls[2] = false;
		}
		else if (y == -1) {
			a.walls[2] = false;
			b.walls[0] = false;
		}
	}

}