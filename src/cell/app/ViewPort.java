package cell.app;

import java.util.HashSet;
import java.util.Set;

import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * 12/14/2017
 * ViewPort.java
 * @author Maximillian Davatelis <theZiggurat>
 * @version 1.0
 */

public class ViewPort {
	
	int startX, startY, cellSize, xSize, ySize;
	Grid grid;
	
	
	/**
	 * Holds window information and interacts between Grid and MovableCanvas
	 * @param cellSize Init size of cells
	 * @param xSize Init size of display X
	 * @param ySize Init size of display Y
	 * @param grid Map of cells to interact with
	 */
	
	public ViewPort(int cellSize, int xSize, int ySize, Grid grid){
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
		startX = (int) d;
		startY = (int) e;
	}
	
	
	/**
	 * Creates a set of all the Cell tuples in the viewport
	 * @return Set of tuples 
	 */
	
	public Set<Cell> gatherSeen() {
		
		Set<Cell> ret = new HashSet<Cell>();
		
		for(int [] tuple: gatherTuples()) {
			tuple[0]/=cellSize;
			tuple[1]/=cellSize;
			if(grid.containsKey(tuple[0]+" "+tuple[1])) {
				ret.add(grid.get(tuple[0]+" "+tuple[1]));
			}
		}
		return ret;
	}
	
	/**
	 * 
	 * @return
	 */
	
	public Set<int []> gatherTuples(){
		Set<int []> ret = new HashSet<int []>();
		//System.out.println("Here");
		
		int renderBeginX = startX - (startX%cellSize)-cellSize;
		int renderBeginY = startY - (startY%cellSize)-cellSize;
		
		int renderEndX = startX + xSize;
		int renderEndY = startY + ySize;
		
		for(int X = renderBeginX; X<renderEndX; X+=cellSize) {
			for(int Y = renderBeginY; Y<renderEndY; Y+=cellSize) {
				ret.add(new int[] {X,Y});
			}
		}
		return ret;
	}
	
	public int getCurrX() {
		return startX;
	}
	
	public int getCurrY() {
		return startY;
	}
	
	public void updateSize(int x, int y) {
		xSize = x;
		ySize = y;
	}
	
	public void setTileSize(int t) {
		if (cellSize<5&&t<0) {}
		else {
		cellSize += t;
		}
	}
	
	public int getTileSize() {
		return cellSize;
	}
	
	public int [] returnTuple(double pressedX, double pressedY) {
		int [] ret = new int[] {0,0};
		int realX = (int) (pressedX + startX);
		int realY = (int) (pressedY + startY);
		ret[0] = realX/cellSize;
		ret[1] = realY/cellSize;
		return ret;
	}

}
