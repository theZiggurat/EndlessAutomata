package cell.app;

import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;

/**
 * 12/14/2017
 * Grid.java
 * @author Maximillian_Davatelis <theZiggurat>
 * @version 1.0
 */

public class Grid extends HashMap<String, Cell>{
	
	
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
		Cell[] block;
		for(Cell c: iter) {
			block = gatherBlock(c);
			int currNeighbors = 0;
			for(Cell C: block) {
				if(!iter.contains(C)&&c.getAge()!=-1) {iter.add(C);}
				if(c.getAge()!=-1) {currNeighbors++;}
			}
			c.setNeighbors(currNeighbors);
		}
	}
	
	/**
	 * 
	 * @param c Cell to gather edges from
	 * @return Array of 8 cells which all border the input cell. If cell didn't exist,
	 * empty cell is created.
	 */
	
	
	
	public Cell[] gatherBlock(Cell c) {
		Cell[] ret = new Cell[8];
		
		int [] indexes = {-1,-1,-1,0,-1,1,0,-1,0,1,1,-1,1,0,1,1};
		int x = c.getX(), y = c.getY();
		Cell curr;
		int [] tuple = new int[2];
		
		for(int i = 0; i<indexes.length;i++){
			
			tuple[0] = indexes[i] + x; i++;
			tuple[1] = indexes[i] + y;
			
			curr = Get(tuple);
			
			if(curr == null) {
				put(tuple[0]+" "+tuple[1], new Cell(tuple));
				ret[i/2] = Get(tuple);
			}
			else 
			{
				ret[i/2] = curr;
			}
		}
		return ret;
	}
	
	public void debug() {
		System.out.println("Total Cells: "+size());
	}
	
	
	
	public boolean ContainsKey(int [] tuple) {
		if(containsKey(tuple[0]+""+tuple[1])) {return true;}
		return false;
	}
	
	
	public Cell Get(int[] tuple) {
		return this.get(tuple[0]+" " +tuple[1]);
	}
	
	
	
	

}
