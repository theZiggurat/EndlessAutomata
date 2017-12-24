package cell.app;

import java.util.ArrayList;
import java.util.Arrays;

import cell.data.Cell;

public class Ruleset {
	
	/**
	 * This class contains the ruleset for 2d automata in 1 cell range.
	 * Credits to http://psoup.math.wisc.edu/mcell/rullex_life.html for source.
	 */
	
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
			new ArrayList<Integer>(Arrays.asList(3,4)), 
			new ArrayList<Integer>(Arrays.asList(3,4)));

	public final static Ruleset AMEOBA = new Ruleset(
			new ArrayList<Integer>(Arrays.asList(1,3,5,8)), 
			new ArrayList<Integer>(Arrays.asList(3,5,7)));
	
	public final static Ruleset TWOBYTWO = new Ruleset(
			new ArrayList<Integer>(Arrays.asList(1,2,5)), 
			new ArrayList<Integer>(Arrays.asList(3,6)));
	
	public final static Ruleset ASSIMILATION = new Ruleset(
			new ArrayList<Integer>(Arrays.asList(4,5,6,7)), 
			new ArrayList<Integer>(Arrays.asList(3,4,5)));

	public final static Ruleset COAGULATIONS = new Ruleset(
			new ArrayList<Integer>(Arrays.asList(2,3,5,6,7)), 
			new ArrayList<Integer>(Arrays.asList(3,7,8)));
	
	public final static Ruleset CORAL = new Ruleset(
			new ArrayList<Integer>(Arrays.asList(4,5,6,7,8)), 
			new ArrayList<Integer>(Arrays.asList(3)));
	
	public final static Ruleset DAY_AND_NIGHT = new Ruleset(
			new ArrayList<Integer>(Arrays.asList(3,4,6,7,8)), 
			new ArrayList<Integer>(Arrays.asList(3,6,7,8)));
	
	public final static Ruleset DIAMOEBA = new Ruleset(
			new ArrayList<Integer>(Arrays.asList(5,6,7,8)), 
			new ArrayList<Integer>(Arrays.asList(3,5,6,7,8)));
	
	public final static Ruleset FLAKES = new Ruleset(
			new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,5,6,7,8)), 
			new ArrayList<Integer>(Arrays.asList(3)));
	
	public final static Ruleset GNARL = new Ruleset(
			new ArrayList<Integer>(Arrays.asList(1)), 
			new ArrayList<Integer>(Arrays.asList(1)));
	
	public final static Ruleset HIGHLIFE = new Ruleset(
			new ArrayList<Integer>(Arrays.asList(2,3)), 
			new ArrayList<Integer>(Arrays.asList(3,6)));
	
	public final static Ruleset INVERSELIFE = new Ruleset(
			new ArrayList<Integer>(Arrays.asList(3,4,6,7,8)), 
			new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,7,8)));
	
	public final static Ruleset LONGLIFE = new Ruleset(
			new ArrayList<Integer>(Arrays.asList(5)), 
			new ArrayList<Integer>(Arrays.asList(3,4,5)));
	
	public final static Ruleset MAZE = new Ruleset(
			new ArrayList<Integer>(Arrays.asList(1,2,3,4,5)), 
			new ArrayList<Integer>(Arrays.asList(3)));
	
	

}
