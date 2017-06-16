package starwars;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import edu.monash.fit2099.gridworld.Grid.CompassBearing;
import edu.monash.fit2099.simulator.matter.Actor;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.space.Location;
import edu.monash.fit2099.simulator.space.World;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.actions.Take;
import starwars.actions.Transport;
import starwars.entities.*;
import starwars.entities.actors.*;

/**
 * Class representing a world in the Star Wars universe. 
 * 
 * @author ram
 */
/*
 * Change log
 * 2017-02-02:  Render method was removed from Middle Earth
 * 				Displaying the Grid is now handled by the TextInterface rather 
 * 				than by the Grid or MiddleWorld classes (asel)
 */
public class SWWorld extends World {
	
	/**The entity manager of the world which keeps track of <code>SWEntities</code> and their <code>SWLocation</code>s*/
	private static final EntityManager<SWEntityInterface, SWLocation> entityManager = new EntityManager<SWEntityInterface, SWLocation>();
	
	private SWUniverse myUniverse;
	
	/**
	 * Constructor of <code>SWWorld</code>. This will initialize the <code>SWLocationMaker</code>
	 * and the grid.
	 */
	public SWWorld() {
		SWLocation.SWLocationMaker factory = SWLocation.getMaker();
		this.myUniverse = new SWUniverse(factory);
		
	}

	/** 
	 * Returns the height of the <code>Grid</code>. Useful to the Views when rendering the map.
	 * 
	 * @author ram
	 * @return the height of the grid
	 */
	public int height() {
		return this.myUniverse.getCurrentGrid().getHeight();
	}
	
	/** 
	 * Returns the width of the <code>Grid</code>. Useful to the Views when rendering the map.
	 * 
	 * @author ram
	 * @return the height of the grid
	 */
	public int width() {
		return this.myUniverse.getCurrentGrid().getWidth();
	}
	
	/**
	 * Set up the world, setting descriptions for locations and placing items and actors
	 * on the grid.
	 * 
	 * @author 	ram
	 * @param 	iface a MessageRenderer to be passed onto newly-created entities
	 */
	public void initializeWorld(MessageRenderer iface) {
		this.initializeYavinIV(iface);
		this.initializeDeathStar(iface);
		this.initializeInitialWorld(iface);
	}

	/*
	 * Render method was removed from here
	 */
	
	/**
	 * Determine whether a given <code>SWActor a</code> can move in a given direction
	 * <code>whichDirection</code>.
	 * 
	 * @author 	ram
	 * @param 	a the <code>SWActor</code> being queried.
	 * @param 	whichDirection the <code>Direction</code> if which they want to move
	 * @return 	true if the actor can see an exit in <code>whichDirection</code>, false otherwise.
	 */
	public boolean canMove(SWActor a, Direction whichDirection) {
		SWLocation where = (SWLocation)entityManager.whereIs(a); // requires a cast for no reason I can discern
		return where.hasExit(whichDirection);
	}
	
	/**
	 * Accessor for the grid.
	 * 
	 * @author ram
	 * @return the grid
	 */
	public SWGrid getGrid() {
		return this.myUniverse.getCurrentGrid();
	}

	/**
	 * Move an actor in a direction.
	 * 
	 * @author ram
	 * @param a the actor to move
	 * @param whichDirection the direction in which to move the actor
	 */
	public void moveEntity(SWActor a, Direction whichDirection) {
		
		//get the neighboring location in whichDirection
		Location loc = entityManager.whereIs(a).getNeighbour(whichDirection);
		
		// Base class unavoidably stores superclass references, so do a checked downcast here
		if (loc instanceof SWLocation)
			//perform the move action by setting the new location to the the neighboring location
			entityManager.setLocation(a, (SWLocation) entityManager.whereIs(a).getNeighbour(whichDirection));
	}

	/**
	 * Returns the <code>Location</code> of a <code>SWEntity</code> in this grid, null if not found.
	 * Wrapper for <code>entityManager.whereIs()</code>.
	 * 
	 * @author 	ram
	 * @param 	e the entity to find
	 * @return 	the <code>Location</code> of that entity, or null if it's not in this grid
	 */
	public Location find(SWEntityInterface e) {
		return entityManager.whereIs(e); //cast and return a SWLocation?
	}

	/**
	 * This is only here for compliance with the abstract base class's interface and is not supposed to be
	 * called.
	 */

	@SuppressWarnings("unchecked")
	public EntityManager<SWEntityInterface, SWLocation> getEntityManager() {
		return SWWorld.getEntitymanager();
	}

	/**
	 * Returns the <code>EntityManager</code> which keeps track of the <code>SWEntities</code> and
	 * <code>SWLocations</code> in <code>SWWorld</code>.
	 * 
	 * @return 	the <code>EntityManager</code> of this <code>SWWorld</code>
	 * @see 	{@link #entityManager}
	 */
	public static EntityManager<SWEntityInterface, SWLocation> getEntitymanager() {
		return entityManager;
	}
	
	/**
	 * Switch the current grid of SWWorld
	 * @param aName: the name of the new grid
	 */
	public void switchGrid(WorldName aName)
	{
		if(aName == WorldName.INITIAL)
		{
			this.myUniverse.setCurrentGrid(aName);
		}
		else if(aName == WorldName.DEATHSTAR)
		{
			this.myUniverse.setCurrentGrid(aName);
		}
		else
		{
			this.myUniverse.setCurrentGrid(aName);
		}
	}

	/**
	 * initialization method to initialize original world in which player lives at the beginning of the game
	 * @param iface MessageRenderer
	 */
	private void initializeInitialWorld(MessageRenderer iface)
	{
		this.switchGrid(WorldName.INITIAL);
		SWLocation loc;
		// Set default location string
		for (int row=0; row < height(); row++) {
			for (int col=0; col < width(); col++) {
				loc = this.getGrid().getLocationByCoordinates(col, row);
				loc.setLongDescription("SWWorld (" + col + ", " + row + ")");
				loc.setShortDescription("SWWorld (" + col + ", " + row + ")");
				loc.setSymbol('.');				
			}
		}
		
		
		// BadLands
		for (int row = 5; row < 8; row++) {
			for (int col = 4; col < 7; col++) {
				loc = this.getGrid().getLocationByCoordinates(col, row);
				loc.setLongDescription("Badlands (" + col + ", " + row + ")");
				loc.setShortDescription("Badlands (" + col + ", " + row + ")");
				loc.setSymbol('b');
			}
		}
		
		//Ben's Hut
		loc = this.getGrid().getLocationByCoordinates(5, 6);
		loc.setLongDescription("Ben's Hut");
		loc.setShortDescription("Ben's Hut");
		loc.setSymbol('H');
		
		Direction [] patrolmoves = {CompassBearing.EAST, CompassBearing.EAST,
                CompassBearing.SOUTH,
                CompassBearing.WEST, CompassBearing.WEST,
                CompassBearing.SOUTH,
                CompassBearing.EAST, CompassBearing.EAST,
                CompassBearing.NORTHWEST, CompassBearing.NORTHWEST};
		
		BenKenobi ben = BenKenobi.getBenKenobi(iface, this, patrolmoves);
		ben.setSymbol("B");
		ben.setHitpoints(500);
		loc = this.getGrid().getLocationByCoordinates(5, 9);
		entityManager.setLocation(ben, loc);
		
		
		loc = this.getGrid().getLocationByCoordinates(0, 0);
		
		// Luke
		Player luke = new Player(Team.GOOD, 200, iface, this);
		luke.setShortDescription("Luke");
		entityManager.setLocation(luke, loc);
		luke.resetMoveCommands(loc);
		
		
		// Beggar's Canyon 
		for (int col = 3; col < 8; col++) {
			loc = this.getGrid().getLocationByCoordinates(col, 8);
			loc.setShortDescription("Beggar's Canyon (" + col + ", " + 8 + ")");
			loc.setLongDescription("Beggar's Canyon  (" + col + ", " + 8 + ")");
			loc.setSymbol('C');
			loc.setEmptySymbol('='); // to represent sides of the canyon
		}
		
		// Moisture Farms
		for (int row = 0; row < 10; row++) {
			for (int col = 8; col < 10; col++) {
				loc = this.getGrid().getLocationByCoordinates(col, row);
				loc.setLongDescription("Moisture Farm (" + col + ", " + row + ")");
				loc.setShortDescription("Moisture Farm (" + col + ", " + row + ")");
				loc.setSymbol('F');
				
				// moisture farms have reservoirs
				entityManager.setLocation(new Reservoir(iface), loc);				
			}
		}
		//Add uncle Owen and Aunt Beru to moisture farm  
		Humanoid uncleOwen = new Humanoid(50,"Uncle Owen", iface, this);
		uncleOwen.setSymbol("UO");
		uncleOwen.setForceAbility(ForceAbility.LEVEL0);
		loc = this.getGrid().getLocationByCoordinates(5,9);
		entityManager.setLocation(uncleOwen, loc);
		uncleOwen.resetMoveCommands(loc);

		
		Humanoid auntBeru = new Humanoid(50,"Aunt Beru", iface, this);
		auntBeru.setSymbol("AB");
		auntBeru.setForceAbility(ForceAbility.LEVEL0);
		loc = this.getGrid().getLocationByCoordinates(8,6);
		entityManager.setLocation(auntBeru, loc);
		auntBeru.resetMoveCommands(loc);

		
		// Ben Kenobi's hut
		/*
		 * Scatter some other entities and actors around
		 */
		// a canteen
		loc = this.getGrid().getLocationByCoordinates(6,  9);
		Canteen canteen = new Canteen(iface, 10,8);
		canteen.setSymbol("o");
		canteen.setHitpoints(500);
		entityManager.setLocation(canteen, loc);
		canteen.addAffordance(new Take(canteen, iface));

		// an oil can treasure
		loc = this.getGrid().getLocationByCoordinates(5,9);
		OilCan oilcan = new OilCan(iface);
		oilcan.setShortDescription("an oil can");
		oilcan.setLongDescription("an oil can, which would theoretically be useful for fixing robots");
		oilcan.setSymbol("x");
		oilcan.setHitpoints(100);
		// add a Take affordance to the oil can, so that an actor can take it
		entityManager.setLocation(oilcan, loc);
		
		// a lightsaber
		LightSaber lightSaber = new LightSaber(iface);
		loc = this.getGrid().getLocationByCoordinates(5,9);
		entityManager.setLocation(lightSaber, loc);
		
		// A blaster 
		Blaster blaster = new Blaster(iface);
		loc = this.getGrid().getLocationByCoordinates(3, 4);
		entityManager.setLocation(blaster, loc);
		
		// A C-3PO Droid
		C3PO c3po = new C3PO(200, iface, this, null);
		c3po.setSymbol("P");
		c3po.initializeStatusAndAffordance(DroidStatus.FUNCTIONAL);
		loc = this.getGrid().getLocationByCoordinates(0, 0);
		entityManager.setLocation(c3po, loc);

		
		Direction [] r2d2PatrolMoves = {CompassBearing.EAST, CompassBearing.EAST, CompassBearing.EAST, 
				CompassBearing.EAST, CompassBearing.EAST, CompassBearing.WEST,
				CompassBearing.WEST, CompassBearing.WEST, CompassBearing.WEST, CompassBearing.WEST};

		// A R2-D2 Droid
		R2D2 r2d2 = new R2D2(200, iface, this, null, r2d2PatrolMoves);
		r2d2.setSymbol("R");
		r2d2.setHitpoints(50);
		r2d2.initializeStatusAndAffordance(DroidStatus.FUNCTIONAL);
		loc = this.getGrid().getLocationByCoordinates(1, 1);
		entityManager.setLocation(r2d2, loc);
		
		// A general droid 1
		GeneralDroid generalDroid1 = new GeneralDroid(100, iface, this, null);
		generalDroid1.setHitpoints(50);
		generalDroid1.setSymbol("G");
		generalDroid1.initializeStatusAndAffordance(DroidStatus.FUNCTIONAL);
		loc = this.getGrid().getLocationByCoordinates(2, 1);
		entityManager.setLocation(generalDroid1, loc);
		
		// A general droid 2
		GeneralDroid generalDroid2 = new GeneralDroid(100, iface, this, null);
		generalDroid1.setHitpoints(0);
		generalDroid2.setSymbol("G");
		generalDroid2.initializeStatusAndAffordance(DroidStatus.IMMOBILE);
		loc = this.getGrid().getLocationByCoordinates(3, 1);
		entityManager.setLocation(generalDroid2, loc);
		

		//create Tusken Raiders
		TuskenRaider tim = new TuskenRaider(200, "Tim", iface, this);
		tim.setSymbol("T1");
		loc = this.getGrid().getLocationByCoordinates(3, 8);  //(3, 8)
		entityManager.setLocation(tim, loc);
		
		TuskenRaider kim = new TuskenRaider(200, "Kim", iface, this);				
		kim.setSymbol("T2");
		loc = this.getGrid().getLocationByCoordinates(4, 4);
		entityManager.setLocation(kim, loc);
		
        TuskenRaider jim = new TuskenRaider(200, "Jim", iface, this);				
		jim.setSymbol("T3");
		loc = this.getGrid().getLocationByCoordinates(4, 2);
		entityManager.setLocation(jim, loc);
		
		TuskenRaider lim = new TuskenRaider(200, "Lim", iface, this);				
		lim.setSymbol("T4");
		loc = this.getGrid().getLocationByCoordinates(4, 3);
		entityManager.setLocation(lim, loc);
		
		// A Millennium Falcon Spaceship
		MillenniumFalcon mf = new MillenniumFalcon(iface);
		mf.addAffordance(new Transport(mf, iface, WorldName.DEATHSTAR, this));
		mf.addAffordance(new Transport(mf, iface, WorldName.YAVINIV, this));
		loc = this.getGrid().getLocationByCoordinates(0, 0);
		entityManager.setLocation(mf, loc);
		
	}
	
	/**
	 * Initialization method to initialize Yavin IV planet
	 * @param iface MessageRenderer
	 */
	private void initializeYavinIV(MessageRenderer iface)
	{
		this.switchGrid(WorldName.YAVINIV);
		SWLocation loc;
		// Set default location string
		for (int row=0; row < height(); row++) {
			for (int col=0; col < width(); col++) {
				loc = this.getGrid().getLocationByCoordinates(col, row);
				loc.setLongDescription("SWWorld (" + col + ", " + row + ")");
				loc.setShortDescription("SWWorld (" + col + ", " + row + ")");
				loc.setSymbol('.');				
			}
		}
		
		// A Millennium Falcon Spaceship
		MillenniumFalcon mf = new MillenniumFalcon(iface);
		mf.addAffordance(new Transport(mf, iface, WorldName.DEATHSTAR, this));
		mf.addAffordance(new Transport(mf, iface, WorldName.INITIAL, this));
		loc = this.getGrid().getLocationByCoordinates(0, 0);
		entityManager.setLocation(mf, loc);
		
		//create MonMothma
		MonMothma monMothma = new MonMothma("Mon Mothma ", iface, this);
		monMothma.setSymbol("MM");
		loc = this.getGrid().getLocationByCoordinates(0,0);
		entityManager.setLocation(monMothma, loc);
							
		//create Admiral Ackbar
		AdmiralAckbar ackbar = new AdmiralAckbar("Admiral Ackbar ", iface, this);
		ackbar.setSymbol("AA");
		loc = this.getGrid().getLocationByCoordinates(1,0);
		entityManager.setLocation(ackbar, loc);
	}
	
	/**
	 * Initialization method to initialize death star
	 * @param iface: MessageRenderer
	 */
	private void initializeDeathStar(MessageRenderer iface)
	{
		this.switchGrid(WorldName.DEATHSTAR);
		SWLocation loc;
		// Set default location string
		for (int row=0; row < height(); row++) {
			for (int col=0; col < width(); col++) {
				loc = this.getGrid().getLocationByCoordinates(col, row);
				loc.setLongDescription("SWWorld (" + col + ", " + row + ")");
				loc.setShortDescription("SWWorld (" + col + ", " + row + ")");
				loc.setSymbol('.');				
			}
		}
		
		// A Millennium Falcon Spaceship
		MillenniumFalcon mf = new MillenniumFalcon(iface);
		mf.addAffordance(new Transport(mf, iface, WorldName.YAVINIV, this));
		mf.addAffordance(new Transport(mf, iface, WorldName.INITIAL, this));
		loc = this.getGrid().getLocationByCoordinates(0, 0);
		entityManager.setLocation(mf, loc);
		
		// Darth Vader
		loc = this.getGrid().getLocationByCoordinates(5, 5);
		DarthVader darthVader = DarthVader.getDarthVader(iface, this);
		darthVader.setSymbol("DV");
		entityManager.setLocation(darthVader, loc);
		
		// 5 Stormtroppers
		for(int i = 0; i < 5; i++)
		{
			int col = ThreadLocalRandom.current().nextInt(0, 10);
			int row = ThreadLocalRandom.current().nextInt(0, 10);
			loc = this.getGrid().getLocationByCoordinates(col, row);
			Stormtrooper st = new Stormtrooper(iface, this);
			entityManager.setLocation(st, loc);
		}
		
		//princess leia at the edge of DeathStar as a prisoner
		LeiaOrgana leia = new LeiaOrgana(100, iface, this);
		leia.setSymbol("PL");
		loc = this.getGrid().getLocationByCoordinates(9, 9);
		entityManager.setLocation(leia, loc);
	}
	
	/**
	 * 
	 * @return the Universe object inside SWWorld
	 */
	public SWUniverse getUniverse()
	{
		return this.myUniverse;
	}
	
	/**
	 * 
	 * @param a: actor who will be transported from its current grid into another grid
	 */
	public void transportToNewGrid(SWActor a)
	{
		SWLocation loc = this.getGrid().getLocationByCoordinates(0, 0);
		
		// EntityManager sets the new location in the new grid for actor
		entityManager.setLocation(a, loc);
	}
	
	public static ArrayList<Actor> getActors(){
		/**
		 * gets the Actors created in the <Code>SWWorld<Code> from entitiyManager and stores it in an ArrayList 
		 * @return an ArrayList containing <Code>Actor<Code> created in the this class
		 */
		return entityManager.getActors();
	}
}
