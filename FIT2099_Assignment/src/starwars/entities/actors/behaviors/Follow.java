package starwars.entities.actors.behaviors;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.gridworld.Grid.CompassBearing;
import edu.monash.fit2099.simulator.space.Direction;
import starwars.SWActor;
import starwars.SWLocation;
import starwars.SWWorld;

/**
 * This class is used to schedule Follow behaviour of a droid
 * 
 * @author Kaiwei Luo
 *
 */
public class Follow {
	private SWActor owner;
	private SWActor droid;
	private SWWorld world;
	
	public Follow(SWActor a, SWActor d, SWWorld w)
	{
		owner = a;
		world = w;
		droid = d;
	}
	
	/**
	 * We have owner location and droid location, so we try each potential direction and uses
	 * getNeighbour() method to get neighbour's location. If the location we get is the same
	 * as owner location, then we know the realtive location of Droid and Owner
	 * 
	 * @return relative direction of Droid and its Owner
	 */
	public Direction getDirection()
	{
		// get owner location
		SWLocation ownerLocation = world.getEntityManager().whereIs(owner);
		
		// get droid location
		SWLocation droidLocation = world.getEntityManager().whereIs(droid);
        
		// Compare
		for (Grid.CompassBearing d : Grid.CompassBearing.values())
		{
			SWLocation tmp = (SWLocation) droidLocation.getNeighbour(d);
			if(tmp == ownerLocation)
			{
				return d;  // owner direction
			}	
		}
		return null;
	}

}
