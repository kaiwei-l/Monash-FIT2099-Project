package starwars.entities.actors.behaviors;

import edu.monash.fit2099.gridworld.Grid.CompassBearing;
import edu.monash.fit2099.simulator.space.Direction;
import starwars.SWActor;
import starwars.SWLocation;
import starwars.SWWorld;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * get available directions for a SWActor randomly
 */
public class Wander {
	
	private SWActor a;
	
	private SWWorld world;
	
	/**
	 * constructor of Wander class
	 * @param a: target actor
	 * @param world: SWWorld to which the target actor belongs
	 */
	public Wander(SWActor a, SWWorld world)
	{
		this.a = a;
		this.world = world;
	}
	
	/**
	 * 
	 * @return next available direction randomly
	 */
	public Direction getNextMove()
	{
		ArrayList<Direction> lstOfMoves = new ArrayList<>();
		SWLocation actorLoc = world.getEntityManager().whereIs(a); 
		for (CompassBearing d : CompassBearing.values())
		{
			if (actorLoc.getNeighbour(d) != null)
			{
				lstOfMoves.add(d);
			}
		}
		return lstOfMoves.get(ThreadLocalRandom.current().nextInt(0, lstOfMoves.size()));
	}
	
	
	
	

}
