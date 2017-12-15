package cell.app;

import java.util.ArrayList;
import java.util.Arrays;

public class Ruleset {
	
	ArrayList<Integer> survives = new ArrayList<Integer>();
	ArrayList<Integer> born = new ArrayList<Integer>();
	
	
	public Ruleset(ArrayList<Integer> survives, ArrayList<Integer> born) {
		this.survives = survives;
		this.born = born;
	}
	
	public boolean survives(Cell c) {
		if(survives.contains(c.getNeighbors())) {return true;}
		return false;
	}
	
	public boolean born(Cell c) {
		if(born.contains(c.getNeighbors())) {return true;}
		return false;
	}
	
	public final static Ruleset CONWAYS_GAME_OF_LIFE = new Ruleset(
			new ArrayList<Integer>(Arrays.asList(2,3)), 
			new ArrayList<Integer>(Arrays.asList(3) ));
	
	public final static Ruleset THIRTYFOUR_LIFE = new Ruleset(
			new ArrayList<Integer>(Arrays.asList(2,3)), 
			new ArrayList<Integer>(Arrays.asList(3) ));

	public final static Ruleset AMEOBA = new Ruleset(
			new ArrayList<Integer>(Arrays.asList(2,3)), 
			new ArrayList<Integer>(Arrays.asList(3) ));


}
