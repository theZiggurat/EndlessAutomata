package cell.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cell.data.Cell;

public class Ruleset {
	
	/**
	 * This class contains the ruleset for 2d automata in 1 cell range.
	 * Credits to http://psoup.math.wisc.edu/mcell/rullex_life.html for source.
	 */
	
	ArrayList<Integer> survives = new ArrayList<Integer>();
	ArrayList<Integer> born = new ArrayList<Integer>();
	String name;
	
	
	/**
	 * Constructor not to be used outside of this class
	 * @param name
	 * @param survives
	 * @param born
	 */
	
	public Ruleset(String name, ArrayList<Integer> survives, ArrayList<Integer> born) {
		this.name = name;
		this.survives = survives;
		this.born = born;
	}
	
	/**
	 * Returns true if said cell survives the iteration
	 * @param c Cell
	 * @return The cell's survival
	 */
	
	public boolean survives(Cell c) {
		if(survives.contains(c.getNeighbors())) {return true;}
		return false;
	}
	
	/**
	 * Returns true if said cell can be created for that iteration
	 * @param c Cell
	 * @return Whether the cell is created or not
	 */
	public boolean born(Cell c) {
		if(born.contains(c.getNeighbors())) {return true;}
		return false;
	}
	
	
	// disgusting code, turn back now
	
	public final static Ruleset CONWAYS_GAME_OF_LIFE = new Ruleset("Conway's Life",
			new ArrayList<Integer>(Arrays.asList(2,3)), 
			new ArrayList<Integer>(Arrays.asList(3) ));
	
	public final static Ruleset THIRTYFOUR_LIFE = new Ruleset("34 Life",
			new ArrayList<Integer>(Arrays.asList(3,4)), 
			new ArrayList<Integer>(Arrays.asList(3,4)));

	public final static Ruleset AMEOBA = new Ruleset("Ameoba",
			new ArrayList<Integer>(Arrays.asList(1,3,5,8)), 
			new ArrayList<Integer>(Arrays.asList(3,5,7)));
	
	public final static Ruleset TWOBYTWO = new Ruleset("2x2",
			new ArrayList<Integer>(Arrays.asList(1,2,5)), 
			new ArrayList<Integer>(Arrays.asList(3,6)));
	
	public final static Ruleset ASSIMILATION = new Ruleset("Assimilation",
			new ArrayList<Integer>(Arrays.asList(4,5,6,7)), 
			new ArrayList<Integer>(Arrays.asList(3,4,5)));

	public final static Ruleset COAGULATIONS = new Ruleset("Coagulations",
			new ArrayList<Integer>(Arrays.asList(2,3,5,6,7)), 
			new ArrayList<Integer>(Arrays.asList(3,7,8)));
	
	public final static Ruleset CORAL = new Ruleset("Coral",
			new ArrayList<Integer>(Arrays.asList(4,5,6,7,8)), 
			new ArrayList<Integer>(Arrays.asList(3)));
	
	public final static Ruleset DAY_AND_NIGHT = new Ruleset("Day and Night",
			new ArrayList<Integer>(Arrays.asList(3,4,6,7,8)), 
			new ArrayList<Integer>(Arrays.asList(3,6,7,8)));
	
	public final static Ruleset DIAMOEBA = new Ruleset("Diamoeba",
			new ArrayList<Integer>(Arrays.asList(5,6,7,8)), 
			new ArrayList<Integer>(Arrays.asList(3,5,6,7,8)));
	
	public final static Ruleset FLAKES = new Ruleset("Flakes",
			new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,5,6,7,8)), 
			new ArrayList<Integer>(Arrays.asList(3)));
	
	public final static Ruleset GNARL = new Ruleset("Gnarl",
			new ArrayList<Integer>(Arrays.asList(1)), 
			new ArrayList<Integer>(Arrays.asList(1)));
	
	public final static Ruleset HIGHLIFE = new Ruleset("Highlife",
			new ArrayList<Integer>(Arrays.asList(2,3)), 
			new ArrayList<Integer>(Arrays.asList(3,6)));
	
	public final static Ruleset INVERSELIFE = new Ruleset("Inverse-Life",
			new ArrayList<Integer>(Arrays.asList(3,4,6,7,8)), 
			new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,7,8)));
	
	public final static Ruleset LONGLIFE = new Ruleset("Long Life",
			new ArrayList<Integer>(Arrays.asList(5)), 
			new ArrayList<Integer>(Arrays.asList(3,4,5)));
	
	public final static Ruleset MAZE = new Ruleset("Maze",
			new ArrayList<Integer>(Arrays.asList(1,2,3,4,5)), 
			new ArrayList<Integer>(Arrays.asList(3)));
	
	public final static Ruleset MAZECTRIC = new Ruleset("Mazectric",
			new ArrayList<Integer>(Arrays.asList(1,2,3,4)), 
			new ArrayList<Integer>(Arrays.asList(3)));
	
	public final static Ruleset MOVE = new Ruleset("Move",
			new ArrayList<Integer>(Arrays.asList(2,4,5)), 
			new ArrayList<Integer>(Arrays.asList(3,6,8)));
	
	public final static Ruleset P_LIFE = new Ruleset("Pseudo Life",
			new ArrayList<Integer>(Arrays.asList(2,3,8)), 
			new ArrayList<Integer>(Arrays.asList(3,5,7)));
	
	public final static Ruleset REPLICATOR = new Ruleset("Replicator",
			new ArrayList<Integer>(Arrays.asList(1,3,5,7)), 
			new ArrayList<Integer>(Arrays.asList(1,3,5,7)));
	
	public final static Ruleset SEEDS = new Ruleset("Seeds",
			new ArrayList<Integer>(Arrays.asList()), 
			new ArrayList<Integer>(Arrays.asList(2)));
	
	public final static Ruleset	SERVIETTES = new Ruleset("Serviettes",
			new ArrayList<Integer>(Arrays.asList()), 
			new ArrayList<Integer>(Arrays.asList(2,3,4)));
	
	public final static Ruleset STAINS = new Ruleset("Stains",
			new ArrayList<Integer>(Arrays.asList(2,3,5,6,7,8)), 
			new ArrayList<Integer>(Arrays.asList(3,6,7,8)));
	
	public final static Ruleset WALLED_CITIES = new Ruleset("Walled Cities",
			new ArrayList<Integer>(Arrays.asList(2,3,4,5)), 
			new ArrayList<Integer>(Arrays.asList(4,5,6,7,8)));
	
	
	// Probably not the best way to do this type of thing, but it works!
	
	public static List<Ruleset> allRules = Arrays.asList(CONWAYS_GAME_OF_LIFE, THIRTYFOUR_LIFE, AMEOBA, TWOBYTWO, 
			ASSIMILATION, COAGULATIONS, CORAL, DAY_AND_NIGHT, DIAMOEBA, FLAKES, GNARL, HIGHLIFE, INVERSELIFE, LONGLIFE, 
			MAZE, MAZECTRIC, MOVE, P_LIFE, REPLICATOR, SEEDS, SERVIETTES, STAINS, WALLED_CITIES);
	
	
	/**
	 * Creates ruleset
	 * @param name of rule
	 * @param survives amount of neighbors where cell can live
	 * @param born amount of neighbors where cell can be created
	 * @return ruleset containing all input information
	 */
	public Ruleset makeRuleset(String name, ArrayList<Integer> survives, ArrayList<Integer> born) {
		Ruleset ret = new Ruleset(name, survives, born);
		allRules.add(ret);
		return ret;
		
	}
}
