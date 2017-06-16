package starwars;

import java.util.ArrayList;
import java.util.EnumMap;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;

/**
 * Class that serves as a container class which contains multiple girds
 * 
 */
public class SWUniverse {
	
	/**A EnumMap which uses WorldName as index and the value stored is SWGrid*/
	private EnumMap<WorldName, SWGrid> worldList = new EnumMap<>(WorldName.class);
	
	/**The name of the current grid in which player lives*/
	private WorldName currentWorldName;
	
	/**
	 * Constructor for the SWUniverse
	 * This Constructor initializes all SWGrid required by users with prescribed size
	 * 
	 * @param factory: SWLocationMaker, which is used to construct SWGrid
	 */
	public SWUniverse(SWLocation.SWLocationMaker factory)
	{
		this.worldList.put(WorldName.INITIAL, new SWGrid(factory, 10, 10));
		this.worldList.put(WorldName.YAVINIV, new SWGrid(factory, 2, 2));
		this.worldList.put(WorldName.DEATHSTAR, new SWGrid(factory, 10, 10));
		
		this.currentWorldName = WorldName.INITIAL;
	}

	/**
	 * return the current SWGrid in which player lives
	 * @return a SWGrid, in which player lives
	 */
	public SWGrid getCurrentGrid()
	{
		return this.worldList.get(this.currentWorldName);
	}
	
	/**
	 * set current grid in which player lives
	 * @param newWorld: name of the grid
	 */
	public void setCurrentGrid(WorldName newWorld)
	{
		this.currentWorldName = newWorld;
	}

}
