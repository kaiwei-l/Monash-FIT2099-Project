package starwars.entities.actors;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.ForceAbility;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWLegend;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Attack;
import starwars.actions.ForceChoke;
import starwars.actions.Move;
import starwars.actions.Seduce;
import starwars.entities.LightSaber;
import starwars.entities.actors.behaviors.Wander;

/**
 * a class which represents Darth Vader
 * 
 * Darth Vader will wonders around randomly with a LightSaber. Darth Vader will force choke
 * any SWActor he encounter in a 50% chance. Darth Vader will drag Luke into drak side of the
 * force and Luke has 25% chance to escape if Luke has been trained
 *
 */
public class DarthVader extends SWLegend{
	
	private static DarthVader darthVader = null;
	
	/**Darth Vader gets next direction to move to from this class*/
	private Wander path;
	
	/**
	 * constructor of Darth Vader
	 * Darth Vader has strong force and he has a lightsaber
	 * @param m: MessageRenderer
	 * @param world: the world to which Darth Vader belongs
	 */
	private DarthVader(MessageRenderer m, SWWorld world)
	{
		super(Team.EVIL, 10000, m, world);
		this.setShortDescription("Darth Vader");
		this.setLongDescription("Darth Vader, most powerful Sith Worrier");
		LightSaber vaderweapon = new LightSaber(m);
		setItemCarried(vaderweapon);
		forceAbility = ForceAbility.LEVEL3;
		path = new Wander(this, world);
	}
	
	public static DarthVader getDarthVader(MessageRenderer m, SWWorld world)
	{
		darthVader = new DarthVader(m, world);
		darthVader.activate();
		return darthVader;
	}
	
	@Override
	protected void legendAct()
	{
		if(isDead())
		{
			return;
		}
		
		List<SWEntityInterface> entities = world.getEntityManager().
				contents(world.getEntityManager().whereIs(this));
		
		// not just darth vader
		if(entities.size() > 1)
		{
			for (SWEntityInterface e : entities)
			{
				if(e instanceof SWActor)
				{
					// encounter luke
				    if(e != this && e instanceof Player)
				    {
					    // turn luke into dark side in a 50% chance
					    if(ThreadLocalRandom.current().nextInt(1, 100) >= 50)
					    {
						    scheduler.schedule(new Seduce(e, messageRenderer), this, 1);
					    }
					    // attack luke
					    else
					    {
						    scheduler.schedule(new Attack(e, messageRenderer), this, 1);
					    }
					    return;
				    }
				    else if(e != this)
				    {
					// force choke this actor in 50% chance
					    if(ThreadLocalRandom.current().nextInt(1, 100) >= 50)
					    {
						    scheduler.schedule(new ForceChoke(e, messageRenderer), this, 1);
						    return;
					    }
				    }
				}
			}
		}
		// randomly wander
		Direction nextDirection = this.path.getNextMove();
		Move myMove = new Move(nextDirection, messageRenderer, world);
		scheduler.schedule(myMove, this, 1);

	}

}
