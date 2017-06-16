package starwars.actions;

import java.util.List;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.WorldName;
import starwars.SWWorld;

/**
 * transports player and 
 * all other SWActors, who are in the same team with player 
 * and are in or around player's position to the grid player wants to go
 * 
 * Any entity with this affordance can transport SWActor
 */
public class Transport extends SWAffordance implements SWActionInterface{
	
	/**the name of the grid which SWActor will be transported to*/
	private WorldName where;
	
	/**SWWorld world*/
	private SWWorld world;
	
	/**
	 * constructor of class Transport
	 * 
	 * @param theTarget: target actor who will be transported
	 * @param m: MessageRenderer
	 * @param where: name of the new grid
	 * @param world: SWWorld to which target actor belongs to
	 */
	public Transport(SWEntityInterface theTarget, MessageRenderer m, WorldName where, SWWorld world)
	{
		super(theTarget, m);
		this.where = where;
		this.world = world;
		priority = 1;
	}
	
	@Override
	public int getDuration()
	{
		return 1;
	}
	
	@Override
	public String getDescription()
	{
		return "transport to " + where.toString(this.where);
	}
	
	@Override
	public boolean canDo(SWActor a)
	{
		return !a.isDead();
	}
	
	@Override
	public void act(SWActor a)
	{
		if(a.isDead())
		{return;}

		this.world.switchGrid(this.where);
		
		SWLocation lukeLoc = world.getEntityManager().whereIs(a);
		
		// transport all SWActors inside the target actor's location except target actor himself
		this.transportActor(a, lukeLoc);
		
		// transport all the neighbour SWActor of target actor
		for (Grid.CompassBearing d : Grid.CompassBearing.values())
		{
			SWLocation tmp = (SWLocation) lukeLoc.getNeighbour(d);
			if (tmp != null)
			{
				this.transportActor(a, tmp);	
			}
		}
		// transport target actor
		this.world.transportToNewGrid(a);
	}
	
	/**
	 * this method only transports target SWactor and all other SWActor, who with the same team as the target SWActor and are  
	 * inside the same location as the target SWActor to the new grid at a time
	 * 
	 * @param a: target actor to be transported
	 * @param aLocation: location of the target SWActor
	 */
	private void transportActor(SWActor a, SWLocation aLocation)
	{
		EntityManager<SWEntityInterface, SWLocation> em = world.getEntityManager();
		List<SWEntityInterface> entities = em.contents(aLocation);
		
		// check current location's entities
		if(entities != null)
		{
			for(SWEntityInterface e : entities)
			{
				// entities has to be SWActor and it has to be in the same team with target actor
				if(e != a && e instanceof SWActor && ((SWActor)e).getTeam() == a.getTeam())
				{
					world.transportToNewGrid((SWActor)e);
				}
			}
		}
	}
	
	

}
