package cell.data;

/**
 * 12/14/2017
 * Cell.java
 * @author Maximillian_Davatelis <theZiggurat>
 * @version 1.1
 */

public class Cell {
	
	private int age, neighbors;
	private int[] tuple;
	
	/**
	 * Smallest building block of cellular automata. Cells are small objects that hold
	 * coordinates, state of itself (age) and state of immediate neighbors
	 * @param tuple [x,y] representing the coordinates of cell
	 */
	
	public Cell(int [] tuple) {
		this.tuple = tuple;
		this.age = -1;
		this.neighbors = 0;
	}
	
	/**
	 * Increments age of cell
	 */
	
	public void survive() {
		age++;
	}
	
	/**
	 *  Resets age to dead age
	 */
	
	public void kill() {
		age = -1;
	}
	
	/**
	 * @return Age of cell in iterations
	 */
	
	public int getAge() {
		return age;
	}
	
	
	/**
	 * Returns key for use in Grid map
	 * @return integer tuple for key
	 */
	
	public String getKey() {
		return (tuple[0]+" "+tuple[1]);
	}
	
	/**
	 * @return X coordinate
	 */
	
	public int getX() {
		return tuple[0];
	}
	
	/**
	 * @return Y coordinate
	 */
	public int getY() {
		return tuple[1];
	}
	
	/**
	 * Updates number of neighbors for a cell
	 * @param number of neighbors
	 */
	
	public void setNeighbors(int n) {
		neighbors = n;
	}
	
	/**
	 * @return number of neighbors
	 */
	public int getNeighbors() {
		return neighbors;
	}
	
	/**
	 * Returns location, age, and neighbors of input cell in console
	 */
	public void fullDebug() {
		System.out.print("X: " + tuple[0]+",");
		System.out.print("Y: " + tuple[1]+",");
		System.out.print("Age: " + age+",");
		System.out.println("Neighbors: " + neighbors);
	}
	
	/**
	 * Returns key of input cell in console
	 */
	public void debug() {
		System.out.println("{"+tuple[0]+","+tuple[1]+"}");
	}
	

}
