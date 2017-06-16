package starwars.entities.actors;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Attack;
import starwars.actions.Move;
import starwars.entities.Blaster;
import starwars.entities.actors.behaviors.Wander;

/**
 * a class which represents Stormtropper in Star Wars
 */
public class Stormtrooper extends SWActor {
	
	/**
	 * Stormtropper has no name but ID number according to the Star wars movies
	 */
	private String id;
	
	/**
	 * Stormtropper get random available direction from this class and moves to that direction
	 */
	private Wander path;
	
	/**
	 * constructor of Stormtropper
	 * 
	 * Each Stormtropper will get a random 3-digit number as his ID number
	 * 
	 * Each Stormtropper has a blaster initially
	 * @param m
	 * @param world
	 */
	public Stormtrooper(MessageRenderer m, SWWorld world)
	{
		super(Team.EVIL, 100, m, world);
		path = new Wander(this, world);
		this.id = "" + ThreadLocalRandom.current().nextInt(100, 999);
		this.setSymbol("ST");
		Blaster tropperweapon = new Blaster(m);
		setItemCarried(tropperweapon);
	}
	
	@Override
	public String getShortDescription()
	{
		return id + " the Stormtropper";
	}
	
	@Override
	public String getLongDescription()
	{
		return this.getShortDescription();
	}
	
	private String describeLocation()
	{
		SWLocation location = this.world.getEntityManager().whereIs(this);
		return this.getShortDescription() + " [" + this.getHitpoints() + "] is at " + location.getShortDescription();
	}
	
	@Override
	public void act()
	{
		List<SWEntityInterface> entities = world.getEntityManager().
				contents(world.getEntityManager().whereIs(this));

		// attack SWActor with different team
		if(entities.size() > 1)
		{
			for(SWEntityInterface e : entities)
			{
				if(e != this && e instanceof SWActor && ((SWActor)e).getTeam() != this.getTeam())
				{
					// 25% chance to attack the SWActor who is not in Team.EVIL
					if(ThreadLocalRandom.current().nextInt(1, 100) >= 75)
					{
						// 25% chance to shoot without miss
						if(ThreadLocalRandom.current().nextInt(1, 100) >= 75)
						{
							scheduler.schedule(new Attack(e, messageRenderer), this, 1);
						}
						// miss shoot
						else
						{
							say("Stormtropper shoots wildly!");
						}
					}
					// Stormtropper should only attack or try to acttack one SWActor at each round
					return;
				}
			}
		}
		
		// radioing backup
		if(ThreadLocalRandom.current().nextInt(1, 100) >= 95)
		{
			this.world.getEntityManager().setLocation(new Stormtrooper(messageRenderer, world), this.world.getEntityManager().whereIs(this));
			return;
		}
		
		// wander
		Direction nextDirection = this.path.getNextMove();
		Move myMove = new Move(nextDirection, messageRenderer, world);
		scheduler.schedule(myMove, this, 1);
		
	}
}
