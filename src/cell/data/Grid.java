package cell.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import cell.app.Ruleset;

/**
 * 12/14/2017
 * Grid.java
 * @author Maximillian_Davatelis <theZiggurat>
 * @version 1.0
 */

public class Grid extends HashMap<String, Cell>{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<Cell> retArr = new ArrayList<Cell>();
	Ruleset Rules;
	
	/**
	 * Holds all the existing cells that are interesting for interations (is alive
	 * or has neighbors that are alive). 
	 * TODO: scrapping old cells with no neighbor to save space
	 */
	
	public Grid(Ruleset Rules) {
		this.Rules = Rules;
	}
	
	
	
	/**
	 * Creates collection of all "interesting" nodes to do calculations and of neighbors.
	 */
	
	public void updateNeighbors() {
		Collection<Cell> iter = values();
		Iterator<Cell> i = iter.iterator();
		
		while(i.hasNext()) {
			gatherNeighbors(i.next());
		}
		
		Iterator<Cell> i2 = retArr.iterator();
		
		while(i2.hasNext()) {
			Cell baby = i2.next();
			gatherNeighbors(baby);
			put(baby.getKey(), baby);
		}
		
		retArr.clear();
	}
	
	
	
	
	
	public void iterate() {
		Collection<Cell> iter = values();
		Iterator<Cell> i = iter.iterator();
		while(i.hasNext()) {
			Cell c = i.next();
			if(c.getAge()!=-1) { //initially alive
				if(Rules.survives(c)) {
					c.survive();
					System.out.println("Survive: "+ c.getKey());
				}
				else {
					c.kill();
					System.out.println("Died: "+c.getKey());
				}
			}
			
			else { //initially dead
				if(Rules.born(c)) {
					c.survive();
					System.out.println("Born: "+ c.getKey());
				}
			}
		}
		updateNeighbors();
	}
	
	
	/*public List<Cell> createList(){
		Collection<Cell> iterable = values();
		
		
		
		
	}*/
	
	public void setNeighbors(Cell c) {
		
	}
	
	
	
	/**
	 * 
	 * @param c Cell to gather edges from
	 * @return Array of 8 cells which all border the input cell. If cell didn't exist,
	 * empty cell is created.
	 */
	
	public void gatherNeighbors(Cell c) {
		ArrayList<Cell> retArr = new ArrayList<Cell>();
		
		int [] indexes = {-1,-1,-1,0,-1,1,0,-1,0,1,1,-1,1,0,1,1};
		int x = c.getX(), y = c.getY();
		int neighbors = 0;
		Cell curr;
		int [] tuple = new int[2];
		
		for(int i = 0; i<indexes.length;i++){
			
			tuple[0] = indexes[i] + x; i++;
			tuple[1] = indexes[i] + y;
			
			curr = Get(tuple);
			
			if(curr == null&&!retArr.contains(curr)&&c.getAge()!=-1) { // if cell is currently alive, look at its neighbors that are not in the master map yet
				retArr.add(Get(tuple));
				System.out.println("Added: "+ c.getKey());
			}
			else if(curr!=null){							   // if cell is in master map, and it is not alive, don't count it and don't add it to arraylist
				if(curr.getAge()!=-1) {neighbors++;}
			}
		}
		c.setNeighbors(neighbors);
	}
	
	
	/**
	 * Prints to the console for debugging purposes
	 */
	
	public void debug() {
		System.out.println("Total Cells: "+size());
	}
	
	/**
	 * Simple conversion from tuple to key
	 * @param tuple coordinates
	 * @return if said coordinate is mapped
	 */
	
	public boolean ContainsKey(int [] tuple) {
		if(containsKey(tuple[0]+""+tuple[1])) {return true;}
		return false;
	}
	
	
	/**
	 * Simple conversion from tuple to key
	 * @param tuple coordinates
	 * @return Cell in coordinate map
	 */
	
	
	public Cell Get(int[] tuple) {
		return this.get(tuple[0]+" " +tuple[1]);
	}
	
	
	
	

}
