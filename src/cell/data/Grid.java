package cell.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import cell.lib.Ruleset;

/**
 * 12/14/2017
 * Grid.java
 * @author Maximillian_Davatelis <theZiggurat>
 * @version Alpha 2.0
 */

public class Grid extends ConcurrentHashMap<String, Cell>{
	
	
	private boolean changed = false;
	ArrayList<Cell> retArr = new ArrayList<Cell>();
	static Ruleset Rules;
	long totalTime = 0;
	
	/**
	 * Holds all the existing cells that are interesting for interations (is alive
	 * or has neighbors that are alive). 
	 */
	
	public Grid(Ruleset Rules) {
		Grid.Rules = Rules;
	}
	
	
	
	
	// STATE CHANGING METHODS --------------------------------------------------------------------------------------
	
	
	/**
	 * Goes through all cells in hashmap and updates age based on whether cell survives or not (depending on neighbors)
	 */
	public void iterate() {
		
		long pretime = System.nanoTime();
		
		if(changed) {updateNeighborsAll(); changed = false;}
		Iterator<Cell> i = values().iterator();
		
		while(i.hasNext()) {
			Cell c = i.next();
			
			// initially alive
			if(c.getAge()!=-1) { 
				if(Rules.survives(c)) {c.survive();}
				else {
					c.kill();
					remove(c.getKey());
				}
			}
			
			// initially dead
			else { 
				if(Rules.born(c)) {c.survive();}
				else {remove(c.getKey());}
			}
		}
		updateNeighborsAll();
		totalTime = (System.nanoTime() - pretime)/1000000;
		debug();
	}
	
	/**
	 * 
	 */
	public void updateNeighborsAll() {
		List<Cell> set = buildSet();
		Iterator<Cell> iter = set.iterator();
		
		Cell curr; int neighbors;
		
		while(iter.hasNext()) {
			curr = iter.next();
			neighbors = 0;
			
			for(Cell neighbor: gatherDirectNeighbors(curr, false)) {
				if(neighbor.getAge()!=-1) {neighbors++;}
			}
			curr.setNeighbors(neighbors);
			put(curr.getKey(), curr);
		}
	}
	
	
	/**
	 * Only to be used in the case a the input cell was created from an outisde source (click event, etc.).
	 * @param c Cell that was just created
	 */
	
	public void updateNeighborsOnClick(Cell c) {
		int neighbors = 0;
		for(Cell neighbor: gatherDirectNeighbors(c, true)) {
			if(neighbor.getAge()!=-1) {neighbors++;}
			neighbor.setNeighbors(neighbor.getNeighbors()+1);
		}
		c.setNeighbors(neighbors);
	}
	
	
	/**
	 * Gathers all alive cells and the alive cell's direct neighbors for any given state of the map.
	 * @return Set of cells that are alive or have neighbors that are alive
	 */
	public List<Cell> buildSet() {
	
		Set <Cell> ret = new HashSet<Cell>();
		Collection<Cell> forIterating = new HashSet<Cell>();
		ret.addAll(values().stream().collect(Collectors.toSet()));
		forIterating.addAll(values().stream().collect(Collectors.toSet()));
		
		
		Iterator<Cell> iter = forIterating.iterator();
		
		while(iter.hasNext()) {
			Cell curr = iter.next();
			for(Cell q: gatherDirectNeighbors(curr, true)) {
				ret.add(q);
			}
		}
		return new ArrayList<Cell>(ret);
		
	}
	
	
	
	/**
	 * Gathers a cells 8 direct neighbors (edge and corner neighbors). If a neighbor cell isn't initialized, 
	 * method will create them.
	 * @param Cell to find neighbors for
	 * @return ArrayList of cells that are the neighbors
	 */
	
	public ArrayList<Cell> gatherDirectNeighbors(Cell c, boolean create) {
		
		ArrayList<Cell> ret = new ArrayList<Cell>();
		
		int [] grabFrom = {-1,-1,-1,0,-1,1,0,-1,0,1,1,-1,1,0,1,1};
		
		for(int i = 0; i<16; i++) {
			int m = grabFrom[i] + c.getX(); i++;
			int n = grabFrom[i] + c.getY();
			int [] tuple = {m,n};
			
			if (create){ret.add(GetOrAdd(tuple));}
			else{Cell add = Get(tuple);
			if(add!=null){ret.add(add);}
			}
		}
		return ret;
	}
	
	
	// NON STATE CHANGING METHODS -----------------------------------------------------------------------------------
	
	/**
	 * Simple conversion from tuple to key. Adds tuple to map with standard key based on tuple
	 * @param tuple coordinates
	 */
	public void Put(int [] tuple) {
		put(tuple[0]+" "+tuple[1],new Cell(tuple));
	}
	
	
	/**
	 * Simple conversion from tuple to key
	 * @param tuple coordinates
	 * @return if said coordinate is mapped
	 */
	public boolean ContainsKey(int [] tuple) {
		if(containsKey(tuple[0]+" "+tuple[1])) {return true;}
		return false;
	}
	
	
	/**
	 * Simple conversion from tuple to key
	 * @param tuple coordinates
	 * @return Cell in coordinate map
	 */
	public Cell Get(int[] tuple) {
		return get(tuple[0]+" " +tuple[1]);
	}
	
	
	/**
	 * Either gets or creates a new cell depending if it is in the map or not
	 * @param tuple
	 * @return Cell that was either there or now created
	 */
	public Cell GetOrAdd(int [] tuple) {
		if(!ContainsKey(tuple)) {
			Put(tuple);
		}
		return Get(tuple);
	}
	
	/**
	 * Clears grid of all entries
	 */
	public void clearAll() {
		clear();
	}
	
	/**
	 * Changes grid ruleset to input rule
	 * @param r Ruleset for grid to be switched to
	 */
	public static void changeRules(Ruleset r){
		Rules = r;
	}
	
	/**
	 * Enables changed flag to be used if mouse input changes grid state
	 */
	public void changed() {
		changed = true;
	}
	
	
	/**
	 * Prints to the console for debugging purposes
	 */
	public void debug() {
		System.out.println(totalTime + "::"+size());
	}
	
	

}
