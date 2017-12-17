package cell.data;

import java.util.HashSet;
import java.util.Set;



/**
 * 12/14/2017
 * ViewPort.java
 * @author Maximillian Davatelis <theZiggurat>
 * @version 1.0
 */

public class ViewPort {
	
	public double cellSize, startX, startY, xSize, ySize;
	public Grid grid;
	
	
	/**
	 * Holds window information and interacts between Grid and MovableCanvas
	 * @param cellSize Init size of cells
	 * @param xSize Init size of display X
	 * @param ySize Init size of display Y
	 * @param grid Map of cells to interact with
	 */
	
	public ViewPort(double cellSize, double xSize, double ySize, Grid grid){
		this.startX = 0;
		this.startY = 0;
		this.cellSize = cellSize;
		this.xSize = xSize;
		this.ySize = ySize;
		this.grid = grid;
	}
	
	/**
	 * Updates the coordinates of the viewport
	 * @param d new X value for the viewport
	 * @param e new Y value for the viewport
	 */
	
	public void translate(double d, double e) {
		startX = d;
		startY = e;
	}
	
	
	/**
	 * Creates a set of all the Cell tuples in the viewport
	 * @return Set of tuples 
	 */
	
	public Set<Cell> gatherSeen() {
		
		Set<Cell> ret = new HashSet<Cell>();
		
		for(double [] tuple: gatherTuples()) {
			tuple[0]/=cellSize;
			tuple[1]/=cellSize;
			if(grid.containsKey(tuple[0]+" "+tuple[1])) {
				ret.add(grid.get(tuple[0]+" "+tuple[1]));
			}
		}
		return ret;
	}
	
	/**
	 * Gathers all top-left verticies of cells that belong on the current screen
	 * @return Set <double []> that contains all coordinates of on-screen items  
	 */
	
	public Set<double []> gatherTuples(){
		Set<double []> ret = new HashSet<double []>();
		//System.out.println("Here");
		
		double renderBeginX = startX - (startX%cellSize)-cellSize;
		double renderBeginY = startY - (startY%cellSize)-cellSize;
		
		double renderEndX = startX + xSize;
		double renderEndY = startY + ySize;
		
		for(double X = renderBeginX; X<renderEndX; X+=cellSize) {
			for(double Y = renderBeginY; Y<renderEndY; Y+=cellSize) {
				ret.add(new double[] {X,Y});
			}
		}
		return ret;
	}
	
	
	
	
	public void addCell(int [] tuple) {
		Cell c = new Cell(tuple);
		c.survive();
		grid.put(tuple[0]+" "+tuple[1], c);
		grid.updateNeighbors();
	}
	

	
	
	public void setTileSize(double d) {
		if (cellSize<5&&d<0) {}
		else {
		cellSize += d;
		}
	}
	
	public double getTileSize() {
		return cellSize;
	}
	
	public int [] returnTuple(double pressedX, double pressedY) {
		int [] ret = new int[] {0,0};
		int realX = (int) (pressedX + startX);
		int realY = (int) (pressedY + startY);
		ret[0] = realX/(int)cellSize;
		ret[1] = realY/(int)cellSize;
		return ret;
	}
	
	public void updateSize(int x, int y) {
		xSize = x;
		ySize = y;
	}
	
	public double getXsize() {
		return xSize;
	}
	
	public double getYsize() {
		return ySize;
	}
	
	public double getCurrX() {
		return startX;
	}
	
	public double getCurrY() {
		return startY;
	}
	
	public boolean containsKey(int [] tuple) {
		return grid.ContainsKey(tuple);
	}
	
	public void iterate(){
		grid.iterate();
	}

	}
