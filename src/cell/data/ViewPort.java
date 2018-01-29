package cell.data;

import java.util.HashSet;
import java.util.Set;

/**
 * 12/14/2017
 * ViewPort.java
 * @author Maximillian Davatelis <theZiggurat>
 * @version 1.1
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
			
			if(containsKey(tuple)) {
				ret.add(grid.Get(convert(tuple)));
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
	
	/**
	 * Returns x and y coordinate of first location where gridlines should be drawn
	 * @return x and y of render start
	 */
	public double[] topLeft(){
		double [] ret = new double[2];
		ret[0] = startX - (startX%cellSize) - cellSize;
		ret[1] = startY - (startY%cellSize) - cellSize;
		return ret;
	}
	
	/**
	 * For interfacing with canvas for the purpose of inserting cells
	 * @param tuple
	 * @return
	 */
	
	public boolean catchCoord(double [] tuple) {
		tuple = returnTuple(tuple);
		int [] gridCoord = convert(tuple);
		if(!grid.ContainsKey(gridCoord)) {
			addCell(gridCoord);
			return true;
		}
		else{
			grid.remove(gridCoord[0]+""+gridCoord[1]);
			addCell(gridCoord);
		}
		return false;
	}
	
	
	
	/**
	 * 
	 * @param tuple
	 */
	public void addCell(int [] tuple) {
		grid.Put(tuple);
		grid.Get(tuple).survive();
		grid.changed();
	}
	
	
	
	/**
	 * Changes tile size for the purpose of zooming in and out
	 * @param d TileSize
	 */
	public void setTileSize(double d) {
		if (cellSize<5&&d<0) {}
		else {cellSize += d;}
	}
	
	
	/**
	 * Converts from double tuple used in canvas to int tuple used in grid
	 * @param tuple Double coordinates
	 * @return Integer coordinates
	 */
	
	public int [] convert(double [] tuple) {
		int [] convert = new int[2];
		convert[0] = (int) Math.floor(tuple[0]);
		convert[1] = (int) Math.floor(tuple[1]);
		return convert;
	}
	
	
	/**
	 * Maps canvas coordinates to viewport coordinates
	 * @param pressedX X location on canvas
	 * @param pressedY Y location on canvas
	 * @return canvas coordinate mapped to viewport
	 */
	
	public double [] returnTuple(double[] tuple) {
		double [] ret = new double[2];
		ret[0] = (tuple[0] + startX)/cellSize;
		ret[1] = (tuple[1] + startY)/cellSize;
		return ret;
	}
	
	
	/**
	 * Changes size of viewpane 
	 * @param x Width
	 * @param y Height
	 */
	
	public void updateSize(int x, int y) {
		xSize = x;
		ySize = y;
	}
	
	
	/**
	 * Iterate function to be called from canvas
	 */
	public void iterate(){
		grid.iterate();
	}
	
	// SETTERS AND GETTERS
	
	
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
	
	public double getTileSize() {
		return cellSize;
	}
	
	
	/**
	 * Returns true if containing grid contains alive cell at input location
	 * @param tuple location of cell
	 * @return boolean if cell is there or not
	 */
	public boolean containsKey(double [] tuple) {
		return grid.ContainsKey(convert(tuple));
	}

}
